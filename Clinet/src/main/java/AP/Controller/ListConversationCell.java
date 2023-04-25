package AP.Controller;

import javafx.scene.control.ListCell;
import same.Mail;

import java.io.IOException;

public class ListConversationCell extends ListCell<Mail> {

    @Override
    public void updateItem(Mail mail, boolean empty) {
        super.updateItem(mail, empty);
        if (mail != null) {
            setStyle("-fx-background-color: #ffffff");
            try {
                setGraphic(new ConvItemController(mail).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
