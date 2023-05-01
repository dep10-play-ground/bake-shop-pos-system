package lk.ijse.dep10.application.model;

import lk.ijse.dep10.application.util.UserRole;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String userName;
    private String fullName;
    private ArrayList<String> contactNumbers = new ArrayList<>();

    private String address;

    public UserRole userRole;

    public User() {
    }

    public User(String userName, String fullName, ArrayList<String> contactNumbers, String address, UserRole userRole) {
        this.userName = userName;
        this.fullName = fullName;
        this.contactNumbers = contactNumbers;
        this.address = address;
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<String> getContactNumbers() {
        return contactNumbers;
    }

    public void setContactNumbers(ArrayList<String> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
