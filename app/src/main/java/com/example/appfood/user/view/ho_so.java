package com.example.appfood.user.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appfood.user.model.User1;
import com.example.appfood.user.view.DataBaseHelper;
import com.example.giaodien.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ho_so extends AppCompatActivity {
    Button btndx, btnSuaTT;

    TextView tvTen;
    TextView tvSDT;
    TextView tvEmail;
    TextView tvDC;
    ArrayList<User1> users;
    ArrayList<String> dsnd;
    ActivityResultLauncher<Intent> launcherChonAnh;

    ImageView imgAnhHS;

    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ho_so);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btndx = findViewById(R.id.btnDX);
        btnSuaTT = findViewById(R.id.btnSuaTT);
        tvTen = findViewById(R.id.tvTen );
        tvSDT = findViewById(R.id.tvSDT );
        tvEmail = findViewById(R.id.tvEmail );
        tvDC = findViewById(R.id.tvDC );
        //xu ly su kien click btndx
//        btndx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ho_so.this, thong_bao.class);
//                startActivity(intent);
//            }
//        });

        // khai bao launcher de chon anh tu thu vien
        launcherChonAnh = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK && o.getData() != null) {
                            Uri uriAnh = o.getData().getData();

                            // Xóa ảnh cũ nếu có
                            if (imgAnhHS != null && imgAnhHS.getTag() != null) {
                                String oldPath = imgAnhHS.getTag().toString();
                                if (!"ic_launcher_foreground".equals(oldPath)) {
                                    if (oldPath != null && oldPath.startsWith(getFilesDir().getAbsolutePath())) {
                                        File oldFile = new File(oldPath);
                                        if (oldFile.exists()) oldFile.delete();
                                    }

                                }
                            }

                            // Copy ảnh mới vào bộ nhớ app
                            String path = saveImageToInternalStorage(uriAnh);
                            if (path != null && imgAnhHS != null) {
                                Bitmap bm = BitmapFactory.decodeFile(path);
                                imgAnhHS.setImageBitmap(bm);
                                imgAnhHS.setTag(path); // lưu đường dẫn file mới
                            }
                        }
                    }
                }
        );



        // xử lý sự kiện sửa thông tin
        btnSuaTT.setOnClickListener(v -> {
            DataBaseHelper db = new DataBaseHelper(ho_so.this);
            User1 nguoi_dung = db.getNameUser(1); // hoặc id phù hợp
            if (nguoi_dung != null) {
                showEditDialog(0, nguoi_dung); // position có thể không cần thiết nếu không dùng
            } else {
                Toast.makeText(ho_so.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
            }
        });


        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        // Lay nguoi dung co id = 2
        User1 nguoi_dung = dataBaseHelper.getNameUser(1);

        if (nguoi_dung == null) {
            Log.e("DB_ERROR", "Không tìm thấy user với id=1");
        } else {
            Log.d("DB_USER", "Tên: " + nguoi_dung.getTen_nguoi_dung());
        }

        // Hien thi thong tin ra man hinh
       if(nguoi_dung != null){
           tvTen.setText(nguoi_dung.getTen_nguoi_dung());
           tvSDT.setText(nguoi_dung.getSdt());
           tvEmail.setText(nguoi_dung.getEmail());
           tvDC.setText(nguoi_dung.getDia_chi());
       }
       else{
           tvTen.setText("Khong tim thay nguoi dung");
       }

    }



    private void showEditDialog(int position, User1 tk) {
        LayoutInflater inflater = getLayoutInflater();
        View editView = inflater.inflate(R.layout.sua_ho_so_ca_nhan, null);

        EditText edtTen = editView.findViewById(R.id.edtTen);
        EditText edtSDT = editView.findViewById(R.id.edtSDT);
        EditText edtEmail = editView.findViewById(R.id.edtEmail);
        EditText edtDC = editView.findViewById(R.id.edtDC);
        imgAnhHS = editView.findViewById(R.id.imgHS);

        // Gán dữ liệu cũ
        edtTen.setText(tk.getTen_nguoi_dung());
        edtSDT.setText(tk.getSdt());
        edtEmail.setText(tk.getEmail());
        edtDC.setText(tk.getDia_chi());

        // Hiển thị ảnh cũ (nếu có)
        if (tk.getUrlAnh() != null && !tk.getUrlAnh().isEmpty()) {
            File f = new File(tk.getUrlAnh());
            if (f.exists()) {
                imgAnhHS.setImageURI(Uri.fromFile(f));
            } else {
                Log.e("IMG_ERROR", "File không tồn tại: " + tk.getUrlAnh());
                imgAnhHS.setImageResource(R.drawable.ic_launcher_foreground);
            }
            imgAnhHS.setTag(tk.getUrlAnh());
        } else {
            imgAnhHS.setImageResource(R.drawable.ic_launcher_foreground);
            imgAnhHS.setTag("ic_launcher_foreground");
        }


        // Chọn ảnh mới
        imgAnhHS.setOnClickListener(v -> {
            Intent intentChonAnh = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            launcherChonAnh.launch(intentChonAnh);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editView)
                .setTitle("Sửa tài khoản")
                .setPositiveButton("Lưu", null)
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnLuu = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnLuu.setOnClickListener(v -> {
            String ten = edtTen.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String dia_chi = edtDC.getText().toString().trim();

            // Xử lý ảnh
            String uriAnh;
            if (imgAnhHS.getTag() != null) {
                // Nếu đã chọn ảnh mới thì lấy ảnh mới
                uriAnh = imgAnhHS.getTag().toString();
            } else {
                // Nếu chưa chọn ảnh mới thì giữ ảnh cũ
                uriAnh = tk.getUrlAnh();

                // Nếu cả ảnh cũ cũng null thì gán ảnh mặc định
                if (uriAnh == null || uriAnh.isEmpty()) {
                    uriAnh = "ic_launcher_foreground"; // tên ảnh mặc định trong drawable
                }
            }

            DataBaseHelper db = new DataBaseHelper(ho_so.this);

            if (!ten.isEmpty() && !sdt.isEmpty() && !dia_chi.isEmpty() &&
                    !email.isEmpty()) {
                db.updateUser(tk.getMa_nguoi_dung(), ten , sdt , email , dia_chi , uriAnh);
                tvTen.setText(ten);
                tvSDT.setText(sdt);
                tvEmail.setText(email);
                tvDC.setText(dia_chi);

//                tepTinAdapter.notifyDataSetChanged();
                Toast.makeText(ho_so.this, "Đã sửa tep: " + tk.getTen_nguoi_dung(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(ho_so.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

            db.close();
        });
    }


    private String saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "avatar_" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath(); // đường dẫn file thực tế
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}