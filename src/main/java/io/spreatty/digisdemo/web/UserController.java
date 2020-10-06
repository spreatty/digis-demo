package io.spreatty.digisdemo.web;

import io.spreatty.digisdemo.exception.UserNotFoundException;
import io.spreatty.digisdemo.pojo.dto.UserDTO;
import io.spreatty.digisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> assembler;

    @PostMapping
    public ResponseEntity<?> create(UserDTO userDTO) {
        Optional<UserDTO> answer = userService.createUser(userDTO);
        if (!answer.isPresent()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        EntityModel<UserDTO> entityModel = assembler.toModel(answer.get());
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody UserDTO userDTO) {
        Optional<UserDTO> answer = userService.updateUser(id, userDTO);
        if (!answer.isPresent()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        EntityModel<UserDTO> entityModel = assembler.toModel(answer.get());
        return ResponseEntity.ok().body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<UserDTO> get(@PathVariable int id) {
        return assembler.toModel(userService.getUser(id));
    }

    @GetMapping
    public CollectionModel<EntityModel<UserDTO>> getAll() {
        return CollectionModel.of(
                userService.getAllUsers().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void userNotFound() {
    }
}
