package com.orbsec.photobackendusersapi.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private List<AlbumResponseDto> albums;
}
