package fr.soft64.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

	@NotBlank
	@Size(max = 255)
	private String password;

	@NotBlank
	@Email 
	@Size(max = 255)
	private String email;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserLoginDto [password=" + password + ", email=" + email + "]";
	}

}
