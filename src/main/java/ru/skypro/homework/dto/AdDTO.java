package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdDTO {

    private Integer pk;
    private Integer author;
    private String image;
    private Integer price;
    private String title;

}
