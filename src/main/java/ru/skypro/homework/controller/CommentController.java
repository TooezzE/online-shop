package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@Controller
@RequestMapping("/ads")
public class CommentController {

    @GetMapping("/{adId}/comments")
    public Comments getAdComments(@PathVariable int adId) {
        return new Comments();
    }

    @PostMapping("/{adId}/comments")
    public CommentDTO createComment(@PathVariable int adId, @RequestBody CreateOrUpdateComment text) {
        return new CommentDTO();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int adId, @PathVariable int commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public CommentDTO editComment(@PathVariable int adId, @PathVariable int commentId,
                               @RequestBody CreateOrUpdateComment text) {
        return new CommentDTO();
    }
}


