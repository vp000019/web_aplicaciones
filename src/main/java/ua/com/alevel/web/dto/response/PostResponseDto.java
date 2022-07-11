package ua.com.alevel.web.dto.response;

import ua.com.alevel.persistence.entity.post.Post;

public class PostResponseDto extends ResponseDto {

    private String name;
    private Double price;
    private String pathToImage;
    private String description;
    private String date;
    private Long userId;

    public PostResponseDto(Post post) {
        setId(post.getId());
        setVisible(post.getVisible());
        setCreated(post.getCreated());
        setUpdated(post.getUpdated());
        this.date = post.getDate();
        this.userId = post.getBaseUser().getId();
        this.name = post.getNamePost();
        this.price = post.getPrice();
        this.pathToImage = post.getImagePath();
        this.description = post.getDescription();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
