package ru.skypro.homework.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String filePath;
    private Long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(filePath, image.filePath) && Objects.equals(fileSize, image.fileSize) && Objects.equals(mediaType, image.mediaType) && Arrays.equals(data, image.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
