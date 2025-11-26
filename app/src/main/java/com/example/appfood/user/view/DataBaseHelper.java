package com.example.appfood.user.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appfood.user.model.ThongBao;
import com.example.appfood.user.model.User1;
import com.example.giaodien.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "DatDoAn.db";
    private static final int DATABASE_VERSION = 1;

    private String dbPath;

    //Bang nguoi dung
    private static final String TABLE_NAME = "User";
    private static final String COLUMN_ID = "ma_nguoi_dung";
    private static final String COLUMN_NAME = "ten_nguoi_dung";
    private static final String COLUMN_SDT = "so_dien_thoai";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DIA_CHI = "dia_chi";
    private static final String COLUMN_AVATAR = "duong_dan_anh";

    // Bang thong bao
    private static final String TABLE_NAME_TB = "thong_bao";
    private static final String COLUMN_ID_TB = "ma_thong_bao";
    private static final String COLUMN_NDTB = "nd_thong_bao";
    private static final String COLUMN_MND = "ma_nguoi_dung";
    private static final String COLUMN_ANH = "duong_dan_luu";
    public DataBaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        // lay duong dan den thu muc database that tren thiet bi
        dbPath = context.getDatabasePath(DATABASE_NAME).getPath();

        // copy db neu chua co
        copyDatabaseIfNeeded();
    }

    private void copyDatabaseIfNeeded() {
        File dbFile = new File(dbPath);

        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();

            try {
                InputStream input = context.getAssets().open(DATABASE_NAME);
                OutputStream output = new FileOutputStream(dbPath);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // khoi tao phuong thuc
    // HAM INSERT
    @Override
    public void onCreate(SQLiteDatabase db){

    }
    // HAM CAP NHAT
    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion){}

    // HAM TRUY VAN
    public User1 getNameUser(int id_nd){
        User1 user = null;
        SQLiteDatabase _db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id_nd;
        Cursor cursor = _db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String ten = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SDT));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String dia_chi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIA_CHI));
                String avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));

                int resID = context.getResources().getIdentifier(
                        avatar,   // tên ảnh
                        "drawable",
                        context.getPackageName()
                );

                user = new User1(id , ten , sdt , email, dia_chi, resID , null);

            }while (cursor.moveToNext());
        }

        cursor.close();
        _db.close();

        return user;
    }

    // Lay danh sach thong bao trong bang thong bao
    public ArrayList<ThongBao> getThongBao(int id_nd){
        ArrayList<ThongBao> tb = new ArrayList<>();
        SQLiteDatabase _db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_TB + " WHERE " + COLUMN_MND + " = " + id_nd;
        Cursor cursor = _db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_TB));
                String noi_dung = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NDTB));
                String ma_nd = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MND));
                String urlAnh = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANH));

                // Convert tên ảnh → resource ID
                int resID = context.getResources().getIdentifier(
                        urlAnh,   // tên ảnh
                        "drawable",
                        context.getPackageName()
                );

                ThongBao thongBao;
                if (resID != 0) {
                    thongBao = new ThongBao(resID, null , noi_dung);
                } else {
                     thongBao = new ThongBao(R.drawable.ic_launcher_foreground, null, noi_dung);
                }


                tb.add(thongBao);

            }while (cursor.moveToNext());
        }

        cursor.close();
        _db.close();

        return tb;
    }


    // khơi tạo phương thức sửa thông tin hồ sơ
    public void updateUser(int id , String ten , String sdt , String email, String dc , String duong_dan_anh){
        SQLiteDatabase _db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME , ten);
        values.put(COLUMN_SDT, sdt);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_DIA_CHI, dc);
        values.put(COLUMN_AVATAR, duong_dan_anh);

        int result = _db.update(TABLE_NAME , values , COLUMN_ID + " = ?" , new String[]{String.valueOf(id)});
        if (result == 0){
            Toast.makeText(context , "Update failed" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context , "Update successfully" , Toast.LENGTH_SHORT).show();
        }

        _db.close();
    }


}
