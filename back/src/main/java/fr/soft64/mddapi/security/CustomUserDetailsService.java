package fr.soft64.mddapi.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.model.Users;
import fr.soft64.mddapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public final UserDetails loadUserByUsername(final String userEmail) throws UsernameNotFoundException {
		final Optional<Users> user = userRepository.findByEmail(userEmail);

		if (user.isEmpty())
			throw new UsernameNotFoundException(userEmail + " not found");
		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
				getGrantedAuthorities("ROLE_USER"));
	}

	private List<GrantedAuthority> getGrantedAuthorities(final String role) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		// Spring Security automatically prefix role with ROLE_
		// so if the role name in database isn't prefix with ROLE_
		// we have to it
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

}
