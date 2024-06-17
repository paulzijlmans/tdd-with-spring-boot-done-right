package de.rieckpil;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

  public List<Comment> findAll() {
    return List.of();
  }

  public UUID createComment(String content, String authorName) {
    return UUID.randomUUID();
  }
}
