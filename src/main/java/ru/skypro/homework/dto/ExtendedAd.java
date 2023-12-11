package ru.skypro.homework.dto;

import lombok.Data;


@Data
public class ExtendedAd {

    private Integer pk;
    private Integer price;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String title;

}
