package ru.skypro.homework.entity;

import java.util.List;
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
    @Size(min = 8, max = 64)
    @Column(name = "description")
    private String description;
    @Column(name = "email")
    private String email;
    @Min(0)
    @Max(10000000)
    @Column(name = "price")
    private Integer price;
    @Size(min = 4, max = 32)
    @Column(name = "title")
    private String title;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Image image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Comment> comments;


    public Ad(Integer id, String description, String email, Integer price, String title, Image image, User user, List<Comment> comments) {
        this.id = id;
        this.description = description;
        this.email = email;
        this.price = price;
        this.title = title;
        this.image = image;
        this.user = user;
        this.comments = comments;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(id, ad.id) && Objects.equals(description, ad.description) && Objects.equals(email, ad.email) && Objects.equals(price, ad.price) && Objects.equals(title, ad.title) && Objects.equals(image, ad.image) && Objects.equals(user, ad.user) && Objects.equals(comments, ad.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, email, price, title, image, user, comments);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", image=" + image +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
