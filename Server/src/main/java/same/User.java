package same;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    private static final long serialVersionUID = 19L;
    private String username;
    private String name;
    private String lastName;
    private int age;
    private String phoneNumber;

    private String country;
    private String password;
    private String gender;
    private String recoveryMail;
    private String recoveryQuestion;
    private String recoveryAnswer;
    private byte[] profilePhoto;
    private Set<String> blockedUsers = new HashSet<>();

    public Set<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getRecoveryMail() {
        return recoveryMail;
    }

    public String getRecoveryQuestion() {
        return recoveryQuestion;
    }

    public String getRecoveryAnswer() {
        return recoveryAnswer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRecoveryMail(String recoveryMail) {
        this.recoveryMail = recoveryMail;
    }

    public void setRecoveryQuestion(String recoveryQuestion) {
        this.recoveryQuestion = recoveryQuestion;
    }

    public void setRecoveryAnswer(String recoveryAnswer) {
        this.recoveryAnswer = recoveryAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof  User)
            return username.equals(((User) o).username);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return username;
    }
}