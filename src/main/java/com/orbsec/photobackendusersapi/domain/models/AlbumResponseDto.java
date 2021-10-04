package com.orbsec.photobackendusersapi.domain.models;

import lombok.Data;

@Data
public class AlbumResponseDto {

    private int id;
    private String albumId;
    private String userId;
    private String name;
    private String description;
}
