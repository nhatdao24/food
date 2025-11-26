package com.example.du_an_cuoi_ky.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.du_an_cuoi_ky.Models.ThongBao;
import com.example.du_an_cuoi_ky.R;

import java.util.ArrayList;

public class ThongBaoAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ThongBao> listTB;

    public ThongBaoAdapter(@NonNull Context context, int resource, ArrayList<ThongBao> listTB) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.listTB = listTB;
    }

    @Override
    public int getCount(){
        return this.listTB.size();
    }
    @Override
    public View getView(int position , @NonNull View convertView , @NonNull ViewGroup parent){
        // Ket noi voi layout
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
        }

        // anh xa cac thanh phan trong item vao giao dien thong bao

        TextView tvtb = convertView.findViewById(R.id.tvTB);
        ImageView imgtb = convertView.findViewById(R.id.imgTB);

        ThongBao thongBao = listTB.get(position);
        // đặt giá trị cho các thành phần trong listview
        tvtb.setText(thongBao.getTB());
        if(thongBao.getUrlAnhTB() == null){
            imgtb.setImageResource(thongBao.getAnhTB());
        }
        else{
            imgtb.setImageURI(Uri.parse(thongBao.getUrlAnhTB()));
        }

        return convertView;
    }

}
