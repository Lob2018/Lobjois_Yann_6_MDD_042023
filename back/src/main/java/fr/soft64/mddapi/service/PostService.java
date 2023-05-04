package fr.soft64.mddapi.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.model.Post;
import fr.soft64.mddapi.model.Subject;
import fr.soft64.mddapi.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Sort;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private EntityManager entityManager;
	
	private final Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

	public final Iterable<Post> getAllPosts() {
		return postRepository.findAll(sort);
	}

	public final List<Post> findBySubjectRepository(Subject subject) {
		if (subject == null) {
			throw new Error();
		}
		return postRepository.findBySubjectOrderByCreatedAtDesc(subject);
	}

	public List<Post> findBySubscribedSubjectRepository(final List<Long> subjects) {
		TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p WHERE p.subject.id IN (:subjects) ORDER BY p.createdAt DESC",
				Post.class);
		List<Post> posts = query.setParameter("subjects", subjects).getResultList();
		return posts;
	}

	public final Optional<Post> findPostById(final Long id) {
		if (id == null) {
			throw new Error();
		}
		return postRepository.findById(id);
	}

	public final Post createPost(final Post post) {
		post.setCreated_at(ZonedDateTime.now());
		if (postPropertyWithoutCommentsIsNull(post))
			throw new Error();
		return postRepository.save(post);
	}

	public final Post updatePost(final Post post) {
		if (postPropertyWithoutCommentsIsNull(post))
			throw new Error();
		return postRepository.save(post);
	}

	public final void deletePost(Long id) {
		if (id == null) {
			throw new Error();
		}
		this.postRepository.deleteById(id);
	}

	public final boolean postPropertyWithoutCommentsIsNull(final Post post) {
		return post == null || post.getTitle().trim().length() == 0 || post.getContent().trim().length() == 0
				|| post.getSubject() == null || post.getUser() == null;
	}

}
