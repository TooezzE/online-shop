package ru.skypro.homework.dto;


import lombok.Data;

@Data
public class CommentDTO {

    private Integer pk;
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private String text;

}
