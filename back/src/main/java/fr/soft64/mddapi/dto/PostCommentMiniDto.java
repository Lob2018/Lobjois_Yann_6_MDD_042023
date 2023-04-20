package fr.soft64.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostCommentMiniDto {

	@NotBlank
	@Size(max = 2000)
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
