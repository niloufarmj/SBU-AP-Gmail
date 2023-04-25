package AP.Controller;

import AP.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import same.Mail;

import java.awt.*;
import java.io.IOException;

import static AP.App.currentUser;

public class ConvItemController {
    private Mail mail;
    @FXML
    Label textBox , timeLabel;
    @FXML
    AnchorPane root;

    public ConvItemController(Mail mail) throws IOException {
        this.mail = mail;
        if (mail.getSender().equals(currentUser))
            new PageLoader().load("ConvGreenItem" , this);
        else if (mail.getReciever().equals(currentUser.getUsername()))
            new PageLoader().load("ConvRedItem" , this);
    }

    public AnchorPane init() {
        textBox.setText(mail.getTextMessage());
        timeLabel.setText(mail.getDate());
        return root;
    }

}
