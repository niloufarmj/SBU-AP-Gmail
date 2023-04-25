package AP.Controller;

import AP.PageLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import same.Mail;
import same.Message;
import same.MessageType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static AP.App.currentUser;
import static AP.Controller.mainPanelController.selectedMail;
import static AP.Model.Thread.Listener.serverRespond;
import static AP.App.out;
import static java.lang.Thread.sleep;

public class ConversationPageController {
    @FXML
    ImageView userPhoto;
    @FXML
    ListView<Mail> convListView;
    @FXML
    Label userLabel;

    public void initialize() throws IOException, InterruptedException {
        userLabel.setText("your conversation with: " + selectedMail.getSender().getUsername());
        File file = new File("images/profile photos/" + selectedMail.getSender().getUsername() + ".png");
        Image profilePhoto = new Image(file.toURI().toString());
        userPhoto.setImage(new Image(file.toURI().toString()));
        System.out.println("here");
        out.writeObject(new Message(selectedMail , MessageType.GetConversation));
        sleep(200);
        ArrayList<Mail> conv = (ArrayList<Mail>) serverRespond;
        List<Mail> conversation = conv.stream().sorted(Comparator.comparing(Mail::getDate)).collect(Collectors.toList());
        convListView.setItems(FXCollections.observableList(conversation));
        convListView.setCellFactory(convListView -> new ListConversationCell());
    }

    public void returnToPanel(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("receivedMailPage");
    }

    public void sendMail(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("sendMessagePage");
    }

}
