package ru.skypro.homework.service.impl;
import org.springframework.data.jpa.repository.JpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.CommentService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(AdRepository adRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }
    /**
     * Метод получения списка всех комментариев объявления
     * Метод использует {@link JpaRepository#findById}
     * {@link CommentRepository#findAllByAd(Ad)}
     * {@link CommentMapper#toCommentDTO(Comment, User)}
     * @param adId - id объявления
     * @return возвращает DTO модели комментариев
     */
    //Метод получения списка всех комментариев объявления
    @Override
    public CommentsDTO getComments(Integer adId) {
        log.info("Getting comments for ad with ID: {}", adId);
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentRepository.findAllByAd(ad)) {
            commentDTOList.add(CommentMapper.INSTANCE.toCommentDTO(comment, comment.getAuthor()));
        }
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setResults(commentDTOList);
        commentsDTO.setCount(commentDTOList.size());
        commentsDTO.setCount(commentDTOList.size());
        return commentsDTO;
    }
    /**
     * Метод добавления комментария к объявлению
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link JpaRepository#findById(Object)}
     * {@link JpaRepository#save(Object)}
     * {@link CommentMapper#toCommentDTO(Comment, User)}
     * @param adId - id объявления
     * @param createOrUpdateCommentDTO - DTO модель класса {@link CreateOrUpdateCommentDTO};
     * @return возвращает DTO модель комментария
     */
    //Метод добавления комментария к объявлению
    @Override
    public CommentDTO addComment(Integer adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        log.info("Adding comment for ad with ID: {}", adId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Comment comment = new Comment();
        comment.setAd(ad);
        comment.setAuthor(user);
        comment.setText(createOrUpdateCommentDTO.getText());
        comment.setCreatedAt(System.currentTimeMillis());
        return CommentMapper.INSTANCE.toCommentDTO(commentRepository.save(comment), user);
    }
    /**
     * Метод удаляет комментарий
     * Метод использует {@link JpaRepository#deleteById(Object)}
     * @param adId - id объявления
     * @param commentId - id объявления
     */
    //Метод удаления комментария
    @Override
    public Void deleteComment(Integer adId, Integer commentId) {
        log.info("Deleting comment with ID: {} for ad with ID: {}", commentId, adId);
        commentRepository.deleteById(commentId);
        return null;
    }
    /**
     * Метод для изменения комментария
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link JpaRepository#findById(Object)}
     * {@link JpaRepository#save(Object)}
     * {@link CommentMapper#toCommentDTO(Comment, User)}
     * @param adId - id объявления
     * @param commentId - id комментария
     * @param createOrUpdateCommentDTO - DTO модель класса {@link CreateOrUpdateCommentDTO};
     * @return - возвращает DTO модель комментария
     */
    @Override
    public CommentDTO patchComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        log.info("Updating comment with ID: {} for ad with ID: {}", commentId, adId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.setText(createOrUpdateCommentDTO.getText());
        return CommentMapper.INSTANCE.toCommentDTO(commentRepository.save(comment),user);
    }
}
