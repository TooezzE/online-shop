package ru.skypro.homework.entity;

import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer authorId;
    @Size(min = 8, max = 64)
    private String description;
    private String email;
    private String imageLink;
    @Min(0)
    @Max(10000000)
    private Integer price;
    @Size(min = 4, max = 32)
    private String title;


    public Ad(Integer id,
              int authorId,
              String description,
              String email,
              String imageLink,
              int price,
              String title) {
        this.id = id;
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
        return authorId == ad.authorId && price == ad.price && Objects.equals(id, ad.id) && Objects.equals(description, ad.description) && Objects.equals(email, ad.email) && Objects.equals(imageLink, ad.imageLink) && Objects.equals(title, ad.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, description, email, imageLink, price, title);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }
}
