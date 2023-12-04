package ru.skypro.homework.mapperinterfases;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.entity.Comment;
@Mapper
public interface CommentMapperInterface {

    @Mapping(target = "id", source = "results")
    Comment commentEntity(Comments commentsDto);

    @Mapping(target = "results", source = "id")
    Comments commentsDto(Comment commentEntity);

    @Mapping(target = "text", source = "text")
    Comment commentEntity(CreateOrUpdateComment createOrUpdateCommentDto);

    @Mapping(target = "text", source = "text")
    CreateOrUpdateComment createOrUpdateCommentDto(Comment commentEntity);
}
