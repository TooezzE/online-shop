package ru.skypro.homework.dto.entity;

import java.util.Objects;

import javax.persistence.*;


@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int authorId;
    private String imageLink;
    private int price;
    private String title;


    public Ad(int id, int authorId, String imageLink, int price, String title) {
        this.id = id;
        this.authorId = authorId;
        this.imageLink = imageLink;
        this.price = price;
        this.title = title;
    }

    public Ad() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return id == ad.id && authorId == ad.authorId && price == ad.price && Objects.equals(imageLink, ad.imageLink) && Objects.equals(title, ad.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, imageLink, price, title);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", imageLink='" + imageLink + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }
}
