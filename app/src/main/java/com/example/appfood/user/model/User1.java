package com.example.appfood.user.model;

public class User1 {
    private int ma_nguoi_dung;
    private String ten_nguoi_dung;
    private String sdt;
    private String email;
    private String dia_chi;

    private int anhDaiDien;      // Ảnh đại diện (lưu ID ảnh trong resource)
    private String urlAnh;

    public User1(){}

    public User1(int ma_nguoi_dung, String ten_nguoi_dung, String sdt, String email, String dia_chi, int anhDaiDien, String urlAnh) {
        this.ma_nguoi_dung = ma_nguoi_dung;
        this.ten_nguoi_dung = ten_nguoi_dung;
        this.sdt = sdt;
        this.email = email;
        this.dia_chi = dia_chi;
        this.anhDaiDien = anhDaiDien;
        this.urlAnh = urlAnh;
    }

    public int getMa_nguoi_dung() {
        return ma_nguoi_dung;
    }

    public void setMa_nguoi_dung(int ma_nguoi_dung) {
        this.ma_nguoi_dung = ma_nguoi_dung;
    }

    public String getTen_nguoi_dung() {
        return ten_nguoi_dung;
    }

    public void setTen_nguoi_dung(String ten_nguoi_dung) {
        this.ten_nguoi_dung = ten_nguoi_dung;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public int getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(int anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }

    @Override
    public String toString() {
        return "User{" +
                "ma_nguoi_dung=" + ma_nguoi_dung +
                ", ten_nguoi_dung='" + ten_nguoi_dung + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", dia_chi='" + dia_chi + '\'' +
                ", anhDaiDien=" + anhDaiDien +
                ", urlAnh='" + urlAnh + '\'' +
                '}';
    }
}
