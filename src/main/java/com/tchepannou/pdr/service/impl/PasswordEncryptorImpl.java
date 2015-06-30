package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.service.PasswordEncryptor;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordEncryptorImpl implements PasswordEncryptor {
    //-- Attributes
    private static final String HASH_ALGO = "MD5";

    //-- PasswordEncryptor overrides
    @Override
    public String encrypt(final String password) {
        try {
            return password != null ? hash(password, null) : null;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Encryption algorithm not supported");
        }
    }

    @Override
    public boolean matches(final String plainText, final User user) {
        try {
            String storedHashedPassword = user.getPassword();
            if (storedHashedPassword == null) {
                return plainText == null;
            }
            if (plainText == null) {
                return storedHashedPassword == null;
            }

            byte[] hashWithSaltBytes = new Base64().decode(storedHashedPassword.getBytes());

            // We must know size of hash (without salt).
            final int hashSizeInBytes = 16;

            // Make sure that the specified hash value is long enough.
            if (hashWithSaltBytes.length < hashSizeInBytes) {
                return false;
            }

            // Allocate array to hold original salt bytes retrieved from hash.
            byte[] saltBytes = new byte[hashWithSaltBytes.length - hashSizeInBytes];

            // Copy salt from the end of the hash to the new array.
            for (int i = 0; i < saltBytes.length; i++) {
                saltBytes[i] = hashWithSaltBytes[hashSizeInBytes + i];
            }

            // Compute a new hash string.
            String expectedHashString = hash(plainText, saltBytes);

            // If the computed hash matches the specified hash,
            // the plain text value must be correct.
            return storedHashedPassword.equals(expectedHashString);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    //-- Private
    private String hash(final String plainText, final byte[] salt)
            throws NoSuchAlgorithmException {
        byte[] saltBytes = salt;
        if (saltBytes == null) {
            int saltSize = new Random().nextInt(5) + 4;
            saltBytes = new byte[saltSize];

            SecureRandom secRandom = new SecureRandom();
            secRandom.nextBytes(saltBytes);
        }

        byte[] plainTextBytes = plainText.getBytes();
        byte[] plainTextWithSaltBytes = new byte[saltBytes.length + plainTextBytes.length];

        // Copy plain text bytes into resulting array.
        for (int i = 0; i < plainTextBytes.length; i++) {
            plainTextWithSaltBytes[i] = plainTextBytes[i];
        }

        // Append salt bytes to the resulting array.
        for (int i = 0; i < saltBytes.length; i++) {
            plainTextWithSaltBytes[plainTextBytes.length + i] = saltBytes[i];
        }

        MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
        md.reset();
        md.update(plainTextWithSaltBytes);
        plainTextWithSaltBytes = md.digest();

        // Create array which will hold hash and original salt bytes.
        byte[] hashWithSaltBytes = new byte[plainTextWithSaltBytes.length + saltBytes.length];

        // Copy hash bytes into resulting array.
        for (int i = 0; i < plainTextWithSaltBytes.length; i++) {
            hashWithSaltBytes[i] = plainTextWithSaltBytes[i];
        }

        // Append salt bytes to the result.
        for (int i = 0; i < saltBytes.length; i++) {
            hashWithSaltBytes[plainTextWithSaltBytes.length + i] = saltBytes[i];
        }

        // Convert result into a base64-encoded string.
        String hashValue = new String(new Base64().encode(hashWithSaltBytes));
        return hashValue;
    }

}
