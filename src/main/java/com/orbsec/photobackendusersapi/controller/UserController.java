package com.orbsec.photobackendusersapi.controller;

import com.orbsec.photobackendusersapi.dto.CreateUserDto;
import com.orbsec.photobackendusersapi.dto.ResponseMessage;
import com.orbsec.photobackendusersapi.dto.UserResponseDto;
import com.orbsec.photobackendusersapi.exceptions.UserAccountNotFound;
import com.orbsec.photobackendusersapi.exceptions.UserNotRegistered;
import com.orbsec.photobackendusersapi.services.AlbumsServiceClient;
import com.orbsec.photobackendusersapi.services.FileUploaderClient;
import com.orbsec.photobackendusersapi.services.UserService;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private Environment environment;
    private UserService userService;
    private FileUploaderClient fileUploader;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(Environment environment, UserService userService, FileUploaderClient fileUploader) {
        this.environment = environment;
        this.userService = userService;
        this.fileUploader = fileUploader;
    }

    @GetMapping("/status")
    public String getStatus() {
        var secretToken = environment.getProperty("token.secret");
        var portNumber = environment.getProperty("local.server.port");
        return "Users microservice is up and running on port " + portNumber + " and secret token " + secretToken;
    }

    @GetMapping(path = "/get/{userID}", produces = {"application/json", "application/xml"})
    @PreAuthorize("principal == #userID")
    public UserResponseDto getUserByID(@PathVariable String userID) throws UserAccountNotFound {
        return userService.findUserById(userID);
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll();
    }


    @PostMapping(path = "/add", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserDto createUserDto, AbstractBindingResult bindingResult) throws UserNotRegistered {
        if (bindingResult.hasErrors()) {
            throw new UserNotRegistered("An error has occurred while trying to register user. Check your input values and try again.");
        }

        UserResponseDto userResponseDto = userService.save(createUserDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }


    @PutMapping(path ="/update", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public String updateUser() {
        return "Updating user now ...";
    }


    @DeleteMapping(path ="/delete/{email}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public String deleteUser(@PathVariable String email) throws UserAccountNotFound {
        userService.deleteUser(email);
        return "User " + email + " has just been deleted...";
    }

//    FileUploader-Microservice
    @GetMapping("/uploader/status")
    public String fileUploaderGetStatus() {
        logger.info("Call to fileUploader -> getStatus() service ");
        return fileUploader.getStatus();
    }


    @PostMapping("/uploader/upload")
    @PreAuthorize("principal == #userID")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        logger.info("Call to fileUploader -> uploadFiles() service ");
        return fileUploader.uploadFiles(files);
    }

    @GetMapping("/uploader/files/{fileName}")
    @PreAuthorize("principal == #userID")
    public ResponseEntity<byte[]> getFileByName(@PathVariable String fileName) {
        logger.info("Call to fileUploader -> getFileByName() service ");
        return fileUploader.getFileByName(fileName);
    }

    @DeleteMapping("/uploader/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<String>> deleteAllFiles() {
        logger.info("Call to fileUploader -> deleteAllFiles() service ");
        return fileUploader.deleteAllFiles();
    }

    @DeleteMapping("/uploader/delete/{fileName}")
    @PreAuthorize("principal == #userID")
    public ResponseEntity<String> deleteFileByName(@PathVariable String fileName) {
        logger.info("Call to fileUploader -> deleteFileByName() service ");
        return fileUploader.deleteFileByName(fileName);
    }

}
