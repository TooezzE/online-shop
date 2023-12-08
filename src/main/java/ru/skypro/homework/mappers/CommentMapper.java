package ru.skypro.homework.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentMapper {

    @Autowired
    private CommentRepository repository;
    public Comments getComments() {
        Comments comments = new Comments();
        List<Comment> res = new ArrayList<>(repository.findAll());
        comments.setResults(res);
        comments.setCount(res.size());
        return comments;
    }

    public Comment createOrUpdateCommentConverter(CreateOrUpdateComment createOrUpdateComment) {
        Comment comment = new Comment();
        comment.setText(createOrUpdateComment.getText());
        return comment;
    }
    
}
