package com.example.appfood.user.model;

import java.io.Serializable;

public class ThongBao implements Serializable {
    private int anhTB;
    private String urlAnhTB;
    private String TB;

    public ThongBao(){}

    public ThongBao(int anhTB, String urlAnhTB, String TB) {
        this.anhTB = anhTB;
        this.urlAnhTB = urlAnhTB;
        this.TB = TB;
    }

    public int getAnhTB() {
        return anhTB;
    }

    public void setAnhTB(int anhTB) {
        this.anhTB = anhTB;
    }

    public String getUrlAnhTB() {
        return urlAnhTB;
    }

    public void setUrlAnhTB(String urlAnhTB) {
        this.urlAnhTB = urlAnhTB;
    }

    public String getTB() {
        return TB;
    }

    public void setTB(String TB) {
        this.TB = TB;
    }
}
