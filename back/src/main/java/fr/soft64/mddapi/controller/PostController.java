package fr.soft64.mddapi.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import fr.soft64.mddapi.dto.CommentDto;
import fr.soft64.mddapi.dto.PostCommentMiniDto;
import fr.soft64.mddapi.dto.PostCompleteCommentsDto;
import fr.soft64.mddapi.dto.PostCreateMiniDto;
import fr.soft64.mddapi.dto.PostDto;
import fr.soft64.mddapi.dto.PostDtoTxtSubject;
import fr.soft64.mddapi.model.Comment;
import fr.soft64.mddapi.model.Post;
import fr.soft64.mddapi.model.Subject;
import fr.soft64.mddapi.model.Users;
import fr.soft64.mddapi.service.CommentService;
import fr.soft64.mddapi.service.PostService;
import fr.soft64.mddapi.service.SubjectService;
import fr.soft64.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Post", description = "The post API. Contains all the operations that can be performed on a post.")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private CommentService commentService;

	private Post convertPostCreateMiniDtoToPost(PostCreateMiniDto miniPost, Users user, Subject subject) {
		Post post = new Post();
		post.setSubject(subject);
		post.setUser(user);
		post.setContent(miniPost.getContent());
		post.setTitle(miniPost.getTitle());
		return post;
	}

	private PostDto convertPostToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setSubject_id(post.getSubject().getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setUsername(post.getUser().getUsername());
		postDto.setCreated_at(post.getCreated_at());
		return postDto;
	}

	private PostDtoTxtSubject convertPostToDtoTxtSubject(Post post) {
		PostDtoTxtSubject postDtoTxtSubject = new PostDtoTxtSubject();
		postDtoTxtSubject.setId(post.getId());
		postDtoTxtSubject.setSubject(post.getSubject().getTopic());
		postDtoTxtSubject.setSubject_id(post.getSubject().getId());
		postDtoTxtSubject.setTitle(post.getTitle());
		postDtoTxtSubject.setContent(post.getContent());
		postDtoTxtSubject.setUsername(post.getUser().getUsername());
		postDtoTxtSubject.setCreated_at(post.getCreated_at());
		return postDtoTxtSubject;
	}

	private CommentDto convertCommentToDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setUsername(comment.getUser().getUsername());
		commentDto.setPostId(comment.getPost().getId());
		commentDto.setCreated_at(comment.getCreated_at());
		commentDto.setComment(comment.getComment());
		return commentDto;
	}

	private PostCompleteCommentsDto convertPostCompleteCommentsToDto(Post post, PostDtoTxtSubject postDto) {
		PostCompleteCommentsDto postCompleteCommentsDto = new PostCompleteCommentsDto();
		postCompleteCommentsDto.setPost(postDto);
		final List<CommentDto> postsDtoList = ((Collection<Comment>) post.getComments()).stream()
				.map(this::convertCommentToDto).collect(Collectors.toList());
		postsDtoList.sort(Comparator.comparing(CommentDto::getCreated_at).reversed());
		postCompleteCommentsDto.setComments(postsDtoList);
		return postCompleteCommentsDto;
	}

	/**
	 * Get all the posts
	 * 
	 * @return The HTTP response
	 */
	@GetMapping("")
	@Operation(description = "List all the posts")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"posts\":[{\"id\":1,\"username\":\"Toto\",\"title\":\"titre1\",\"content\":\"contenu\",\"created_at\":\"2023-01-30T19:44:28+01:00\"},{\"id\":2,\"username\":\"Toto2\",\"title\":\"titre2\",\"content\":\"contenu2\",\"created_at\":\"2023-01-30T19:44:28+01:00\"}]}")), responseCode = "200")
	public final ResponseEntity<Object> findAll() {
		final List<PostDto> postsDtoList = ((Collection<Post>) postService.getAllPosts()).stream()
				.map(this::convertPostToDto).collect(Collectors.toList());
		final HashMap<String, List<PostDto>> map = new HashMap<>();
		map.put("posts", postsDtoList);
		return ResponseEntity.ok().body(map);
	}

	/**
	 * Get a post by id
	 * 
	 * @param id post id
	 * @return The HTTP response
	 */
	@GetMapping("/{postId}")
	@Operation(description = "Get a post by id")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class)), responseCode = "200")
	@ApiResponse(content = @Content(schema = @Schema(defaultValue = "")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Post not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> get(
			@Parameter(description = "The post ID to get") @PathVariable("postId") Long id) {
		try {
			final PostDto postDto = convertPostToDto(postService.findPostById(id).get());
			return ResponseEntity.ok().body(postDto);
		} catch (NoSuchElementException ex) {
			final HashMap<String, String> map = new HashMap<>();
			map.put("message", "Post not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}

	/**
	 * Get all comments for a post by id (post is include)
	 * 
	 * @param id post id
	 * @return The HTTP response
	 */
	@GetMapping("/{postId}/comments")
	@Operation(description = "Get all comments for a post by id (post is include)")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"comments\":[{\"id\":2,\"username\":\"Toto\",\"postId\":1,\"comment\":\"comment2\",\"created_at\":\"2023-04-21T13:47:09+02:00\"},{\"id\":1,\"username\":\"Toto\",\"postId\":1,\"comment\":\"comment1\",\"created_at\":\"2023-04-20T13:47:09+02:00\"}],\"post\":{\"id\":1,\"subject_id\":1,\"subject\":\"Java\",\"username\":\"Toto2\",\"title\":\"Title3\",\"content\":\"Content3\",\"created_at\":\"2023-04-20T13:47:09+02:00\"}}")), responseCode = "200")
	@ApiResponse(content = @Content(schema = @Schema(defaultValue = "")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Post not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> getPostComments(
			@Parameter(description = "The post ID to get their comments") @PathVariable("postId") Long id) {
		try {
			// get the post
			final Post post = postService.findPostById(id).get();
			final PostDtoTxtSubject postDtoTxtSubject = convertPostToDtoTxtSubject(post);
			// get the post's comments
			final PostCompleteCommentsDto postCompleteCommentsDto = convertPostCompleteCommentsToDto(post,
					postDtoTxtSubject);
			return ResponseEntity.ok().body(postCompleteCommentsDto);
		} catch (NoSuchElementException ex) {
			final HashMap<String, String> map = new HashMap<>();
			map.put("message", "Post not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}

	/**
	 * Create a post
	 * 
	 * @return The HTTP response
	 */
	@PostMapping("")
	@ResponseBody
	@Operation(description = "Create a post")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"id\":12,\"subject_id\":1,\"username\":\"Toto\",\"title\":\"Title\",\"content\":\"Content\",\"created_at\":\"2023-04-20T10:41:58.6718493+02:00\"}")), responseCode = "201")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Subject not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> create(@RequestBody @Valid PostCreateMiniDto postCreateMiniDto) {
		try {
			final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
			final Optional<Users> user = userService.findByEmail(mail);
			Optional<Subject> subject = subjectService.findSubjectById(postCreateMiniDto.getSubject_id());
			Post postCreated = postService
					.createPost(convertPostCreateMiniDtoToPost(postCreateMiniDto, user.get(), subject.get()));
			final PostDto postCreatedDto = convertPostToDto(postCreated);
			return ResponseEntity.status(HttpStatus.CREATED).body(postCreatedDto);
		} catch (NoSuchElementException ex) {
			final HashMap<String, String> map = new HashMap<>();
			map.put("message", "Subject not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}

	/**
	 * Update a post
	 * 
	 * @param id                The post id to update
	 * @param postCreateMiniDto The new post data
	 * @return The HTTP response
	 */
	@PutMapping("/{postId}")
	@ResponseBody
	@Operation(description = "Create a post")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"id\":2,\"subject_id\":2,\"username\":\"Toto\",\"title\":\"Title2\",\"content\":\"Content2\",\"created_at\":\"2023-01-30T19:44:28+01:00\"}")), responseCode = "200")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> update(
			@Parameter(description = "The post ID to update") @PathVariable("postId") Long id,
			@RequestBody @Valid PostCreateMiniDto postCreateMiniDto) {
		try {
			final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
			final Optional<Users> user = userService.findByEmail(mail);
			Optional<Subject> subject = subjectService.findSubjectById(postCreateMiniDto.getSubject_id());
			Post postFound = postService.findPostById(id).get();
			if (postFound.getUser().getId() != user.get().getId()) {
				throw new Exception();
			}
			postFound.setTitle(postCreateMiniDto.getTitle());
			postFound.setContent(postCreateMiniDto.getContent());
			postFound.setSubject(subject.get());
			Post postUpdated = postService.updatePost(postFound);
			final PostDto postCreatedDto = convertPostToDto(postUpdated);
			return ResponseEntity.status(HttpStatus.OK).body(postCreatedDto);
		} catch (NoSuchElementException ex) {
			final HashMap<String, String> map = new HashMap<>();
			map.put("message", "Not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
		}
	}

	/**
	 * Delete a post
	 * 
	 * @param id The post id to delete
	 * @return The HTTP response
	 */
	@DeleteMapping("/{postId}")
	@ResponseBody
	@Operation(description = "Delete a post")
	@ApiResponse(content = @Content(schema = @Schema(defaultValue = "")), responseCode = "204", description = "No Content")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Post not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> delete(
			@Parameter(description = "The post ID to delete") @PathVariable("postId") Long id) {
		try {
			final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
			final Optional<Users> user = userService.findByEmail(mail);
			Post postFound = postService.findPostById(id).get();
			if (postFound.getUser().getId() != user.get().getId()) {
				throw new Exception();
			}
			postService.deletePost(postFound.getId());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (NoSuchElementException ex) {
			final HashMap<String, String> map = new HashMap<>();
			map.put("message", "Post not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
		}
	}

	/**
	 * Create a post comment
	 * 
	 * @param id                 The post id to comment
	 * @param postCommentMiniDto The new commment data
	 * @return The HTTP response
	 */
	@PostMapping("/{postId}/comment")
	@ResponseBody
	@Operation(description = "Comment a post")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"id\":6,\"username\":\"Toto\",\"postId\":1,\"created_at\":\"2023-04-20T16:13:32.2322108+02:00\",\"comment\":\"Comment\"}")), responseCode = "201")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> postComment(
			@Parameter(description = "The post ID to comment") @PathVariable("postId") Long id,
			@RequestBody @Valid PostCommentMiniDto postCommentMiniDto) {
		try {
			final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
			final Optional<Users> user = userService.findByEmail(mail);
			final Post postFound = postService.findPostById(id).get();
			Comment comment = new Comment();
			comment.setUser(user.get());
			comment.setComment(postCommentMiniDto.getComment());
			comment.setPost(postFound);
			Comment commentCreated = commentService.createComment(comment);
			final CommentDto returnedComment = convertCommentToDto(commentCreated);
			return ResponseEntity.status(HttpStatus.CREATED).body(returnedComment);
		} catch (NoSuchElementException ex) {
			final HashMap<String, String> map = new HashMap<>();
			map.put("message", "Not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
		}
	}

}
