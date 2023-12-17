package ru.skypro.homework.entity;

import javax.persistence.Id;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer authorId;
    private Long createdAt;
    private String text;
//    @ManyToOne
//    @JoinColumn(name="user_id",referencedColumnName = "id")
//    private User user;
//    @ManyToOne
//    @JoinColumn(name="ad_id", referencedColumnName = "id")
//    private Ad ad;
// Переделать конструктор!!!


    public Comment(int id, Integer authorId, Long createdAt, String text) {
        this.id = id;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.text = text;
    }

    public Comment() {
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id
                && authorId == comment.authorId
                && createdAt == comment.createdAt
                && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, createdAt, text);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                '}';
    }
}
