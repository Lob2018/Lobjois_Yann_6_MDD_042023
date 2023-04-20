package fr.soft64.mddapi.dto;

import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostCreateMiniDto {
	
	@NumberFormat
	private Long subject_id;

	@NotBlank
	@Size(max = 255)
	private String title;

	@NotBlank
	@Size(max = 2000)
	private String content;

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}	
}
