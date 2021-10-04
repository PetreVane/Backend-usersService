package com.orbsec.photobackendusersapi.circuitbreaker;

import com.orbsec.photobackendusersapi.domain.models.AlbumResponseDto;
import com.orbsec.photobackendusersapi.services.AlbumsServiceClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class AlbumsServiceFallback implements AlbumsServiceClient {

    private final Throwable cause;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public AlbumsServiceFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public String getStatus() {
        getErrorMessage();
        return "Albums Microservices is not available. This is the fallback client";
    }

    @Override
    public List<AlbumResponseDto> findAllAlbums() {
        getErrorMessage();
        return new ArrayList<>();
    }

    @Override
    public AlbumResponseDto findAlbumById(String albumId) {
        getErrorMessage();
        return new AlbumResponseDto();
    }

    private void getErrorMessage() {
        String errorMessage;
        logger.warn("This is the fallback method. Albums Microservice cannot be reached!");
        if (cause instanceof FeignException) {
            switch (((FeignException) cause).status()) {
            case 400 :
                errorMessage = "Bad request; the server could not respond to your request!";
                break;
            case 404:
                errorMessage = "Resource not found. You are looking for something that does not exists";
                break;
            default:
                errorMessage = cause.getLocalizedMessage();
        }
            logger.error(String.format("Complete error %s. Status code %d", errorMessage, ((FeignException) cause).status()));
        } else {
            logger.error(String.format("Another error has taken place %s", cause.getLocalizedMessage()));
        }
    }
}
