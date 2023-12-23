package ru.skypro.homework.service;


import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.exceptions.ForbiddenAccessException;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper mapper;

    public CommentService(CommentRepository commentRepository, AdRepository adRepository, UserRepository userRepository, CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    public Comments getCommentsOfAd(Integer adId) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Comments comments = new Comments();
        comments.setResults(ad.getComments());
        comments.setCount(ad.getComments().size());
        return comments;
    }


    public CommentDTO createComment(Integer adId, CreateOrUpdateComment text, String username) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        Comment comment = mapper.createOrUpdateCommentToComment(text);
        comment.setUser(user);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setAd(ad);
        commentRepository.save(comment);
        List<Comment> commentList = ad.getComments();
        commentList.add(comment);
        ad.setComments(commentList);
        adRepository.save(ad);

        return mapper.commentToCommentDTO(comment);
    }

    public void deleteComment(Integer adId, Integer commentId, String username) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if(!user.getUserComments().contains(comment) || !user.getAuthorities().contains("ROLE_ADMIN")) {
            throw new ForbiddenAccessException();
        } else {
            List<Comment> userComments = user.getUserComments();
            List<Comment> adComments = ad.getComments();
            userComments.remove(comment);
            adComments.remove(comment);
            user.setUserComments(userComments);
            ad.setComments(adComments);
            userRepository.save(user);
            adRepository.save(ad);
            commentRepository.deleteById(commentId);
        }
    }

    public CommentDTO editComment(Integer adId, Integer commentId, CreateOrUpdateComment text, String username) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if(!user.getUserComments().contains(comment)) {
            throw new ForbiddenAccessException();
        } else {
            List<Comment> userComments = user.getUserComments();
            List<Comment> adComments = ad.getComments();
            userComments.remove(comment);
            adComments.remove(comment);
            comment.setText(text.getText());
            userComments.add(comment);
            adComments.add(comment);
            user.setUserComments(userComments);
            ad.setComments(adComments);
            userRepository.save(user);
            adRepository.save(ad);
            commentRepository.deleteById(commentId);
        }
        return mapper.commentToCommentDTO(comment);
    }
}
