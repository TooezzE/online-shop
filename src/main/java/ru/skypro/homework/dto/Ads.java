package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.entity.Ad;

import java.util.List;


@Data
public class Ads {

    private int count;
    private List<Ad> results;
}
