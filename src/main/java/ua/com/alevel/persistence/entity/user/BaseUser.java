package ua.com.alevel.persistence.entity.user;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.post.Post;
import ua.com.alevel.persistence.types.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class BaseUser extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "baseUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Post> posts;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
    })
    @JoinTable(name = "travels",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> travelsUser;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public BaseUser() {
        super();
        this.enabled = true;
        this.travelsUser = new HashSet<>();
        this.posts = new HashSet<>();
    }

//    Добавление путешествия(при нажатии на buy
    public void addTravel(Post post) {
        this.travelsUser.add(post);
        post.getTravelsPost().add(this);
    }

    public void removeTravel(Post post) {
        post.getTravelsPost().remove(this);
        this.travelsUser.remove(post);
    }

    public Set<Post> getTravelsUser() {
        return travelsUser;
    }

    public void setTravelsUser(Set<Post> travelsUser) {
        this.travelsUser = travelsUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void addPost(Post post) {
        post.setUserCustomer(this);
        this.posts.add(post);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser user = (BaseUser) o;
        return Objects.equals(email, user.email) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posts);
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
