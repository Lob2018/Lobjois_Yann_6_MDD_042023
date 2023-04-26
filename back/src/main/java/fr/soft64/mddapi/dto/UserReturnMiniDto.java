package fr.soft64.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserReturnMiniDto {

	@NotBlank
	@Size(max = 255)
	private String username;

	@NotBlank
	@Email
	@Size(max = 255)
	private String email;

	public UserReturnMiniDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
