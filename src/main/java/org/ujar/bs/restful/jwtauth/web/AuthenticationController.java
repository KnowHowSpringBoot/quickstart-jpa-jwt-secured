package org.ujar.bs.rst.jwtauth.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ujar.bs.rst.jwtauth.dto.AuthenticationRequestDto;
import org.ujar.bs.rst.jwtauth.entity.User;
import org.ujar.bs.rst.jwtauth.security.jwt.JwtTokenProvider;
import org.ujar.bs.rst.jwtauth.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/auth/")
record AuthenticationController(AuthenticationManager authenticationManager,
                                JwtTokenProvider jwtTokenProvider,
                                UserService userService) {

  @PostMapping("login")
  ResponseEntity<Map<String, String>> login(@RequestBody final AuthenticationRequestDto requestDto) {
    try {
      final String username = requestDto.getUsername();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
      final User user = userService.findByUsername(username);

      if (user == null) {
        throw new UsernameNotFoundException("User with username: " + username + " not found");
      }

      final String token = jwtTokenProvider.createToken(username, user.getRoles());

      final var response = new HashMap<String, String>();
      response.put("username", username);
      response.put("token", token);

      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }
}