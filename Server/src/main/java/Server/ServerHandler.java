package Server;

import same.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerHandler {
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    Socket socket;
    ArrayList<User> allUsers = new ArrayList<>();

    public ServerHandler(Socket serverSocket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException {
        this.socket = serverSocket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectInputStream getInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return objectOutputStream;
    }

    public void getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("time: [" + dateFormat.format(date) + "]");
    }

    public void handle(Object request) throws IOException, ClassNotFoundException {
        //bbinim darkhaste client chi bude
        if (request instanceof Message) {
            Message clientRequest = (Message) request;
            switch (clientRequest.getMessageType()) {
                case SignUp:
                    //new FileHandler().write("user-pass.txt", allUsers);
                    //vase avlin bar tu file ye arrayliste khali nvshtim va az un bbaad uno por krdim
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    Boolean accepted = true;
                    for (User user : allUsers)
                        if (user.equals(clientRequest.getUser()))
                            accepted = false;
                    objectOutputStream.writeObject(accepted);
                    objectOutputStream.flush();
                    if (accepted) {
                        System.out.println("[" + clientRequest.getUser() + "]" + " [SignUp]");
                        getDate();
                    }
                    break;
                case LogIn:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    Boolean accepted1 = false;
                    for (User user : allUsers) {
                        if (user.equals(clientRequest.getUser())) {
                            if (user.getPassword().equals(clientRequest.getUser().getPassword())) {
                                objectOutputStream.writeObject(user);
                                System.out.println("[" + clientRequest.getUser() + "]" + " [LogIn]");
                                getDate();
                                accepted1 = true;
                            }
                        }
                    }
                    if (!accepted1) {
                        try {
                            objectOutputStream.writeObject(accepted1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    allUsers.remove(new User("alavi" , ""));
                    allUsers.remove(new User("naghavi" , ""));
                    new FileHandler().write("user-pass.txt", allUsers);
                    break;
                case AdditionalSignUp:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    clientRequest.getUser().setBlockedUsers(new HashSet<>());
                    System.out.println(clientRequest.getUser().getRecoveryQuestion());
                    allUsers.add(clientRequest.getUser());
                    new FileHandler().write("user-pass.txt", allUsers);
                    Files.createDirectory(Paths.get("src/main/java/DataBase/" + clientRequest.getUser()));
                    File f1 = new File("src/main/java/Database/" + clientRequest.getUser() + "/sent.txt");
                    File f2 = new File("src/main/java/Database/" + clientRequest.getUser() + "/received.txt");
                    f1.createNewFile();
                    f2.createNewFile();
                    new FileHandler().write("src/main/java/Database/" + clientRequest.getUser() + "/sent.txt" , new ArrayList<Mail>());
                    new FileHandler().write("src/main/java/Database/" + clientRequest.getUser() + "/received.txt" , new ArrayList<Mail>());
                    break;
                case Disconnect:
                    System.out.println("[" + clientRequest.getUser() + "]" + " [LogOut]");
                    getDate();
                    break;
                case DeleteAccount:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    System.out.print("[" + clientRequest.getUser() + "]" + " [DeleteAccount]");
                    getDate();
                    allUsers.remove(clientRequest.getUser());
                    Files.delete(Paths.get("src/main/java/DataBase" + clientRequest.getUser()));
                    new FileHandler().write("user-pass.txt", allUsers);
                    break;
                case ChangeInfo:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    for (User user : allUsers) {
                        if (clientRequest.getUser().getUsername().equals(user.getUsername())) {
                            allUsers.remove(user);
                            allUsers.add(clientRequest.getUser());
                            System.out.println("[" + clientRequest.getUser() + "]" + " [ChangeInformation]");
                            getDate();
                            break;
                        }
                    }
                    new FileHandler().write("user-pass.txt", allUsers);
                    break;
                case ReceivedList:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    for (User user : allUsers) {
                        if (clientRequest.getUser().equals(user)) {
                            ArrayList<Mail> receivedMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getUser() + "/received.txt");
                            objectOutputStream.writeObject(receivedMails);
                        }
                    }
                    break;
                case SentList:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    for (User user : allUsers) {
                        if (clientRequest.getUser().equals(user)) {
                            ArrayList<Mail> sentMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getUser() + "/sent.txt");
                            objectOutputStream.writeObject(sentMails);
                        }
                    }
                    break;
                case sendMail:
                    boolean recepientExists = false;
                    boolean blocked = false;
                    for (User user : allUsers) {
                        if (user.getUsername().equals(clientRequest.getMail().getReciever())) {
                            recepientExists = true;
                            if (user.getBlockedUsers().contains(clientRequest.getMail().getSender().getUsername())) {
                                System.out.println("this user is blocked");
                                objectOutputStream.writeObject(false);
                                blocked = true;
                            }
                        }
                    }
                    if (!blocked && recepientExists) {
                        allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                        for (User user : allUsers) {
                            if (user.equals(clientRequest.getMail().getSender())) {
                                clientRequest.getMail().setRead(true);
                                ArrayList<Mail> sentMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getSender().getUsername() + "/sent.txt");
                                sentMails.add(clientRequest.getMail());
                                new FileHandler().write("src/main/java/DataBase/" + clientRequest.getMail().getSender() + "/sent.txt" , sentMails);
                                System.out.println("[" + user + "]" + " [send]");
                                System.out.println("message: [" + clientRequest.getMail().getSubject() + "] from [" + clientRequest.getMail().getSender().getUsername() + "] to [" + clientRequest.getMail().getReciever() + "]");
                                getDate();
                            }
                            if (user.getUsername().equals(clientRequest.getMail().getReciever())) {
                                ArrayList<Mail> receivedMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt");
                                clientRequest.getMail().setRead(false);
                                receivedMails.add(clientRequest.getMail());
                                new FileHandler().write("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt" , receivedMails);
                            }
                        }
                    }
                    if (!recepientExists) {
                        Mail errorMail = new Mail();
                        User errorSender = new User("mailer-daemon" , "");
                        errorSender.setName("Mail Delivery Subsystem");
                        errorMail.setSubject("Address not found");
                        errorMail.setSender(errorSender);
                        errorMail.setTextMessage("Your message wasn't delivered to " + clientRequest.getMail().getReciever() +"@gmail.com"+ " because the address couldn't be found, or is unable to receive mail. ");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        errorMail.setDate(dateFormat.format(new Date()));
                        ArrayList<Mail> receivedMails = (ArrayList<Mail>)new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getSender() + "/received.txt");
                        receivedMails.add(errorMail);
                        new FileHandler().write("src/main/java/DataBase/" + clientRequest.getMail().getSender() + "/received.txt" , receivedMails);
                    }
                break;
                case BlockSender:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    for (User user : allUsers) {
                        if (user.getUsername().equals(clientRequest.getMail().getReciever())) {
                            Set<String> blockedUsers = user.getBlockedUsers();
                            blockedUsers.add(clientRequest.getMail().getSender().getUsername());
                            user.setBlockedUsers(blockedUsers);
                            new FileHandler().write("user-pass.txt", allUsers);
                            break;
                        }
                    }
                    System.out.println("[" + clientRequest.getMail().getReciever() + "] [block] " + clientRequest.getMail().getSender().getUsername());
                break;
                case DeleteMail:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    for (User user : allUsers) {
//                        if (user.getUsername().equals(clientRequest.getMail().getSender().getUsername())) {
//                            ArrayList<Mail> sentMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getSender().getUsername() + "/sent.txt");
//                            System.out.println(sentMails.size());
//                            sentMails.remove(clientRequest.getMail());
//                            System.out.println(sentMails.size());
//                            new FileHandler().write("src/main/java/DataBase/" + clientRequest.getMail().getSender() + "/sent.txt" , sentMails);
//                            System.out.println("[" + clientRequest.getMail().getSender() + "] removemsg");
//                            System.out.println("message: [" + clientRequest.getMail().getSubject() + "] [" + clientRequest.getMail().getSender() + "]");
//                            getDate();
//                        }
                        if (user.getUsername().equals(clientRequest.getMail().getReciever())) {
                            ArrayList<Mail> receivedMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt");
                            receivedMails.remove(clientRequest.getMail());
                            new FileHandler().write("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt" , receivedMails);
                            System.out.println("[" + clientRequest.getMail().getReciever() + "] removemsg");
                            System.out.println("message: [" + clientRequest.getMail().getSubject() + "] [" + clientRequest.getMail().getSender() + "]");
                            getDate();
                        }
                    }

                    break;
                case CheckBlocked:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    boolean isBlocked = false;
                    for (User user : allUsers) {
                        if (user.getUsername().equals(clientRequest.getMail().getReciever())) {
                            if (user.getBlockedUsers().contains(clientRequest.getMail().getSender().getUsername())) {
                                isBlocked = true;
                                break;
                            }
                        }
                    }
                    objectOutputStream.writeObject(isBlocked);
                    break;
                case UnblockSender:
                    for (User user : allUsers) {
                        if (user.getUsername().equals(clientRequest.getMail().getReciever())) {
                            user.getBlockedUsers().remove(clientRequest.getMail().getSender().getUsername());
                            break;
                        }
                    }
                    new FileHandler().write("user-pass.txt", allUsers);
                    break;
                case ImportantMail:
                    System.out.println(clientRequest.getFlag());
                    if (clientRequest.getFlag()) {
                        System.out.println("[" + clientRequest.getMail().getReciever() +  "] mark");
                        System.out.println("message: [" + clientRequest.getMail().getSubject() + "] [" + clientRequest.getMail().getSender() + "] mark as important");
                        getDate();
                    }
                    else {
                        System.out.println("[" + clientRequest.getMail().getReciever() +  "] mark");
                        System.out.println("message: [" + clientRequest.getMail().getSubject() + "] [" + clientRequest.getMail().getSender() + "] mark as unimportant");
                        getDate();
                    }
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    for (User user : allUsers) {
                        if (user.getUsername().equals(clientRequest.getMail().getReciever())){
                            ArrayList<Mail> receivedMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt");
                            for (Mail mail : receivedMails) {
                                if (mail.equals(clientRequest.getMail())) {
                                    if (clientRequest.getFlag())
                                        mail.setImportant(true);
                                    else
                                        mail.setImportant(false);
                                    break;
                                }
                            }
                            new FileHandler().write("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt" , receivedMails);
                        }
                    }
                    break;
                case ReadMail:
                    if (clientRequest.getMail().isRead()) {
                        System.out.println("[" + clientRequest.getMail().getReciever() +  "] mark");
                        System.out.println("message: [" + clientRequest.getMail().getSubject() + "] [" + clientRequest.getMail().getSender() + "] mark as read");
                        getDate();
                    }
                    else {
                        System.out.println("[" + clientRequest.getMail().getReciever() +  "] mark");
                        System.out.println("message: [" + clientRequest.getMail().getSubject() + "] [" + clientRequest.getMail().getSender() + "] mark as unread");
                        getDate();
                    }
                    for (User user : allUsers) {
                        if (user.getUsername().equals(clientRequest.getMail().getReciever())){
                            ArrayList<Mail> receivedMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt");
                            for (Mail mail : receivedMails) {
                                if (mail.equals(clientRequest.getMail())) {
                                    if (clientRequest.getMail().isRead())
                                        mail.setRead(true);
                                    else
                                        mail.setRead(false);
                                    break;
                                }
                            }
                            new FileHandler().write("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt" , receivedMails);
                        }
                    }
                    break;
                case Recovery:
                    allUsers = (ArrayList<User>) new FileHandler().read("user-pass.txt");
                    boolean userExist = false;
                    for (User user : allUsers) {
                        if (user.equals(clientRequest.getUser())){
                            objectOutputStream.writeObject(user);
                            System.out.println("[" + clientRequest.getUser() + "] recover password");
                            getDate();
                            userExist = true;
                            break;
                        }
                    }
                    if (!userExist)
                        objectOutputStream.writeObject(false);
                    break;
                case GetConversation:
                    ArrayList<Mail> conversation = new ArrayList<>();
                    ArrayList<Mail> receivedMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/received.txt");
                    for (Mail mail : receivedMails) {
                        if (mail.getSender().getUsername().equals(clientRequest.getMail().getSender().getUsername())) {
                            conversation.add(mail);
                        }
                    }
                    ArrayList<Mail> sentMails = (ArrayList<Mail>) new FileHandler().read("src/main/java/DataBase/" + clientRequest.getMail().getReciever() + "/sent.txt");
                    for (Mail mail : sentMails) {
                        if (mail.getReciever().equals(clientRequest.getMail().getSender().getUsername())) {
                            conversation.add(mail);
                        }
                    }
                    objectOutputStream.writeObject(conversation);
                    break;
            }
        }
    }
}
