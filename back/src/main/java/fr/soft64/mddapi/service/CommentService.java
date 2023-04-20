package fr.soft64.mddapi.service;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.model.Comment;
import fr.soft64.mddapi.model.Post;
import fr.soft64.mddapi.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public final Comment createComment(final Comment comment) {
		comment.setCreated_at(ZonedDateTime.now());
		if (commentPropertyIsNull(comment))
			throw new Error();
		return commentRepository.save(comment);
	}

	public final boolean commentPropertyIsNull(final Comment comment) {
		return comment == null || comment.getComment().trim().length() == 0 || comment.getCreated_at() == null
				|| comment.getPost() == null || comment.getUser() == null;
	}

}
