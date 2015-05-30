package com.intigate.id;

/**
 * Created by ratnav on 29-05-2015.
 */
public class MailHandler {


    boolean isMail, isRead, IsImp;

    String userImageUrl, UserName, MailTitle, MailSubject,MailContent,Time,firstLetter;

    public int getNoofMails() {
        return NoofMails;
    }

    public void setNoofMails(int noofMails) {
        NoofMails = noofMails;
    }

    int NoofMails;
    public int getConversationID() {
        return ConversationID;
    }

    public void setConversationID(int conversationID) {
        ConversationID = conversationID;
    }

    public void setIsMail(boolean isMail) {
        this.isMail = isMail;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public void setIsImp(boolean isImp) {
        IsImp = isImp;
    }

    int ConversationID;
    public boolean isMail() {
        return isMail;
    }

    public void setMail(boolean isMail) {
        this.isMail = isMail;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean isImp() {
        return IsImp;
    }

    public void setImp(boolean isImp) {
        IsImp = isImp;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMailTitle() {
        return MailTitle;
    }

    public void setMailTitle(String mailTitle) {
        MailTitle = mailTitle;
    }

    public String getMailSubject() {
        return MailSubject;
    }

    public void setMailSubject(String mailSubject) {
        MailSubject = mailSubject;
    }

    public String getMailContent() {
        return MailContent;
    }

    public void setMailContent(String mailContent) {
        MailContent = mailContent;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
