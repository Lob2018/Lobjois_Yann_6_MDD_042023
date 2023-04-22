package fr.soft64.mddapi.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.soft64.mddapi.dto.UserUpdateMiniDto;
import fr.soft64.mddapi.dto.UserUpdateReturnMiniDto;
import fr.soft64.mddapi.model.Users;
import fr.soft64.mddapi.security.JwtTokenUtil;
import fr.soft64.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User", description = "The user API. Contains all the operations that can be performed on a user.")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private UserUpdateReturnMiniDto convertUserToDto(Users user) {
		UserUpdateReturnMiniDto userDto = new UserUpdateReturnMiniDto();
		userDto.setEmail(user.getEmail());
		userDto.setUsername(user.getUsername());
		return userDto;
	}

	/**
	 * Update user email and username
	 * 
	 * @param userCreateMiniDto The new user data
	 * @return The HTTP response
	 */
	@PutMapping("")
	@ResponseBody
	@Operation(description = "Update user credentials")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"email\":\"toto@toto.com\",\"username\":\"Toto\",\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3RvQHRvdG8uY29tIiwiaXNzIjoiZnIuc29mdDY0IiwiaWF0IjoxNjgyMTcyOTgyLCJleHAiOjE2ODI3Nzc3ODJ9.9Dv0gjLO71uISpOyC4Hv9o68AtcIbZPNjXo_frOeZmmWbQoVhRoJ0H18lAaWDvUjCaKSkUwsfyVoe1SUhm6tVA\"}")), responseCode = "200")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "401", description = "Unauthorized")
	public final ResponseEntity<Object> update(@RequestBody @Valid UserUpdateMiniDto userUpdateMiniDto) {
		try {
			final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
			final Users user = userService.findByEmail(mail).get();
			// prepare the new user credentials
			user.setEmail(userUpdateMiniDto.getEmail());
			user.setUsername(userUpdateMiniDto.getUsername());
			user.setPassword(userUpdateMiniDto.getPassword());
			final UsernamePasswordAuthenticationToken newUserAuth = new UsernamePasswordAuthenticationToken(
					user.getEmail(), user.getPassword());
			// update the user's credentials
			Users userUpdated = userService.updateUser(user);
			final Authentication authenticate = authenticationManager.authenticate(newUserAuth);
			final User autendicatedUser = (User) authenticate.getPrincipal();
			final String token = jwtTokenUtil.generateAccessToken(autendicatedUser);
			// prepare the user's data returned
			final UserUpdateReturnMiniDto userUpdatedDto = convertUserToDto(userUpdated);
			userUpdatedDto.setToken(token);
			// return the result
			return ResponseEntity.status(HttpStatus.OK).body(userUpdatedDto);
		} catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
		}
	}

}
