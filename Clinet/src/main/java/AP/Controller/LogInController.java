package AP.Controller;

import javafx.scene.input.MouseEvent;
import same.Message;
import same.MessageType;
import AP.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import same.User;

import java.io.IOException;
import java.util.Calendar;

import static AP.App.currentUser;
import static AP.App.out;
import static AP.Model.Thread.Listener.serverRespond;
import static java.lang.Thread.sleep;


public class LogInController {
    @FXML
    Label whiteSignupLabel;
    @FXML
    Label blackSignupLabel;
    @FXML
    Label whiteLoginLabel;
    @FXML
    Label BlackLoginLabel;
    @FXML
    Label repeatedUsernameLabel;
    @FXML
    Label unvalidDateLabel;
    @FXML
    Label youngerLabel;
    @FXML
    Label wrongPasswordConfirmationLabel , wrongPassLoginLabel;
    @FXML
    Label invalidUsernameLabel1;
    @FXML
    Label invalidUsernameLabel2;
    @FXML
    Hyperlink forgotPassLink;
    @FXML
    ImageView logo;
    @FXML
    ImageView signupBack;
    @FXML
    ImageView loginBack;
    @FXML
    ImageView signupBox;
    @FXML
    ImageView userLogo;
    @FXML
    ImageView mailLogo;
    @FXML
    ImageView dateLogo;
    @FXML
    ImageView passLogo1;
    @FXML
    ImageView passLogo2;
    @FXML
    ImageView showPassLogo1;
    @FXML
    ImageView showPassLogo2;
    @FXML
    ImageView loginBox;
    @FXML
    ImageView userLoginLogo;
    @FXML
    ImageView passLoginLogo;
    @FXML
    ImageView showPassLoginLogo;
    @FXML
    TextField nameField , lastnameField , usernameField , visiblePassField , visiblePassConfirmField , usernameLoginField;
    @FXML
    PasswordField passwordField , passwordConfirmField , passwordLoginField;
    @FXML
    Button signupButton , loginButton;
    @FXML
    DatePicker dateField;
    @FXML
    Hyperlink haveAcountLink;
    @FXML
    Hyperlink noAccountLink;
    @FXML
    Label emptyFieldsLabel , invalidPasswordLabel1 , invalidPasswordLabel2 , shortPasswordLabel;

    public void visibleLogin(javafx.scene.input.MouseEvent actionEvent) throws Exception {
        loginBack.setVisible(true);
        whiteLoginLabel.setVisible(true);
        loginBox.setVisible(true);
        usernameLoginField.setVisible(true);
        showPassLoginLogo.setVisible(true);
        userLoginLogo.setVisible(true);
        passwordLoginField.setVisible(true);
        forgotPassLink.setVisible(true);
        loginButton.setVisible(true);
        blackSignupLabel.setVisible(true);
        passLoginLogo.setVisible(true);
        noAccountLink.setVisible(true);

        BlackLoginLabel.setVisible(false);
        whiteSignupLabel.setVisible(false);
        signupBack.setVisible(false);
        signupBox.setVisible(false);
        userLogo.setVisible(false);
        mailLogo.setVisible(false);
        dateLogo.setVisible(false);
        passLogo2.setVisible(false);
        passLogo1.setVisible(false);
        nameField.setVisible(false);
        lastnameField.setVisible(false);
        usernameField.setVisible(false);
        dateField.setVisible(false);
        passwordField.setVisible(false);
        passwordConfirmField.setVisible(false);
        signupButton.setVisible(false);
        haveAcountLink.setVisible(false);

        hideMessage(actionEvent);
    }

    public void visibleSignup (javafx.event.ActionEvent actionEvent) {
        loginBack.setVisible(false);
        whiteLoginLabel.setVisible(false);
        loginBox.setVisible(false);
        usernameLoginField.setVisible(false);
        showPassLoginLogo.setVisible(false);
        userLoginLogo.setVisible(false);
        passwordLoginField.setVisible(false);
        forgotPassLink.setVisible(false);
        loginButton.setVisible(false);
        blackSignupLabel.setVisible(false);
        passLoginLogo.setVisible(false);
        noAccountLink.setVisible(false);
        wrongPassLoginLabel.setVisible(false);

        BlackLoginLabel.setVisible(true);
        whiteSignupLabel.setVisible(true);
        signupBack.setVisible(true);
        signupBox.setVisible(true);
        userLogo.setVisible(true);
        mailLogo.setVisible(true);
        dateLogo.setVisible(true);
        passLogo2.setVisible(true);
        passLogo1.setVisible(true);
        nameField.setVisible(true);
        lastnameField.setVisible(true);
        usernameField.setVisible(true);
        dateField.setVisible(true);
        passwordField.setVisible(true);
        passwordConfirmField.setVisible(true);
        signupButton.setVisible(true);
        haveAcountLink.setVisible(true);
    }

    public void login (javafx.event.ActionEvent actionEvent) throws IOException, ClassNotFoundException, InterruptedException {
        currentUser.setUsername(usernameLoginField.getText());
        currentUser.setPassword(passwordLoginField.getText());
        out.writeObject(new Message(currentUser , MessageType.LogIn));
        sleep(1000);
        if (serverRespond instanceof User) {
            currentUser = (User) serverRespond;
            new PageLoader().load("mainPanel");
        }
        else
            wrongPassLoginLabel.setVisible(true);
    }

    public void signup (javafx.event.ActionEvent actionEvent) throws IOException, ClassNotFoundException, InterruptedException {
        if (nameField.getText().isEmpty() || lastnameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordConfirmField.getText().isEmpty())
            emptyFieldsLabel.setVisible(true);
        else if (!passwordField.getText().equalsIgnoreCase(passwordConfirmField.getText()))
            wrongPasswordConfirmationLabel.setVisible(true);
        else if (!usernameField.getText().matches("^[a-zA-Z0-9.]+$")) {
            invalidUsernameLabel1.setVisible(true);
            invalidUsernameLabel2.setVisible(true);
        } else if (!passwordField.getText().matches("^[a-zA-Z0-9]+$")) {
            invalidPasswordLabel1.setVisible(true);
            invalidPasswordLabel2.setVisible(true);
        } else if (passwordField.getText().length() < 8)
            shortPasswordLabel.setVisible(true);
        else if (Calendar.getInstance().get(Calendar.YEAR) - dateField.getValue().getYear() < 13)
            youngerLabel.setVisible(true);
        else {
            if (passwordField.isVisible()) {
                currentUser = new User(usernameField.getText(), passwordField.getText());
            } else {
                currentUser = new User(usernameField.getText(), visiblePassField.getText());
            }
                currentUser.setAge(Calendar.getInstance().get(Calendar.YEAR) - dateField.getValue().getYear());
                currentUser.setName(nameField.getText());
                currentUser.setLastName(lastnameField.getText());
                out.writeObject(new Message(currentUser , MessageType.SignUp));
            sleep(1000);
            if ((Boolean) serverRespond) {
                new PageLoader().load("additionalInformation");
            }
            else {
                repeatedUsernameLabel.setVisible(true);
            }
        }
    }

    public void hideMessage (javafx.scene.input.MouseEvent actionEvent){
            invalidUsernameLabel1.setVisible(false);
            invalidUsernameLabel2.setVisible(false);
            emptyFieldsLabel.setVisible(false);
            wrongPasswordConfirmationLabel.setVisible(false);
            invalidPasswordLabel1.setVisible(false);
            invalidPasswordLabel2.setVisible(false);
            shortPasswordLabel.setVisible(false);
            youngerLabel.setVisible(false);
            repeatedUsernameLabel.setVisible(false);
        }

    public void showPass (javafx.scene.input.MouseEvent actionEvent) {
        if (passwordField.isVisible()) {
            passwordField.setVisible(false);
            visiblePassField.setVisible(true);
            visiblePassField.setText(passwordField.getText());
        } else {
            visiblePassField.setVisible(false);
            passwordField.setVisible(true);
            passwordField.setText(visiblePassField.getText());
        }
    }

    public void showPassConfirm (MouseEvent mouseEvent) {
        if (passwordConfirmField.isVisible()) {
            passwordConfirmField.setVisible(false);
            visiblePassConfirmField.setVisible(true);
            visiblePassConfirmField.setText(passwordConfirmField.getText());
        } else {
            visiblePassConfirmField.setVisible(false);
            passwordConfirmField.setVisible(true);
            passwordConfirmField.setText(visiblePassConfirmField.getText());
        }

    }

    public void forgotPass (javafx.event.ActionEvent actionEvent) throws IOException {
        new PageLoader().load("ForgetPassword");
    }

}
