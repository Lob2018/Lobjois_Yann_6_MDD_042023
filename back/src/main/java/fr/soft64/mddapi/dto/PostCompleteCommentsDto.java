package fr.soft64.mddapi.dto;

import java.util.List;

public class PostCompleteCommentsDto {
	private List<CommentDto> comments;
	private PostDtoTxtSubject post;

	public PostCompleteCommentsDto() {
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

	public PostDtoTxtSubject getPost() {
		return post;
	}

	public void setPost(PostDtoTxtSubject post) {
		this.post = post;
	}

}
