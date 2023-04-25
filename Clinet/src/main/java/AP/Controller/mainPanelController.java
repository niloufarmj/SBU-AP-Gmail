package AP.Controller;

import AP.PageLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import same.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static AP.App.currentUser;
import static AP.App.out;
import static AP.Model.Thread.Listener.serverRespond;
import static java.lang.Thread.sleep;

public class mainPanelController {

    ObservableList<String> filters = FXCollections.observableArrayList("All" , "Important" , "Read" , "Unread" , "Unimportant");

    @FXML
    Label blackRecievedLabel , whiteRecievedLabel , blackSentLabel , whiteSentLabel , blackSettingsLabel , whiteSettingsLabel;
    @FXML
    ImageView recievedList , sentList , settingsList , recievedBack , sentBack , settingsBack , image;
    @FXML
    Label nameLabel , lastnameLabel , passwordLabel , gmailLabel , receivedFilterLabel , receivedSearchLabel , sentFilterLabel , sentSearchLabel;
    @FXML
    TextField nameField , passwordField , lastnameField , receivedSearchField , sentSearchField;
    @FXML
    Button confirmButton;
    @FXML
    Hyperlink changePhotoLink , deleteAccountLink , receivedRefreshLink , sentRefreshLink;
    @FXML
    ListView <Mail> receivedListView , sentListView;
    @FXML
    ChoiceBox<String> receivedFilterBox , sentFilterBox;

    List <Mail> receivedMails;
    List <Mail> sentMails;
    public static Mail selectedMail;


    public void initialize() throws IOException, InterruptedException {
        receivedFilterBox.setValue("All");
        receivedFilterBox.setItems(filters);
        sentFilterBox.setValue("All");
        sentFilterBox.setItems(filters);
        ActionEvent a = new ActionEvent();
        refreshReceivedList(a);
        refreshSentList(a);
    }

    public void confirmSetting(ActionEvent actionEvent) throws IOException {
        boolean changed = false;
        if (!nameField.getText().isEmpty()) {
            if (!nameField.getText().equals(currentUser.getName())) {
                currentUser.setName(nameField.getText());
                changed = true;
            }
        }
        if (!lastnameField.getText().isEmpty()) {
            if (!lastnameField.getText().equals(currentUser.getLastName())) {
                currentUser.setLastName(lastnameField.getText());
                changed = true;
            }
        }
        if (!passwordField.getText().isEmpty() && passwordField.getText().matches("^[a-zA-Z0-9]+$")) {
            if (!passwordField.getText().equals(currentUser.getPassword())) {
                currentUser.setPassword(passwordField.getText());
                changed = true;
            }
        }
        if (changed) {
            System.out.println(currentUser.getName());
            System.out.println(currentUser.getLastName());
            System.out.println(currentUser.getPassword());
            out.writeObject(new Message(new userMaker().makeTempUser(currentUser), MessageType.ChangeInfo));
        }


    }

    public void showRecievedList(MouseEvent mouseEvent) {
        //invisibles
        sentBack.setVisible(false);
        settingsBack.setVisible(false);
        sentList.setVisible(false);
        settingsList.setVisible(false);
        image.setVisible(false);
        nameLabel.setVisible(false);
        lastnameLabel.setVisible(false);
        gmailLabel.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        lastnameField.setVisible(false);
        nameField.setVisible(false);
        whiteSentLabel.setVisible(false);
        whiteSettingsLabel.setVisible(false);
        blackRecievedLabel.setVisible(false);
        confirmButton.setVisible(false);
        changePhotoLink.setVisible(false);
        deleteAccountLink.setVisible(false);
        sentFilterLabel.setVisible(false);
        sentRefreshLink.setVisible(false);
        sentSearchField.setVisible(false);
        sentFilterBox.setVisible(false);
        sentListView.setVisible(false);
        //visibles
        recievedBack.setVisible(true);
        recievedList.setVisible(true);
        whiteRecievedLabel.setVisible(true);
        blackSentLabel.setVisible(true);
        blackSettingsLabel.setVisible(true);
        receivedFilterLabel.setVisible(true);
        receivedRefreshLink.setVisible(true);
        receivedSearchField.setVisible(true);
        receivedFilterBox.setVisible(true);
        receivedListView.setVisible(true);
        receivedSearchLabel.setVisible(true);
    }

    public void showSentList(MouseEvent actionEvent) {
        //invisibles
        recievedList.setVisible(false);
        recievedBack.setVisible(false);
        settingsBack.setVisible(false);
        settingsList.setVisible(false);
        image.setVisible(false);
        nameLabel.setVisible(false);
        lastnameLabel.setVisible(false);
        gmailLabel.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        lastnameField.setVisible(false);
        nameField.setVisible(false);
        blackSentLabel.setVisible(false);
        whiteRecievedLabel.setVisible(false);
        whiteSettingsLabel.setVisible(false);
        confirmButton.setVisible(false);
        changePhotoLink.setVisible(false);
        deleteAccountLink.setVisible(false);
        receivedFilterLabel.setVisible(false);
        receivedRefreshLink.setVisible(false);
        receivedSearchField.setVisible(false);
        receivedFilterBox.setVisible(false);
        receivedListView.setVisible(false);
        //visibles
        sentList.setVisible(true);
        sentBack.setVisible(true);
        blackRecievedLabel.setVisible(true);
        blackSettingsLabel.setVisible(true);
        whiteSentLabel.setVisible(true);
        sentFilterLabel.setVisible(true);
        sentRefreshLink.setVisible(true);
        sentSearchField.setVisible(true);
        sentFilterBox.setVisible(true);
        sentListView.setVisible(true);
        sentSearchLabel.setVisible(true);
    }

    public void showSettings(MouseEvent actionEvent) {
        //invisibles
        sentList.setVisible(false);
        sentBack.setVisible(false);
        recievedBack.setVisible(false);
        recievedList.setVisible(false);
        blackSettingsLabel.setVisible(false);
        whiteSentLabel.setVisible(false);
        whiteRecievedLabel.setVisible(false);
        receivedFilterLabel.setVisible(false);
        receivedRefreshLink.setVisible(false);
        receivedSearchField.setVisible(false);
        receivedFilterBox.setVisible(false);
        sentFilterLabel.setVisible(false);
        sentRefreshLink.setVisible(false);
        sentSearchField.setVisible(false);
        sentFilterBox.setVisible(false);
        receivedListView.setVisible(false);
        sentListView.setVisible(false);
        sentSearchLabel.setVisible(false);
        receivedSearchLabel.setVisible(false);
        //visibles
        confirmButton.setVisible(true);
        settingsBack.setVisible(true);
        settingsList.setVisible(true);
        image.setVisible(true);
        nameLabel.setVisible(true);
        lastnameLabel.setVisible(true);
        gmailLabel.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        lastnameField.setVisible(true);
        nameField.setVisible(true);
        blackRecievedLabel.setVisible(true);
        blackSentLabel.setVisible(true);
        whiteSettingsLabel.setVisible(true);
        changePhotoLink.setVisible(true);
        deleteAccountLink.setVisible(true);
        nameField.setText(currentUser.getName());
        lastnameField.setText(currentUser.getLastName());
        passwordField.setText(currentUser.getPassword());
        gmailLabel.setText(currentUser.getUsername() + "@gmail.com");
        if (currentUser.getProfilePhoto() != null) {
            File file = new File("images/profile photos/" + currentUser.getUsername() + ".png");
            Image profilePhoto = new Image(file.toURI().toString());
            image.setImage(new Image(file.toURI().toString()));
        }
        //brye date nmidunm byad che knm
    }

    public void changePhoto(ActionEvent actionEvent) throws IOException {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("select an image");
        chooser.getExtensionFilters().add(imageFilter);
        File file =  chooser.showOpenDialog(null);
        if(file != null){
            image.setImage(new Image(file.getAbsoluteFile().toURI().toString(),130,130,true,true));
        }
        if (image.getImage() != null) {
            FileOutputStream userProfilePhoto = new FileOutputStream("images/profile photos/" + currentUser.getUsername() + ".png");
            Files.copy(file.toPath(), userProfilePhoto);
            File img = new File("images/profile photos/" + currentUser.getUsername() + ".png");
            Image photo = new Image(file.toURI().toString());
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            currentUser.setProfilePhoto(byteArrayOutputStream.toByteArray());
        }
    }

    public void sendMail(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("sendMessagePage");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        out.writeObject(new Message(currentUser, MessageType.Disconnect));
        currentUser = new User("" , "");
        new PageLoader().load("LogIn");
    }

    public void setPhoto(MouseEvent mouseEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("select an image");
        File file = chooser.showOpenDialog(PageLoader.getPrimaryStage());
        if(file != null){
            image.setImage(new Image(file.getAbsoluteFile().toURI().toString(),130,130,true,true));
        }

        BufferedImage bImage = ImageIO.read(new File(image.getImage().getUrl()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        currentUser.setProfilePhoto(data);
    }

    public void deleteAccount(ActionEvent actionEvent) throws IOException {
        out.writeObject(new Message(currentUser , MessageType.DeleteAccount));
        currentUser = new User("" , "");
        new PageLoader().load("LogIn");
    }

    public void selectReceivedMessage(MouseEvent mouseEvent) throws IOException {
        selectedMail = receivedListView.getSelectionModel().getSelectedItem();
        selectedMail.setRead(true);
        out.writeObject(new Message(selectedMail , MessageType.ReadMail , selectedMail.isRead()));
        new PageLoader().load("receivedMailPage");
    }

    public void refreshSentList(ActionEvent actionEvent) throws IOException, InterruptedException {
        out.writeObject(new Message(currentUser , MessageType.SentList));
        sleep(100);
        if (serverRespond instanceof List) {
            sentMails = (List<Mail>) serverRespond;
            Collections.reverse(sentMails);
            List<Mail> newList = sentMails;
            if (sentFilterBox.getValue().equals("Important")) {
                newList = sentMails.stream().filter(mail -> mail.isImportant()).collect(Collectors.toList());
            }
            if (sentFilterBox.getValue().equals("Unimportant"))
                newList = receivedMails.stream().filter(mail -> !mail.isImportant()).collect(Collectors.toList());
            if (sentFilterBox.getValue().equals("Read"))
                newList = receivedMails.stream().filter(mail -> mail.isRead()).collect(Collectors.toList());
            if (sentFilterBox.getValue().equals("Unread"))
                newList = receivedMails.stream().filter(mail -> !mail.isRead()).collect(Collectors.toList());
            if (!sentSearchField.getText().isEmpty()) {
                List<Mail> searchResult = new ArrayList<>();
                for (Mail mail : newList) {
                    if (mail.getSubject().toLowerCase().startsWith(sentSearchField.getText().toLowerCase()))
                        searchResult.add(mail);
                    else if (mail.getSender().getUsername().toLowerCase().startsWith(sentSearchField.getText().toString()))
                        searchResult.add(mail);
                }//end for
                newList = searchResult;
            }
            sentListView.setItems(FXCollections.observableList(newList));
            sentListView.setCellFactory(receivedListView -> new ListMessageCell());
        }
    }

    public void refreshReceivedList(ActionEvent actionEvent) throws IOException, InterruptedException {
        out.writeObject(new Message(currentUser , MessageType.ReceivedList));
        sleep(100);
        if (serverRespond instanceof List) {
            receivedMails = (List<Mail>) serverRespond;
            Collections.reverse(receivedMails);
            List<Mail> newList = receivedMails;
            if (receivedFilterBox.getValue().equals("Important")) {
                newList = receivedMails.stream().filter(mail -> mail.isImportant()).collect(Collectors.toList());
            }
            if (receivedFilterBox.getValue().equals("Unimportant"))
                newList = receivedMails.stream().filter(mail -> !mail.isImportant()).collect(Collectors.toList());
            if (receivedFilterBox.getValue().equals("Read"))
                newList = receivedMails.stream().filter(mail -> mail.isRead()).collect(Collectors.toList());
            if (receivedFilterBox.getValue().equals("Unread"))
                newList = receivedMails.stream().filter(mail -> !mail.isRead()).collect(Collectors.toList());
            if (!receivedSearchField.getText().isEmpty()) {
                List<Mail> searchResult = new ArrayList<>();
                for (Mail mail : newList) {
                    if (mail.getSubject().toLowerCase().startsWith(receivedSearchField.getText().toLowerCase()))
                        searchResult.add(mail);
                    else if (mail.getSender().getUsername().toLowerCase().startsWith(receivedSearchField.getText().toString()))
                        searchResult.add(mail);
                }//end for
                newList = searchResult;
            }
            receivedListView.setItems(FXCollections.observableList(newList));
            receivedListView.setCellFactory(receivedListView -> new ListMessageCell());
        }
    }

    public void selectSentMessage(MouseEvent mouseEvent) throws IOException {
        selectedMail = sentListView.getSelectionModel().getSelectedItem();
        selectedMail.setRead(true);
        out.writeObject(new Message(selectedMail , MessageType.ReadMail , selectedMail.isRead()));
        new PageLoader().load("receivedMailPage");
    }

}
