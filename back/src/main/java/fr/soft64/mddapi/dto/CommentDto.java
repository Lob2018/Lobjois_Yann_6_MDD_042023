package fr.soft64.mddapi.dto;

import java.time.ZonedDateTime;

import org.springframework.format.annotation.NumberFormat;

import fr.soft64.mddapi.model.Post;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 255)
	private String username;

	@NumberFormat
	private Long postId;

	@NotNull
	@Column(name = "created_at", updatable = false)
	private ZonedDateTime created_at;

	@NotBlank
	@Column(name = "comment", length = 2000)
	@Size(max = 2000)
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
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
