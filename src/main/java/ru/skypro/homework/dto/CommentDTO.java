package ru.skypro.homework.dto;


import lombok.Data;

@Data
public class CommentDTO {

    private int pk;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private int createdAt;
    private String text;

}
