package fr.soft64.mddapi.dto;

import java.time.ZonedDateTime;

import fr.soft64.mddapi.model.Post;
import fr.soft64.mddapi.model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentCreateDto {

	private Users user;

	private Post post;

	@NotNull
	private ZonedDateTime created_at;

	@NotBlank
	@Size(max = 2000)
	private String comment;

	public CommentCreateDto() {
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public ZonedDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(ZonedDateTime created_at) {
		this.created_at = created_at;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
