package fr.soft64.mddapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.model.Post;
import fr.soft64.mddapi.model.Subject;
import fr.soft64.mddapi.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public final Iterable<Post> getAllPosts() {
		return postRepository.findAll();
	}

	public final List<Post> findBySubjectRepository(Subject subject) {
		if (subject == null) {
			throw new Error();
		}
		return postRepository.findBySubject(subject);
	}

}
