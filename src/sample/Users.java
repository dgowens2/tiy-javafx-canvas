package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DTG2 on 09/11/16.
 */
public class Users {

    private String username;
    private String fullName;
    private int userId;

    public ArrayList<Users> userList = new ArrayList<Users>();

    public Users(List<Users> incomingUserList) {
        userList = new ArrayList<Users>(incomingUserList);
    }

    public  Users(){
    }

    public Users(String username, String fullName, int userId) {
        this.username = username;
        this.fullName = fullName;
        this.userId = userId;
    }

    public Users(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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



