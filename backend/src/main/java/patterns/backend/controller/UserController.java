package patterns.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.User;
import patterns.backend.dto.UserDto;
import patterns.backend.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = userService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") final Long userId) {
        UserDto userDto = convertToDto(userService.findUserById(userId));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        UserDto userDto = convertToDto(userService.create(user));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody User user) {
        UserDto userDto = convertToDto(userService.update(user));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") final Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private UserDto convertToDto(User user) {
        UserDto userDTO = modelMapper.map(user, UserDto.class);
        return userDTO;
    }

}
