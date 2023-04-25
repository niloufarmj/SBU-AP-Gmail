package AP.Controller;

import AP.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import same.Mail;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class mailCellController {
    private Mail mail;

    @FXML
    ImageView yellowStarImage , unreadIcon , readIcon;
    @FXML
    Label usernameLabel , subjectLabel , timeLabel;
    @FXML
    AnchorPane root;

    public mailCellController(Mail mail) throws IOException {
        this.mail = mail;
        new PageLoader().load("mailCell" , this);
    }

    public AnchorPane init() {
        usernameLabel.setText(mail.getSender().getUsername());
        subjectLabel.setText(mail.getSubject());
        if (!mail.isRead()) {
            usernameLabel.setStyle("-fx-font-weight: bold");
            subjectLabel.setStyle("-fx-font-weight: bold");
        }
        timeLabel.setText(mail.getDate());
        if (mail.isRead()) {
            readIcon.setVisible(true);
            unreadIcon.setVisible(false);
        }
        else {
            unreadIcon.setVisible(true);
            readIcon.setVisible(false);
        }
        if (mail.isImportant()) {
            yellowStarImage.setVisible(true);
        }
        else
            yellowStarImage.setVisible(false);
        return root;
    }

    public void setImportant(MouseEvent mouseEvent) {
        if (yellowStarImage.isVisible())
            mail.setImportant(false);
        else
            mail.setImportant(true);
    }

    public void setRead(MouseEvent mouseEvent) {
        readIcon.setVisible(true);
        unreadIcon.setVisible(false);
        mail.setRead(true);
    }

    public void setUnread(MouseEvent mouseEvent) {
        unreadIcon.setVisible(true);
        readIcon.setVisible(false);
        mail.setRead(false);
    }

}
