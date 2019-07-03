package com.GUI.Entity;

public class Relevance {

    private String user;

    private String password;

    private String url;

    private String data;

    private boolean on_off;

    /** 代理待设置 */
    //private Proxy;

    public Relevance(String[] users, String url, String data){
        this(users[0], users[1], users[2].equals("true"), url, data);
    }

    public Relevance(String user, String password, String url, String data){
        this(user, password, true, url, data);
    }

    public Relevance(String user, String password, boolean on_off, String url, String data){
        this.user = user;
        this.password = password;
        this.url = url;
        this.data = data;
        this.on_off = on_off;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isOn_off() {
        return on_off;
    }

    public void setOn_off(boolean on_off) {
        this.on_off = on_off;
    }

    @Override
    public String toString() {
        return "Relevance{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", data='" + data + '\'' +
                ", on_off=" + on_off +
                '}';
    }
}
