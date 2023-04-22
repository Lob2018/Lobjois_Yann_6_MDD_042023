package fr.soft64.mddapi.service;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.soft64.mddapi.configuration.SecurityConfig;
import fr.soft64.mddapi.model.Users;
import fr.soft64.mddapi.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SecurityConfig securityConfig;

	private final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,254}$";
	private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	public final Users createUser(final Users user) {
		if (userPropertyIsNull(user) || !userPasswordIsStrong(user))
			throw new Error();
		user.setPassword(securityConfig.bCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	public final Users updateUser(final Users user) {
		if (userPropertyIsNull(user) || !userPasswordIsStrong(user))
			throw new Error();
		user.setPassword(securityConfig.bCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	public final Optional<Users> findByEmail(final String email) {
		if (email == null || email.trim().length() == 0) {
			throw new Error();
		}
		return userRepository.findByEmail(email);
	}

	public final boolean userPropertyIsNull(final Users user) {
		return user == null || user.getEmail().trim().length() == 0 || user.getUsername().trim().length() == 0
				|| user.getPassword().trim().length() == 0;
	}

	public final boolean userPasswordIsStrong(final Users user) {
		return (isValid(user.getPassword()));
	}

	/**
	 * <p>
	 * Indicates whether the password is strong enough (8-254 characters, with at
	 * least : 1 number, 1 lowercase letter, 1 uppercase letter, 1 special
	 * character)
	 * </p>
	 * REGEX explanation :<br>
	 * ^ # start of line<br>
	 * (?=.*[0-9]) # positive lookahead, digit [0-9]<br>
	 * (?=.*[a-z]) # positive lookahead, one lowercase character [a-z]<br>
	 * (?=.*[A-Z]) # positive lookahead, one uppercase character [A-Z]<br>
	 * {@code(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) # positive lookahead, one of the
	 * special} character in this [..]<br>
	 * . # matches anything #<br>
	 * {8,254} # length at least 8 characters and maximum of 254 characters<br>
	 * $ # end of line<br>
	 * 
	 * @param password the tested password
	 * @return if it is a strong password or not
	 */
	public final boolean isValid(final String password) {
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
