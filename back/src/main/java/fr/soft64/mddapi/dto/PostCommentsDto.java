package fr.soft64.mddapi.dto;

import java.util.List;

public class PostCommentsDto {

	private Long postId;
	private List<CommentDto> comments;

	public PostCommentsDto() {
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

}
