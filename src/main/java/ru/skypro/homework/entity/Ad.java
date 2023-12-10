package ru.skypro.homework.entity;

import java.util.Objects;

import javax.persistence.*;


@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String authorFirstName;
    private String authorLastName;
    private Integer authorId;
    private String description;
    private String email;
    private String imageLink;
    private int price;
    private String title;


    public Ad(Integer id,
              String authorFirstName,
              String authorLastName,
              int authorId,
              String description,
              String email,
              String imageLink,
              int price,
              String title) {
        this.id = id;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.authorId = authorId;
        this.description = description;
        this.email = email;
        this.imageLink = imageLink;
        this.price = price;
        this.title = title;
    }

    public Ad() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return authorId == ad.authorId && price == ad.price && Objects.equals(id, ad.id) && Objects.equals(authorFirstName, ad.authorFirstName) && Objects.equals(authorLastName, ad.authorLastName) && Objects.equals(description, ad.description) && Objects.equals(email, ad.email) && Objects.equals(imageLink, ad.imageLink) && Objects.equals(title, ad.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorFirstName, authorLastName, authorId, description, email, imageLink, price, title);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                ", authorId=" + authorId +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }
}
