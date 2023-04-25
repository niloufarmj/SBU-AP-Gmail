package AP.Controller;

import same.User;

public class userMaker {

    User makeTempUser (User currentUser) {
        User tempUser = new User(currentUser.getUsername() , currentUser.getPassword());
        tempUser.setBlockedUsers(currentUser.getBlockedUsers());
        tempUser.setName(currentUser.getName());
        tempUser.setLastName(currentUser.getLastName());
        tempUser.setPassword(currentUser.getPassword());
        tempUser.setAge(currentUser.getAge());
        tempUser.setCountry(currentUser.getCountry());
        tempUser.setGender(currentUser.getGender());
        tempUser.setPhoneNumber(currentUser.getPhoneNumber());
        tempUser.setRecoveryQuestion(currentUser.getRecoveryQuestion());
        tempUser.setRecoveryAnswer(currentUser.getRecoveryAnswer());
        tempUser.setRecoveryMail(currentUser.getRecoveryMail());
        tempUser.setProfilePhoto(currentUser.getProfilePhoto());
        return tempUser;
    }

}
