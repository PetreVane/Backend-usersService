package com.orbsec.photobackendusersapi.services;

import com.orbsec.photobackendusersapi.domain.models.AlbumResponseDto;
import com.orbsec.photobackendusersapi.circuitbreaker.AlbumsFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "albums-ms", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumsServiceClient {

    @GetMapping("/albums/status")
    String getStatus();

    @GetMapping(path = "/albums/")
    List<AlbumResponseDto> findAllAlbums();

    @GetMapping(path ="/albums/{albumId}", produces = {"application/json", "application/xml"})
    AlbumResponseDto findAlbumById(@PathVariable String albumId);
}
