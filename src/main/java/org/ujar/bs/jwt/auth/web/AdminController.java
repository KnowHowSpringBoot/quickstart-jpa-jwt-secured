package org.ujar.bs.jwt.auth.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ujar.bs.jwt.auth.dto.AdminUserDto;
import org.ujar.bs.jwt.auth.entity.User;
import org.ujar.bs.jwt.auth.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/admin/")
record AdminController(UserService userService) {

  @GetMapping(value = "users/{id}")
  ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") final Long id) {
    final User user = userService.findById(id);

    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    final AdminUserDto result = AdminUserDto.fromUser(user);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}