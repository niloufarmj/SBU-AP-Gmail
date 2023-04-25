package AP.Controller;

import AP.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import same.Message;
import same.MessageType;
import same.User;

import java.awt.event.ActionEvent;
import java.io.IOException;

import static AP.App.currentUser;
import static AP.App.out;
import static AP.Model.Thread.Listener.serverRespond;
import static java.lang.Thread.sleep;

public class ForgetPasswordController {

    @FXML
    Label enterLabel, questionLabel;
    @FXML
    TextField usernameField, answerField;

    public void returnToLogin(javafx.event.ActionEvent actionEvent) throws IOException {
        new PageLoader().load("LogIn");
    }

    public void confirm(javafx.event.ActionEvent actionEvent) throws IOException, InterruptedException {

        if (enterLabel.isVisible()) {
            out.writeObject(new Message(new User(usernameField.getText(), ""), MessageType.Recovery));
            sleep(100);
            if (serverRespond instanceof Boolean) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "there isn't such a username");
                alert.showAndWait();
            } else {
                currentUser = (User) serverRespond;
                enterLabel.setVisible(false);
                usernameField.setVisible(false);
                questionLabel.setText("Recovery question: " + currentUser.getRecoveryQuestion());
                questionLabel.setVisible(true);
                answerField.setVisible(true);
            }
        }
        else if (questionLabel.isVisible()) {
            if (answerField.getText().equals(currentUser.getRecoveryAnswer())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "your password is: " + currentUser.getPassword());
                alert.showAndWait();
                new PageLoader().load("LogIn");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "your answer doesn't match :( ");
                alert.showAndWait();
            }

        }
    }

}
