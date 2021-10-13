package com.orbsec.photobackendusersapi.circuitbreaker;

import com.orbsec.photobackendusersapi.dto.ResponseFile;
import com.orbsec.photobackendusersapi.dto.ResponseMessage;
import com.orbsec.photobackendusersapi.services.FileUploaderClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileUploaderServiceFallback implements FileUploaderClient {

    private final Throwable cause;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FileUploaderServiceFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public String getStatus() {
        getErrorMessage();
        return "File Uploader micro-service is not available yet!";
    }

    @Override
    public ResponseEntity<ResponseMessage> uploadFiles(MultipartFile files) {
        getErrorMessage();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
    }

    @Override
    public ResponseEntity<List<ResponseFile>> getAllFiles() {
        getErrorMessage();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ArrayList<>());
    }

    @Override
    public ResponseEntity<byte[]> getFileByName(String fileName) {
        getErrorMessage();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
    }

    @Override
    public ResponseEntity<List<String>> deleteAllFiles() {
        getErrorMessage();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
    }

    @Override
    public ResponseEntity<String> deleteFileByName(String fileName) {
        getErrorMessage();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }

    private void getErrorMessage() {
        String errorMessage;
        logger.error("This is the fallback method --> File Uploader micro-service is not available yet!");
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
