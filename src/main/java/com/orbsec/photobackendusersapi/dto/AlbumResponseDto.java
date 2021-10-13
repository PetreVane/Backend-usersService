package com.orbsec.photobackendusersapi.dto;

import lombok.Data;

@Data
public class AlbumResponseDto {

    private int id;
    private String albumId;
    private String userId;
    private String name;
    private String description;
}
