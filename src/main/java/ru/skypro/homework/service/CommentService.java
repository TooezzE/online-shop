package ru.skypro.homework.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.stream.Collectors;

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

    /**
     * Метод получения списка всех комментариев объявления
     * Метод использует {@link JpaRepository#findById(Object)}
     * @param adId - id объявления
     * @return возвращает DTO модели комментариев
     */
    public Comments getCommentsOfAd(Integer adId) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Comments comments = new Comments();
        comments.setResults(ad.getComments().stream().map(c -> mapper.commentToCommentDTO(c)).collect(Collectors.toList()));
        comments.setCount(ad.getComments().size());
        return comments;
    }
    /**
     * Метод добавления комментария к объявлению
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link JpaRepository#findById(Object)}
     * {@link JpaRepository#save(Object)}
     * {@link CommentMapper#commentToCommentDTO(Comment)}
     * @param adId - id объявления
     * @param text - DTO модель класса {@link CreateOrUpdateComment};
     * @return возвращает DTO модель комментария
     */

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
    /**
     * Метод удаляет комментарий
     * Метод использует {@link JpaRepository#deleteById(Object)}
     * @param adId - id объявления
     * @param commentId - id объявления
     */
    public void deleteComment(Integer adId, Integer commentId, String username) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
//        if(!user.getUserComments().contains(comment) || !user.getAuthorities().contains("ROLE_ADMIN")) {
//            throw new ForbiddenAccessException();
//        } else {
//            List<Comment> userComments = user.getUserComments();
//            List<Comment> adComments = ad.getComments();
//            userComments.remove(comment);
//            adComments.remove(comment);
//            user.setUserComments(userComments);
//            ad.setComments(adComments);
            userRepository.save(user);
            adRepository.save(ad);
            commentRepository.deleteById(comment.getId());
        }
//    }
    /**
     * Метод для изменения комментария
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link JpaRepository#findById(Object)}
     * {@link JpaRepository#save(Object)}
     * {@link CommentMapper#commentToCommentDTO(Comment)}
     * @param adId - id объявления
     * @param commentId - id комментария
     * @param text - DTO модель класса {@link CreateOrUpdateComment};
     * @return - возвращает DTO модель комментария
     */
    public CommentDTO editComment(Integer adId, Integer commentId, CreateOrUpdateComment text, String username) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.setText(text.getText());
        commentRepository.save(comment);
//        if(!user.getUserComments().contains(comment)) {
//            throw new ForbiddenAccessException();
//        } else {
//            List<Comment> userComments = user.getUserComments();
//            List<Comment> adComments = ad.getComments();
//            userComments.remove(comment);
//            adComments.remove(comment);
//            comment.setText(text.getText());
//            userComments.add(comment);
//            adComments.add(comment);
//            user.setUserComments(userComments);
//            ad.setComments(adComments);
//            userRepository.save(user);
//            adRepository.save(ad);
//            commentRepository.deleteById(commentId);
//            commentRepository.save(comment);
//        }
        return mapper.commentToCommentDTO(comment);
    }
}
