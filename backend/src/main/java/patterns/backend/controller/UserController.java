package patterns.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.User;
import patterns.backend.dto.UserDTO;
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
    public List<UserDTO> getUsers() {
        List<User> users = userService.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable final long id) {
        User user = userService.findUserById(id);
        return convertToDto(user);
    }

    @PutMapping()
    public void addUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable final long id) {
        userService.deleteUserById(id);
    }


    private UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

}
