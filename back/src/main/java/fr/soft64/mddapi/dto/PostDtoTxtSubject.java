package fr.soft64.mddapi.dto;

import java.time.ZonedDateTime;

public class PostDtoTxtSubject {
	private Long id;
	private Long subject_id;
	private String subject;
	private String username;
	private String title;
	private String content;
	private ZonedDateTime createdAt;

	public PostDtoTxtSubject() {
	}

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

	public ZonedDateTime getCreated_at() {
		return createdAt;
	}

	public void setCreated_at(ZonedDateTime created_at) {
		this.createdAt = created_at;
	}

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
