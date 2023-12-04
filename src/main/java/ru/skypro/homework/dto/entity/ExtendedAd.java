package ru.skypro.homework.dto.entity;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "ext_ads")
public class ExtendedAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int price;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String imageLink;
    private String title;

    public ExtendedAd(int id, int price, String authorFirstName, String authorLastName,
                      String description, String email, String imageLink, String title) {
        this.id = id;
        this.price = price;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.description = description;
        this.email = email;
        this.imageLink = imageLink;
        this.title = title;
    }

    public ExtendedAd() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedAd that = (ExtendedAd) o;
        return id == that.id
                && price == that.price
                && Objects.equals(authorFirstName, that.authorFirstName)
                && Objects.equals(authorLastName, that.authorLastName)
                && Objects.equals(description, that.description)
                && Objects.equals(email, that.email)
                && Objects.equals(imageLink, that.imageLink)
                && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, authorFirstName, authorLastName,
                description, email, imageLink, title);
    }

    @Override
    public String toString() {
        return "ExtendedAd{" +
                "id=" + id +
                ", price=" + price +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
