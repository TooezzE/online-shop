package ru.skypro.homework.dto;


import lombok.Data;

@Data
public class CreateOrUpdateComment {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
