package com.example.silvio.sg_test.singleton;

import com.example.silvio.sg_test.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvo on 5/18/17.
 */

public class UsersList {
    private static UsersList instance;
    private List<User> users;

    private UsersList(){
        users = new ArrayList<>();
    }

    public static UsersList getInstance() {
        if (null == instance){
            instance = new UsersList();
        }
        return instance;
    }

    public static void setInstance(UsersList instance) {
        UsersList.instance = instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        user.setPassword(md5(user.getPassword()));
        users.add(user);
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
