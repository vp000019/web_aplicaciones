package ua.com.alevel.web.dto.response;

import ua.com.alevel.persistence.entity.user.BaseUser;
import ua.com.alevel.persistence.types.Role;

public class UserResponseDto extends ResponseDto {

    private String email;
    private String login;
    private String firstName;
    private String lastName;
    private int points;
    private Role role;
    private String phoneNumber;

    public UserResponseDto(BaseUser user) {
        setId(user.getId());
        setCreated(user.getCreated());
        setUpdated(user.getUpdated());
        setVisible(user.isEnabled());
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.points = user.getPosts().size();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.login = user.getLogin();
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
