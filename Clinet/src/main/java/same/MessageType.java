package same;

public enum MessageType
{
    SignUp,
    /**
     * sign up a new user and check if it doesn't already exist
     */
    LogIn,
    /**
     * a user that exist in the list of users log in with username and password
     */
    AdditionalSignUp,
    /**
     * complete sign up steps and add new user to the list of users
     */
    Disconnect,
    /**
     * no idea
     */
    DeleteAccount,
    /**
     * delete a user and it's data in the server
     */
    ChangeInfo,
    /**
     * change user's information through settings
     * here we delete the previous user and add a new user with new information we have
     */
    ReceivedList,
    /**
     * access the list of received mails of a user
     */
    SentList,
    /**
     * access the list of sent mails of a user
     */
    sendMail,
    /**
     * a user sending a new mail to another user
     * here we check if that another user exists
     * if it doesn't exist our user will receive a mail
     */
    recevieMail,
    /**
     * when user A sends a mail to user B we use this type of message for user B to receive the mail
     */
    BlockSender,
    DeleteMail,
    CheckBlocked,
    UnblockSender,
    ImportantMail,
    ReadMail,
    Recovery,
    GetConversation
}