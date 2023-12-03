package ru.skypro.homework.dto.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

//@Entity
//@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int authorId;
    private String authorImgLink;
    private String authorFirstName;
    private int createdAt;
    private String text;


    public Comment(int id, int authorId, String authorImgLink,
                   String authorFirstName, int createdAt, String text) {
        this.id = id;
        this.authorId = authorId;
        this.authorImgLink = authorImgLink;
        this.authorFirstName = authorFirstName;
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

    public String getAuthorImgLink() {
        return authorImgLink;
    }

    public void setAuthorImgLink(String authorImgLink) {
        this.authorImgLink = authorImgLink;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
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
                && Objects.equals(authorImgLink, comment.authorImgLink)
                && Objects.equals(authorFirstName, comment.authorFirstName)
                && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, authorImgLink, authorFirstName, createdAt, text);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", authorImgLink='" + authorImgLink + '\'' +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                '}';
    }
}
