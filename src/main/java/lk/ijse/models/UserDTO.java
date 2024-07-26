package lk.ijse.models;


public class UserDTO {
    private String userId;
    private String name;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public UserDTO() {
    }
}
