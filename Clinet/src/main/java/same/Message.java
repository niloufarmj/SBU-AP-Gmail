package same;

import java.io.Serializable;

public class Message implements Serializable {

    User user;
    Mail mail;
    MessageType messageType;
    Boolean flag;

    public Message(User user , MessageType messageType) {
        this.messageType = messageType;
        this.user = user;
    }

    public Message(Mail mail , MessageType messageType) {
        this.messageType = messageType;
        this.mail = mail;
    }

    public Message(Mail mail , MessageType messageType , Boolean flag) {
        this.messageType = messageType;
        this.mail = mail;
        this.flag = flag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}