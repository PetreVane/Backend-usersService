package com.orbsec.photobackendusersapi.services;

import com.orbsec.photobackendusersapi.message.ResponseFile;
import com.orbsec.photobackendusersapi.message.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "fileUploader-ms")
public interface FileUploaderClient {

    @GetMapping("/status")
    String getStatus();

    @PostMapping("/upload")
    ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile files);

    @GetMapping("/files")
    ResponseEntity<List<ResponseFile>> getAllFiles();

    @GetMapping("/files/{fileName}")
    ResponseEntity<byte[]> getFileByName(@PathVariable String fileName);

    @DeleteMapping("/delete")
    ResponseEntity<List<String>> deleteAllFiles();

    @DeleteMapping("/delete/{fileName}")
    ResponseEntity<String> deleteFileByName(@PathVariable String fileName);
}
