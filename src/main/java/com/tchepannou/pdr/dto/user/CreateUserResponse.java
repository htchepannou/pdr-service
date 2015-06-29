package com.tchepannou.pdr.dto.user;

import com.tchepannou.pdr.domain.User;

public class CreateUserResponse extends GetUserResponse {
    //-- Constructor
    public CreateUserResponse(final User user){
        super (user);
    }
}
