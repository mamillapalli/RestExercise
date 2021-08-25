package com.ravikanth.restexercise.controllers;

import com.ravikanth.restexercise.entity.User;
import com.ravikanth.restexercise.exceptions.UserNotFoundException;
import com.ravikanth.restexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/users")
    public List<User> listAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> findUserById(@PathVariable int id)
    {
        User user = userRepository.findUser(id);
        if(user == null) throw new UserNotFoundException(id);
        EntityModel<User> userEntityModel = EntityModel.of(user);
        WebMvcLinkBuilder usersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).listAllUsers());
        userEntityModel.add(usersLink.withRel("LinkToUsers"));
        return userEntityModel;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user)
    {
        User newUser = userRepository.addUser(user);
        URI newResourceLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(newResourceLocation).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        User deletedUser = userRepository.deleteById(id);
        if(deletedUser == null) throw new UserNotFoundException(id);

    }

}
