package fr.soft64.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.soft64.mddapi.model.Post;
import fr.soft64.mddapi.model.Subject;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findBySubjectOrderByCreatedAtDesc(Subject subject);
	
}
