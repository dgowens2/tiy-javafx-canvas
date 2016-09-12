package sample;

/**
 * Created by DTG2 on 09/11/16.
 */
public class Users {

    private String username;
    private String fullName;
    private int userId;

//    public  Users{
//
//    }

    public Users(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users(String username, String fullName, int userId) {
        this.username = username;
        this.fullName = fullName;
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}



