package org.ujar.bs.rst.jwtauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.ujar.bs.rst.jwtauth.entity.Status;
import org.ujar.bs.rst.jwtauth.entity.User;

/**
 * DTO class for user requests by ROLE_ADMIN
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String status;

  public static AdminUserDto fromUser(User user) {
    final var adminUserDto = new AdminUserDto();
    adminUserDto.setId(user.getId());
    adminUserDto.setUsername(user.getUsername());
    adminUserDto.setFirstName(user.getFirstName());
    adminUserDto.setLastName(user.getLastName());
    adminUserDto.setEmail(user.getEmail());
    adminUserDto.setStatus(user.getStatus().name());
    return adminUserDto;
  }

  public User toUser() {
    final var user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setStatus(Status.valueOf(status));
    return user;
  }
}