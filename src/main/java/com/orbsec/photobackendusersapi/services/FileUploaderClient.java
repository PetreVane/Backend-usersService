package com.orbsec.photobackendusersapi.services;

import com.orbsec.photobackendusersapi.circuitbreaker.FileUploaderFallbackFactory;
import com.orbsec.photobackendusersapi.dto.ResponseFile;
import com.orbsec.photobackendusersapi.dto.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "fileUploader-ms", fallbackFactory = FileUploaderFallbackFactory.class)
public interface FileUploaderClient {

    @GetMapping("/api/status")
    String getStatus();

    @PostMapping("/api/upload")
    ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files);

    @GetMapping("/api/files")
    ResponseEntity<List<ResponseFile>> getAllFiles();

    @GetMapping("/api/files/{fileName}")
    ResponseEntity<byte[]> getFileByName(@PathVariable String fileName);

    @DeleteMapping("/api/delete")
    ResponseEntity<List<String>> deleteAllFiles();

    @DeleteMapping("/api/delete/{fileName}")
    ResponseEntity<String> deleteFileByName(@PathVariable String fileName);

    @GetMapping("/test/{message}")
    public ResponseEntity<String> testStatus(@PathVariable String message);
}
