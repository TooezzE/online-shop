package ru.skypro.homework.mappers;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;

@Service
public class CommentMapper {

     public CommentDTO commentToCommentDTO(Comment comment) {
         CommentDTO dto = new CommentDTO();
         dto.setPk(comment.getId());
         dto.setAuthorImage("/images/get/" + comment.getUser().getImage().getId());
         dto.setText(comment.getText());
         dto.setCreatedAt(comment.getCreatedAt());

         return dto;
     }

     public CreateOrUpdateComment commentToCreateOrUpdateComment(Comment comment) {
         CreateOrUpdateComment dto = new CreateOrUpdateComment();
         dto.setText(comment.getText());

         return dto;
     }

     public Comment commentDTOToComment(CommentDTO dto) {
         Comment comment = new Comment();
         comment.setId(dto.getPk());
         comment.setCreatedAt(dto.getCreatedAt());
         comment.setText(dto.getText());

         return comment; // not all fields
     }

     public Comment createOrUpdateCommentToComment(CreateOrUpdateComment dto) {
         Comment comment = new Comment();
         comment.setText(dto.getText());

         return comment; // not all fields
     }
}
