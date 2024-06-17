package de.rieckpil;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static reactor.core.publisher.Mono.when;

/**
 * Develop a REST API to retrieve and create comments. Everybody should be able to retrieve comments
 * but only logged-in users with the role ADMIN can create a comment.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

  private final CommentService commentService;

  public CommentApiController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public List<Comment> getAllComments() {
    return commentService.findAll();
  }

  @PostMapping
  public ResponseEntity<Void> createComment(
    @Valid @RequestBody CommentCreationRequest request,
    Authentication authentication,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    UUID newCommentId = commentService
      .createComment(request.content(), authentication.getName());

    UriComponents uriComponents = uriComponentsBuilder
      .path("/api/comments/{id}")
      .buildAndExpand(newCommentId);

    return ResponseEntity.created(uriComponents.toUri()).build();
  }
}
