package fr.soft64.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.soft64.mddapi.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
