package com.intigate.id;

/**
 * Created by ratnav on 19-05-2015.
 */
public class Moder_Announcements {

    boolean isValid;
    boolean Seen;

    public String getLogoURL() {
        return LogoURL;
    }

    public void setLogoURL(String logoURL) {
        LogoURL = logoURL;
    }

    String LogoURL;
    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    boolean isVisible;
    int LoyltyId,NotificationTypeId,NotificationSubTypeId;
    String NotificationId,Subject,Body,Image,NotificationType,NotificationSubType,BranchName,CompanyName;

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isSeen() {
        return Seen;
    }

    public void setSeen(boolean seen) {
        Seen = seen;
    }

    public int getLoyltyId() {
        return LoyltyId;
    }

    public void setLoyltyId(int loyltyId) {
        LoyltyId = loyltyId;
    }

    public int getNotificationTypeId() {
        return NotificationTypeId;
    }

    public void setNotificationTypeId(int notificationTypeId) {
        NotificationTypeId = notificationTypeId;
    }

    public int getNotificationSubTypeId() {
        return NotificationSubTypeId;
    }

    public void setNotificationSubTypeId(int notificationSubTypeId) {
        NotificationSubTypeId = notificationSubTypeId;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(String notificationId) {
        NotificationId = notificationId;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(String notificationType) {
        NotificationType = notificationType;
    }

    public String getNotificationSubType() {
        return NotificationSubType;
    }

    public void setNotificationSubType(String notificationSubType) {
        NotificationSubType = notificationSubType;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
