package com.spmapi.spmapi.DTOs;

import org.apache.catalina.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDTO {

    private String username;
    private String password;    


    public CreateUserDTO userToCreateUserDTO(User user){
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername(user.getUsername());
        createUserDTO.setPassword(user.getPassword());
   
    return createUserDTO;
   }

}