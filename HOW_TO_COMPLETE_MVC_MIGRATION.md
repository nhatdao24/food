# ✅ HƯỚNG DẪN TÁI CẤU TRÚC TOÀN BỘ DỰ ÁN THEO MVC

## 🎯 MỤC TIÊU
Di chuyển TOÀN BỘ code vào cấu trúc MVC chuẩn với package `view`

## 📊 TIẾN TRÌNH HIỆN TẠI

### ✅ ĐÃ HOÀN THÀNH (100%):

#### 1. Model Layer
```
✅ com.example.appfood.model/
   ├── Shop.java
   ├── User.java
   ├── Dish.java
   ├── Promotion.java
   ├── Order.java
   └── Review.java
```

#### 2. Repository Layer
```
✅ com.example.appfood.repository/
   ├── ShopRepository.java + ShopRepositoryImpl.java
   ├── UserRepository.java + UserRepositoryImpl.java
   └── DishRepository.java + DishRepositoryImpl.java ← MỚI TẠO
```

#### 3. Controller Layer
```
✅ com.example.appfood.controller/
   ├── ShopController.java
   ├── UserController.java
   └── DishController.java ← MỚI TẠO
```

#### 4. View Layer (đã bắt đầu)
```
✅ com.example.appfood.view/
   └── AddDishActivity.java ← MỚI TẠO (sử dụng DishController)
```

---

## 🚀 CÁC BƯỚC TIẾP THEO

### CÁCH 1: SỬ DỤNG INTELLIJ/ANDROID STUDIO (KHUYẾN NGHỊ)

#### Bước 1: Tạo các package
1. Mở Android Studio
2. Chuột phải vào `com.example.appfood`
3. New → Package
4. Tạo:
   - `view`
   - `view.adapter`
   - `view.fragment`

#### Bước 2: Di chuyển Activities vào view/
1. Chọn tất cả file Activity (giữ Ctrl + click):
   - MainActivity.java
   - HomeActivity.java
   - RegisterStep1Activity.java
   - RegisterStep2Activity.java
   - DishListActivity.java
   - EditDishActivity.java
   - PromotionListActivity.java
   - AddPromotionActivity.java
   - EditPromotionActivity.java
   - OrdersManagerActivity.java
   - NewOrdersActivity.java
   - ConfirmedOrdersActivity.java
   - ReviewListActivity.java
   - ReviewDetailActivity.java
   - SalesReportActivity.java

2. Drag & Drop vào package `view`
3. Android Studio sẽ hỏi: "Move class(es)?" → Chọn **"Refactor"**
4. Android Studio tự động:
   - Cập nhật package declaration
   - Fix imports trong tất cả file
   - Cập nhật AndroidManifest.xml

#### Bước 3: Di chuyển Adapters vào view/adapter/
1. Chọn:
   - DishAdapter.java
   - PromotionAdapter.java
   - OrdersPagerAdapter.java

2. Drag & Drop vào `view/adapter`
3. Chọn "Refactor"

#### Bước 4: Di chuyển Fragments vào view/fragment/
1. Chọn:
   - NewOrdersFragment.java
   - ConfirmedOrdersFragment.java

2. Drag & Drop vào `view/fragment`
3. Chọn "Refactor"

#### Bước 5: Xóa file cũ ở root
Android Studio đã di chuyển, bạn chỉ cần kiểm tra:
- ❌ Xóa `Dish.java` ở root (đã có trong `model/`)
- ❌ Xóa `Promotion.java` ở root (đã có trong `model/`)

---

### CÁCH 2: THỰC HIỆN THỦ CÔNG (Nếu Refactor không hoạt động)

#### Tôi đã tạo sẵn file mới:
- ✅ `view/AddDishActivity.java` (đã dùng MVC với DishController)

#### Bạn cần tạo tiếp:
1. Copy nội dung file cũ
2. Tạo file mới trong package `view`
3. Đổi package declaration thành: `package com.example.appfood.view;`
4. Thêm import cho R: `import com.example.appfood.R;`
5. Cập nhật để sử dụng Controller (nếu có)

---

## 📝 TEMPLATE TẠO VIEW MỚI VỚI MVC

### Ví dụ: EditDishActivity với MVC

```java
package com.example.appfood.view;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.appfood.controller.DishController;
import com.example.appfood.model.Dish;
import com.example.appfood.repository.DishRepository;

public class EditDishActivity extends AppCompatActivity {
    
    private DishController dishController;
    private String dishId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        
        dishController = DishController.getInstance();
        dishId = getIntent().getStringExtra("dishId");
        
        initViews();
        loadDish();
    }
    
    private void loadDish() {
        dishController.getDishById(dishId, new DishRepository.OnDataLoadedCallback<Dish>() {
            @Override
            public void onSuccess(Dish dish) {
                // Hiển thị dữ liệu lên UI
                displayDish(dish);
            }
            
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditDishActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void updateDish() {
        // Tạo Dish object từ input
        Dish dish = new Dish(...);
        dish.setDishId(dishId);
        
        dishController.updateDish(dish, new DishRepository.OnOperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditDishActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
            
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditDishActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

---

## 🔧 FIX LỖI THƯỜNG GẶP

### Lỗi 1: Cannot resolve symbol 'R'
**Giải pháp**: Thêm import
```java
import com.example.appfood.R;
```

### Lỗi 2: Activity không tìm thấy trong Manifest
**Giải pháp**: Cập nhật AndroidManifest.xml
```xml
<!-- Cũ -->
<activity android:name=".AddDishActivity" />

<!-- Mới -->
<activity android:name=".view.AddDishActivity" />
```

### Lỗi 3: Cannot find class
**Giải pháp**: Cập nhật import trong file gọi Activity
```java
// Cũ
// Không cần import (cùng package)

// Mới
import com.example.appfood.view.AddDishActivity;
```

---

## 📋 CHECKLIST DI CHUYỂN

### Activities (16 files)
- [ ] MainActivity.java
- [ ] HomeActivity.java
- [ ] RegisterStep1Activity.java
- [ ] RegisterStep2Activity.java (đã có MVC ở root)
- [ ] DishListActivity.java
- [x] AddDishActivity.java ← ĐÃ TẠO MỚI
- [ ] EditDishActivity.java
- [ ] PromotionListActivity.java
- [ ] AddPromotionActivity.java
- [ ] EditPromotionActivity.java
- [ ] OrdersManagerActivity.java
- [ ] NewOrdersActivity.java
- [ ] ConfirmedOrdersActivity.java
- [ ] ReviewListActivity.java
- [ ] ReviewDetailActivity.java
- [ ] SalesReportActivity.java

### Adapters (3 files)
- [ ] DishAdapter.java
- [ ] PromotionAdapter.java
- [ ] OrdersPagerAdapter.java

### Fragments (2 files)
- [ ] NewOrdersFragment.java
- [ ] ConfirmedOrdersFragment.java

---

## 🎯 ƯU TIÊN TIẾP THEO

### 1. Repository & Controller cho Promotion
```java
// Cần tạo:
- PromotionRepository.java
- PromotionRepositoryImpl.java
- PromotionController.java
```

### 2. Repository & Controller cho Order
```java
// Cần tạo:
- OrderRepository.java
- OrderRepositoryImpl.java
- OrderController.java
```

### 3. Repository & Controller cho Review
```java
// Cần tạo:
- ReviewRepository.java
- ReviewRepositoryImpl.java
- ReviewController.java
```

---

## ⚡ LỆNH NHANH

Nếu bạn muốn tôi tạo tất cả file còn lại, hãy nói:
- "Tạo tất cả Activities trong view package"
- "Tạo PromotionRepository và PromotionController"
- "Tạo OrderRepository và OrderController"

---

## 📄 KẾT QUẢ CUỐI CÙNG

```
com.example.appfood/
├── model/                     ✅ HOÀN THÀNH
├── repository/                ✅ 60% (Shop, User, Dish)
├── controller/                ✅ 60% (Shop, User, Dish)
└── view/                      ⏳ 5% (chỉ có AddDishActivity)
    ├── (16 Activities)        ⏳ CẦN DI CHUYỂN
    ├── adapter/
    │   └── (3 Adapters)       ⏳ CẦN DI CHUYỂN
    └── fragment/
        └── (2 Fragments)      ⏳ CẦN DI CHUYỂN
```

---

## 💡 GỢI Ý

**CÁCH NHANH NHẤT**: Sử dụng Refactor trong Android Studio
1. Select tất cả Activity files
2. Chuột phải → Refactor → Move
3. Chọn package `com.example.appfood.view`
4. Click "Refactor"
5. Android Studio làm tất cả!

**BẠN MUỐN TÔI LÀM GÌ TIẾP?**
- Tạo tất cả Repository & Controller còn lại?
- Tạo tất cả Activity trong package view?
- Chỉ hướng dẫn bạn tự làm?

Hãy cho tôi biết! 🚀

