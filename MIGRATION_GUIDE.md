# Script di chuyển file vào cấu trúc MVC

## CẤU TRÚC MỚI ĐÃ TẠO:

### ✅ HOÀN TẤT:
```
com.example.appfood/
├── model/                  ✅ ĐÃ TẠO
│   ├── Shop.java
│   ├── User.java
│   ├── Dish.java
│   ├── Promotion.java
│   ├── Order.java
│   └── Review.java
│
├── repository/             ✅ ĐÃ TẠO
│   ├── ShopRepository.java
│   ├── ShopRepositoryImpl.java
│   ├── UserRepository.java
│   ├── UserRepositoryImpl.java
│   ├── DishRepository.java          ✅ MỚI
│   └── DishRepositoryImpl.java      ✅ MỚI
│
├── controller/             ✅ ĐÃ TẠO
│   ├── ShopController.java
│   ├── UserController.java
│   └── DishController.java          ✅ MỚI
│
└── view/                   ✅ ĐANG TẠO
    ├── AddDishActivity.java         ✅ MỚI (đã dùng MVC)
    ├── EditDishActivity.java        ⏳ ĐANG TẠO
    ├── DishListActivity.java        ⏳ ĐANG TẠO
    ├── DishAdapter.java             ⏳ ĐANG TẠO
    └── ... (tất cả Activities/Adapters khác)
```

## CÁC FILE CẦN DI CHUYỂN VÀO PACKAGE `view`:

### Activities (sẽ di chuyển vào com.example.appfood.view):
1. MainActivity.java
2. HomeActivity.java
3. RegisterStep1Activity.java
4. RegisterStep2Activity.java (đã có MVC)
5. DishListActivity.java
6. AddDishActivity.java (✅ đã tạo mới với MVC)
7. EditDishActivity.java
8. PromotionListActivity.java
9. AddPromotionActivity.java
10. EditPromotionActivity.java
11. OrdersManagerActivity.java
12. NewOrdersActivity.java
13. ConfirmedOrdersActivity.java
14. ReviewListActivity.java
15. ReviewDetailActivity.java
16. SalesReportActivity.java

### Adapters (sẽ di chuyển vào com.example.appfood.view.adapter):
1. DishAdapter.java
2. PromotionAdapter.java
3. OrdersPagerAdapter.java

### Fragments (sẽ di chuyển vào com.example.appfood.view.fragment):
1. NewOrdersFragment.java
2. ConfirmedOrdersFragment.java

## HƯỚNG DẪN DI CHUYỂN THỦ CÔNG:

### Bước 1: Tạo package mới
- Chuột phải vào `com.example.appfood` → New → Package
- Tạo: `view`, `view.adapter`, `view.fragment`

### Bước 2: Di chuyển file
- Drag & Drop các file vào package tương ứng
- IntelliJ sẽ tự động cập nhật package declaration

### Bước 3: Fix imports trong AndroidManifest.xml
Tìm và thay thế trong `AndroidManifest.xml`:
```xml
TRƯỚC:
<activity android:name=".AddDishActivity" />

SAU:
<activity android:name=".view.AddDishActivity" />
```

### Bước 4: Fix imports trong các file Java
Thêm import cho các class đã di chuyển:
```java
import com.example.appfood.view.AddDishActivity;
import com.example.appfood.view.DishListActivity;
// etc.
```

## XÓA CÁC FILE CŨ SAU KHI DI CHUYỂN:

Sau khi đã tạo file mới trong package `view`, xóa các file cũ:
- ❌ AddDishActivity.java (ở root appfood/)
- ❌ Dish.java (ở root appfood/) - đã có trong model/
- ❌ Promotion.java (ở root appfood/) - đã có trong model/

## LƯU Ý:
- File trong package `view` sẽ sử dụng Controller
- File cũ ở root sử dụng Firebase trực tiếp
- Sau khi di chuyển xong, cần update AndroidManifest.xml

