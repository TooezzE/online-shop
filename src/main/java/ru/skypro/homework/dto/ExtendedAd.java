package ru.skypro.homework.dto;

import lombok.Data;


@Data
public class ExtendedAd {

    private int pk;
    private int price;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String title;

}
