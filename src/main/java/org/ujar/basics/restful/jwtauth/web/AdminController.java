package org.ujar.basics.restful.jwtauth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ujar.basics.restful.jwtauth.dto.AdminUserDto;
import org.ujar.basics.restful.jwtauth.entity.User;
import org.ujar.basics.restful.jwtauth.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/admin/")
@RequiredArgsConstructor
class AdminController {
  private final UserService userService;

  @GetMapping(value = "users/{id}")
  ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") final Long id) {
    User user = userService.findById(id);

    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    AdminUserDto result = AdminUserDto.fromUser(user);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
