package fr.soft64.mddapi.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.soft64.mddapi.dto.PostDto;
import fr.soft64.mddapi.dto.SubjectDto;
import fr.soft64.mddapi.dto.SubjectDtoWithSubscribeOrNot;
import fr.soft64.mddapi.model.Post;
import fr.soft64.mddapi.model.Subject;
import fr.soft64.mddapi.model.Subscription;
import fr.soft64.mddapi.model.Users;
import fr.soft64.mddapi.service.PostService;
import fr.soft64.mddapi.service.SubjectService;
import fr.soft64.mddapi.service.SubscriptionService;
import fr.soft64.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/subject")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Subject", description = "The subject API. Contains all the operations that can be performed on a subject.")
public class SubjectController {

	@Autowired
	private UserService userService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private PostService postService;
	@Autowired
	private ModelMapper modelMapper;

	private final SubjectDto convertSubjectToDto(final Subject subject) {
		final SubjectDto subjectDto = modelMapper.map(subject, SubjectDto.class);
		return subjectDto;
	}

	private final SubjectDtoWithSubscribeOrNot convertSubjectToSubscribeDto(final Subject subject,
			final boolean subscribe) {
		SubjectDtoWithSubscribeOrNot subjecSubscribetDto = new SubjectDtoWithSubscribeOrNot();
		subjecSubscribetDto.setDescription(subject.getDescription());
		subjecSubscribetDto.setId(subject.getId());
		subjecSubscribetDto.setSubscribe(subscribe);
		subjecSubscribetDto.setTopic(subject.getTopic());
		return subjecSubscribetDto;
	}

	private final Long convertSubjectToId(final Subject subject) {
		return subject.getId();
	}

	private PostDto convertPostToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setSubject_id(post.getSubject().getId());
		postDto.setContent(post.getContent());
		postDto.setUsername(post.getUser().getUsername());
		postDto.setCreated_at(post.getCreated_at());
		return postDto;
	}

	/**
	 * Get all the subjects
	 * 
	 * @return The HTTP response
	 */
	@GetMapping("")
	@Operation(description = "List all the subjects")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"subjects\":[{\"id\":1,\"topic\":\"Java\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\"},{\"id\":2,\"topic\":\"JavaScript\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\"},{\"id\":3,\"topic\":\"Python\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\"},{\"id\":4,\"topic\":\"Web3\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\"}]}")), responseCode = "200")
	public final ResponseEntity<Object> findAll() {
		final List<SubjectDto> subjectsDtoList = ((Collection<Subject>) subjectService.getAllSubjects()).stream()
				.map(this::convertSubjectToDto).collect(Collectors.toList());
		final HashMap<String, List<SubjectDto>> map = new HashMap<>();
		map.put("subjects", subjectsDtoList);
		return ResponseEntity.ok().body(map);
	}

	/**
	 * Get a subject by id
	 * 
	 * @param id subject id
	 * @return The HTTP response
	 */
	@GetMapping("/{subjectId}")
	@Operation(description = "Get a subject by id")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDto.class)), responseCode = "200")
	@ApiResponse(content = @Content(schema = @Schema(defaultValue = "")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Subject not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> get(
			@Parameter(description = "The subject ID to get") @PathVariable("subjectId") Long id) {
		try {
			final SubjectDto subjectDto = convertSubjectToDto(subjectService.findSubjectById(id).get());
			return ResponseEntity.ok().body(subjectDto);
		} catch (NoSuchElementException ex) {
			final HashMap<String, String> map = new HashMap<>();
			map.put("message", "Subject not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}

	/**
	 * Get all subjects subscribed or not for current user (ordered by topics)
	 * 
	 * @return The HTTP response
	 */
	@GetMapping("/user")
	@Operation(description = "Get all subjects subscribed or not for current user (ordered by topics)")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"subjects\":[{\"id\":1,\"topic\":\"Java\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\",\"subscribe\":true},{\"id\":2,\"topic\":\"JavaScript\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\",\"subscribe\":true},{\"id\":3,\"topic\":\"Python\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\",\"subscribe\":false},{\"id\":4,\"topic\":\"Web3\",\"description\":\"Loremipsumdolorsitamet,consecteturadipiscingelit,seddoeiusmodtemporincididuntutlaboreetdoloremagnaaliqua.\",\"subscribe\":false}]}")), responseCode = "200")
	@ApiResponse(content = @Content(schema = @Schema(defaultValue = "")), responseCode = "401", description = "Unauthorized")
	public final ResponseEntity<Object> getCurrentUserSubjects() {
		// the complete subjects list
		final List<SubjectDtoWithSubscribeOrNot> subjectsDtoCompleteList = ((Collection<Subject>) subjectService
				.getAllSubjects()).stream().map(subject -> convertSubjectToSubscribeDto(subject, false))
				.collect(Collectors.toList());
		// the user subjects list
		final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
		final Optional<Users> user = userService.findByEmail(mail);
		if (user.isEmpty())
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
		final List<SubjectDtoWithSubscribeOrNot> userSubjectsDtoList = ((Set<Subject>) user.get().getSubjects())
				.stream().map(subject -> convertSubjectToSubscribeDto(subject, true)).collect(Collectors.toList());
		// merge
		userSubjectsDtoList.addAll(subjectsDtoCompleteList.stream()
				.filter(obj -> userSubjectsDtoList.stream().noneMatch(o -> o.getId() == obj.getId()))
				.collect(Collectors.toList()));
		// order by topic
		userSubjectsDtoList
				.sort(Comparator.comparing(SubjectDtoWithSubscribeOrNot::getTopic, String.CASE_INSENSITIVE_ORDER));
		final HashMap<String, List<SubjectDtoWithSubscribeOrNot>> map = new HashMap<>();
		map.put("subjects", userSubjectsDtoList);
		return ResponseEntity.ok().body(map);
	}

	/**
	 * Get posts by subject id
	 * 
	 * @param id subject id
	 * @return The HTTP response
	 */
	@GetMapping("/{subjectId}/posts")
	@Operation(description = "Get posts by subject id")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"posts\":[{\"id\":1,\"username\":\"Toto\",\"title\":\"titre1\",\"content\":\"contenu\",\"created_at\":\"2023-01-30T19:44:28+01:00\"},{\"id\":2,\"username\":\"Toto2\",\"title\":\"titre2\",\"content\":\"contenu2\",\"created_at\":\"2023-01-30T19:44:28+01:00\"}]}")), responseCode = "200")
	@ApiResponse(content = @Content(schema = @Schema(defaultValue = "")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Subject not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> getPostsBySubjectId(
			@Parameter(description = "The subject ID") @PathVariable("subjectId") Long id) {
		final HashMap<String, String> map = new HashMap<>();
		final Optional<Subject> subject = subjectService.findSubjectById(id);
		if (subject.isEmpty()) {
			map.put("message", "Subject not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		List<Post> allPosts = postService.findBySubjectRepository(subject.get());
		final List<PostDto> postsDtoList = ((List<Post>) allPosts).stream().map(this::convertPostToDto)
				.collect(Collectors.toList());
		final HashMap<String, List<PostDto>> mapCompleted = new HashMap<>();
		mapCompleted.put("posts", postsDtoList);
		return ResponseEntity.ok().body(mapCompleted);
	}

	/**
	 * Get posts by subjects the user subscribed
	 * 
	 * @return The HTTP response
	 */
	@GetMapping("/user/posts")
	@Operation(description = "Get posts by subjects the user subscribed")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\"posts\":[{\"id\":1,\"username\":\"Toto\",\"title\":\"titre1\",\"content\":\"contenu\",\"created_at\":\"2023-01-30T19:44:28+01:00\"},{\"id\":2,\"username\":\"Toto2\",\"title\":\"titre2\",\"content\":\"contenu2\",\"created_at\":\"2023-01-30T19:44:28+01:00\"}]}")), responseCode = "200")
	@ApiResponse(content = @Content(schema = @Schema(defaultValue = "")), responseCode = "401", description = "Unauthorized")
	public final ResponseEntity<Object> getPostsBySubjects() {
		final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
		final Optional<Users> user = userService.findByEmail(mail);
		if (user.isEmpty())
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
		final List<Long> subjectsIdsList = ((Set<Subject>) user.get().getSubjects()).stream()
				.map(this::convertSubjectToId).collect(Collectors.toList());
		// without subscribe
		if (subjectsIdsList.size() == 0) {
			final List<PostDto> postsDtoList = ((Collection<Post>) postService.getAllPosts()).stream()
					.map(this::convertPostToDto).collect(Collectors.toList());
			final HashMap<String, List<PostDto>> map = new HashMap<>();
			map.put("posts", postsDtoList);
			return ResponseEntity.ok().body(map);
		}
		// query
		List<Post> allPosts = postService.findBySubscribedSubjectRepository(subjectsIdsList);
		final List<PostDto> postsDtoList = ((List<Post>) allPosts).stream().map(this::convertPostToDto)
				.collect(Collectors.toList());
		final HashMap<String, List<PostDto>> mapCompleted = new HashMap<>();
		mapCompleted.put("posts", postsDtoList);
		return ResponseEntity.ok().body(mapCompleted);
	}

	/**
	 * User subscribe to a subject
	 * 
	 * @param id the subject id
	 * @return The HTTP response
	 */
	@PostMapping("/{subjectId}/user")
	@Operation(description = "User subscribe to a subject")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"User subscribed !\"\r\n" + "}")), responseCode = "201")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"The user already subscribes !\"\r\n"
			+ "}")), responseCode = "409", description = "Conflict")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Subject not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> subscribe(
			@Parameter(description = "The subject ID for which the user ID subscribed") @PathVariable("subjectId") Long id) {
		final HashMap<String, String> map = new HashMap<>();
		try {
			final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
			final Optional<Users> user = userService.findByEmail(mail);
			final Optional<Subject> subject = subjectService.findSubjectById(id);
			if (user.isEmpty())
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
			if (subject.isEmpty()) {
				map.put("message", "Subject not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
			final Subscription subscription = new Subscription(user.get().getId(), subject.get().getId());
			subscriptionService.createSubscription(subscription);
			map.put("message", "User subscribed !");
			return ResponseEntity.status(HttpStatus.CREATED).body(map);
		} catch (DataIntegrityViolationException ex) {
			map.put("message", "The user already subscribes !");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
		}
	}

	/**
	 * User no longer subscribe to a subject
	 * 
	 * @param subjectId the subject id
	 * @return The HTTP response
	 */
	@DeleteMapping("/{subjectId}/user")
	@Operation(description = "User no longer subscribe to a subject")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"User no longer subscribe !\"\r\n" + "}")), responseCode = "200")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Error during unsubscribe process\"\r\n"
			+ "}")), responseCode = "400", description = "Bad Request")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{}")), responseCode = "401", description = "Unauthorized")
	@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type = "object", defaultValue = "{\r\n"
			+ "  \"message\": \"Subscription not found\"\r\n" + "}")), responseCode = "404", description = "Not Found")
	public final ResponseEntity<Object> unsubscribe(
			@Parameter(description = "The subject ID for which the user ID no longer subscribe") @PathVariable("subjectId") Long subjectId) {
		final HashMap<String, String> map = new HashMap<>();
		try {
			final String mail = SecurityContextHolder.getContext().getAuthentication().getName();
			final Optional<Users> user = userService.findByEmail(mail);
			if (user.isEmpty())
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
			final Optional<Subscription> subscription = subscriptionService
					.getSubscriptionWithUserAndSubjectId(user.get().getId(), subjectId);
			if (subscription.isEmpty()) {
				map.put("message", "Subscription not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
			subscriptionService.deleteSubscriptionById(subscription.get().getId());
			map.put("message", "User no longer subscribe !");
			return ResponseEntity.ok().body(map);
		} catch (Exception ex) {
			map.put("message", "Error during unsubscribe process");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}
}
