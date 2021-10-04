package com.orbsec.photobackendusersapi.circuitbreaker;

import com.orbsec.photobackendusersapi.services.AlbumsServiceClient;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {

    @Override
    public AlbumsServiceClient create(Throwable cause) {
        return new AlbumsServiceFallback(cause);
    }
}
