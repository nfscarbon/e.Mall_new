package com.intigate.id;

/**
 * Created by ratnav on 18-05-2015.
 */
public class Model_mail_details {

    boolean isValid;
    String Name;
    int ConversationID;

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public int getMailTypeId() {
        return MailTypeId;
    }

    public void setMailTypeId(int mailTypeId) {
        MailTypeId = mailTypeId;
    }

    int CompanyId;
    int MailTypeId;
    String Subject;
    String Body;
    String SendDate;
    String ReplyDate;
    int SendBy;
    String LogoURL;
    String UserPhoto;
    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getConversationID() {
        return ConversationID;
    }

    public void setConversationID(int conversationID) {
        ConversationID = conversationID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public String getReplyDate() {
        return ReplyDate;
    }

    public void setReplyDate(String replyDate) {
        ReplyDate = replyDate;
    }

    public int getSendBy() {
        return SendBy;
    }

    public void setSendBy(int sendBy) {
        SendBy = sendBy;
    }

    public String getLogoURL() {
        return LogoURL;
    }

    public void setLogoURL(String logoURL) {
        LogoURL = logoURL;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }


}
