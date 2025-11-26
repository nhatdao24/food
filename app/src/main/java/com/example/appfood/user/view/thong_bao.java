package com.example.appfood.user.view;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appfood.user.model.ThongBao;
import com.example.appfood.user.view.Adapters.ThongBaoAdapter;
import com.example.appfood.user.model.User1;
import com.example.giaodien.R;

import java.util.ArrayList;

public class thong_bao extends AppCompatActivity {
    ListView lvtb;
    ImageView imgThoat;
    ThongBaoAdapter thongBaoAdapter;
    ArrayList<ThongBao> dstb;
    ArrayAdapter adapter;

    ArrayList<String> lstTB;
//    ActivityResultLauncher<Intent> launchertb;

    ImageView imgTB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_bao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvtb = findViewById(R.id.lvtb);
        imgThoat = findViewById(R.id.imgThoat);

        com.example.appfood.user.view.DataBaseHelper dataBaseHelper = new com.example.appfood.user.view.DataBaseHelper(this);
//        // khoi tao danh sach thong bao
        dstb = dataBaseHelper.getThongBao(1);

        if(dstb == null) dstb = new ArrayList<>();

        thongBaoAdapter = new ThongBaoAdapter(this, R.layout.item_thong_bao, dstb);
        lvtb.setAdapter(thongBaoAdapter);

        // xử lý sự kiện quay lại
//        imgThoat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(thong_bao.this , ho_so.class);
//                startActivity(intent);
//            }
//        });

    }



}