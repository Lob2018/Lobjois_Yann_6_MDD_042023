package fr.soft64.mddapi.service;

import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

}
