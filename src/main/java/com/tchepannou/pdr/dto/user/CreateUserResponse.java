package com.tchepannou.pdr.dto.user;

import com.tchepannou.pdr.domain.User;

public class CreateUserResponse extends UserResponse {
    //-- Constructor
    public CreateUserResponse(final User user){
        super (user);
    }
}
