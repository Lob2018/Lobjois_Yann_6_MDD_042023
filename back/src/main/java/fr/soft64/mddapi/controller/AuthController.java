package fr.soft64.mddapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.soft64.mddapi.dto.UserLoginDto;
import fr.soft64.mddapi.dto.UserRegisterDto;
import fr.soft64.mddapi.model.Users;
import fr.soft64.mddapi.security.JwtTokenUtil;
import fr.soft64.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "The auth API. Contains all the operations that can be performed on a authentication.")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ModelMapper modelMapper;

	private Users convertToEntity(final UserRegisterDto userRegisterDto) {
		return modelMapper.map(userRegisterDto, Users.class);
	}

	@PostMapping("/register")
	@ResponseBody
	@Operation(description = "Register a user")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"token\": \"jwt\"\r\n" + "}")), responseCode = "200")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "400", description = "Bad Request")
	/**
	 * Register a new user
	 * 
	 * @param user  The user credentials to register
	 * @param token The corresponding token
	 * @return The HTTP response
	 * @throws MethodArgumentNotValidException Exception for invalid username
	 */
	public final ResponseEntity<Object> register(@RequestBody @Valid UserRegisterDto userRegistering) {
		final HashMap<String, String> map = new HashMap<>();
		try {
			final Users user = convertToEntity(userRegistering);
			final String plainPassword = user.getPassword();
			userService.createUser(user);
			// Get and return the new token
			final Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), plainPassword));
			final User autendicatedUser = (User) authenticate.getPrincipal();
			final String token = jwtTokenUtil.generateAccessToken(autendicatedUser);
			map.put("token", token);
			return ResponseEntity.ok().body(map);
		} catch (Error ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>());
		}
	}

	@PostMapping("/login")
	@Operation(description = "User login")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"token\": \"jwt\"\r\n" + "}")), responseCode = "200")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"error\"\r\n" + "}")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "400", description = "Bad Request")
	/**
	 * User login
	 * 
	 * @param user User login data
	 * @return The HTTP response
	 * @throws MethodArgumentNotValidException
	 */
	public final ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto user) {
		final HashMap<String, String> map = new HashMap<>();
		try {
			final String email = user.getEmail();
			// Get and return the new token
			final Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, user.getPassword()));
			final User autendicatedUser = (User) authenticate.getPrincipal();
			final String token = jwtTokenUtil.generateAccessToken(autendicatedUser);
			map.put("token", token);
			return ResponseEntity.ok().body(map);
		} catch (BadCredentialsException ex) {
			map.put("message", "error");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	/**
	 * Handles @Valid Exceptions (ex. empty email or password)
	 * 
	 * @param ex Spring Boot throwned exception, when the target argument
	 *           annotated @Valid fails to pass the Hibernate Validator
	 * @return Empty JSON
	 */
	public final Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return new HashMap<>();
	}
}