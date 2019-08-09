package com.GUI.Entity;

import java.util.List;

public class Relevance {

    private String user;

    private String password;

    private boolean on_off;

    private List<URLData> urlData;

    private String website;

    /** 代理待设置 */
    //private Proxy;

    public Relevance(String[] users, List<URLData> urlData){
        this(users[0], users[1], users[2].equals("true"), users[3],urlData);
    }

    public Relevance(String user, String password, boolean on_off, String website, List<URLData> urlData){
        this.user = user;
        this.password = password;
        this.urlData = urlData;
        this.on_off = on_off;
        this.website = website;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOn_off() {
        return on_off;
    }

    public void setOn_off(boolean on_off) {
        this.on_off = on_off;
    }

    public List<URLData> getUrlData() {
        return urlData;
    }

    public void setUrlData(List<URLData> urlData) {
        this.urlData = urlData;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUserTxt(){
        return user + "," + password + "," + on_off + "," + website;
    }

    @Override
    public String toString() {
        return "user=" + user + ",   ------------  password=" + password + ",   ------------  on_off=" + on_off + ",   ------------  website=" + website + ",   ------------  urlData=" + urlData;
    }
}