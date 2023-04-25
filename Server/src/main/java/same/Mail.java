package same;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Mail implements Serializable {

    private final static long serialVersionUID = 100L;
    User sender;
    String reciever;
    String  date;
    String textMessage;
    byte[] attachedPhoto;
    byte[] attachedMusic;
    byte[] attachedVideo;
    String subject;
    boolean isImportant;
    boolean isRead;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return isImportant ==
                Objects.equals(sender, mail.sender) &&
                Objects.equals(reciever, mail.reciever) &&
                Objects.equals(subject, mail.subject);
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public byte[] getAttachedPhoto() {
        return attachedPhoto;
    }

    public void setAttachedPhoto(byte[] attachedPhoto) {
        this.attachedPhoto = attachedPhoto;
    }

    public byte[] getAttachedMusic() {
        return attachedMusic;
    }

    public void setAttachedMusic(byte[] attachedMusic) {
        this.attachedMusic = attachedMusic;
    }

    public byte[] getAttachedVideo() {
        return attachedVideo;
    }

    public void setAttachedVideo(byte[] attachedVideo) {
        this.attachedVideo = attachedVideo;
    }

}
