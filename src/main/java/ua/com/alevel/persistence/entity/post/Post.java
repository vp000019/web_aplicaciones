package ua.com.alevel.persistence.entity.post;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.user.BaseUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BaseUser baseUser;

    @Column(name = "path_image")
    private String imagePath;

    @Column(name = "name", nullable = false)
    private String namePost;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "date")
    private String date;

    @ManyToMany(mappedBy = "travelsUser")
    private Set<BaseUser> travelsPost;

    @Column(name = "visible")
    private Boolean visible;

    public Post() {
        this.visible = true;
        this.travelsPost = new HashSet<>();
    }

    public String getDate() {
        return date;
    }

    public Set<BaseUser> getTravelsPost() {
        return travelsPost;
    }

    public void setTravelsPost(Set<BaseUser> travelsPost) {
        this.travelsPost = travelsPost;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public BaseUser getUserCustomer() {
        return baseUser;
    }

    public void setUserCustomer(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BaseUser getBaseUser() {
        return baseUser;
    }

    public void setBaseUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(imagePath, post.imagePath) && Objects.equals(namePost, post.namePost) && Objects.equals(description, post.description) && Objects.equals(price, post.price) && Objects.equals(visible, post.visible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imagePath, namePost, description, price, visible);
    }
}
