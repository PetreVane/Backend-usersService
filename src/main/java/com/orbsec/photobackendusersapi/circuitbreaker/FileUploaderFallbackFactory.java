package com.orbsec.photobackendusersapi.circuitbreaker;

import org.springframework.cloud.openfeign.FallbackFactory;

public class FileUploaderFallbackFactory implements FallbackFactory<FileUploaderServiceFallback> {

    @Override
    public FileUploaderServiceFallback create(Throwable cause) {
        return new FileUploaderServiceFallback(cause);
    }
}
