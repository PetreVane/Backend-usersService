package com.orbsec.photobackendusersapi.controller;

import com.orbsec.photobackendusersapi.dto.CreateUserDto;
import com.orbsec.photobackendusersapi.dto.UserResponseDto;
import com.orbsec.photobackendusersapi.exceptions.UserAccountNotFound;
import com.orbsec.photobackendusersapi.exceptions.UserNotRegistered;
import com.orbsec.photobackendusersapi.services.AlbumsServiceClient;
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
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private Environment environment;
    private UserService userService;
    private AlbumsServiceClient albumsServiceClient;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(Environment environment, UserService userService, AlbumsServiceClient albumsServiceClient) {
        this.environment = environment;
        this.userService = userService;
        this.albumsServiceClient = albumsServiceClient;
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


    //    @GetMapping(path = "/{userID}", produces = {"application/json", "application/xml"})
//    @PreAuthorize("principal == #userID")
//    public UserResponseDto getUserByIdAndAlbums(@PathVariable String userID) throws UserAccountNotFound {
//
//        logger.info("Before calling Albums Microservice");
//        List<AlbumResponseDto> actualList = albumsServiceClient.findAllAlbums();
//        logger.info("After calling Albums Microservice");
//        var user = userService.findUserById(userID);
//        user.setAlbums(actualList);
//        return user;
//    }

//    @GetMapping(path = "/albums/status")
//    public String testFeignClient() {
//        return albumsServiceClient.getStatus();
//    }
}
