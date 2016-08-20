package com.luofeng.runningman.model;

/**
 * Created by 罗峰 on 2016/8/20.
 */
public class User {
    private String nickname;
    private String birthday;
    private String gender;
    private String height;
    private String weight;

    public User(String nickname, String birthday, String gender, String height, String weight) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.height = height;
        this.weight = weight;

    }

    public String getNickname() {
        return nickname;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getGender() {
        return gender;
    }
    public String getHeight() {
        return height;
    }
    public String getWeight() {
        return weight;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
        public void setGender(String gender) {
        this.gender = gender;
    }
        public void setHeight(String height) {
        this.height = height;
    }
        public void setWeight(String weight) {
        this.weight = weight;
    }

}
