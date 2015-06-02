package com.intigate.offers;

/**
 * Created by ratnav on 21-05-2015.
 */
public class Handler_user_review {

    String user_name,comment,time,image_url;
    Float rating;
    Handler_user_review(String user_name,String comment,String time,String image_url,Float rating){
        setUser_name(user_name);
        setComment(comment);
        setTime(time);
        setImage_url(image_url);
        setRating(rating);

    }
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
