# Hướng dẫn cấu trúc MVC - AppFood

## 📁 Cấu trúc dự án đã được tái tổ chức

Dự án đã được cấu trúc lại theo mô hình **MVC (Model-View-Controller)** chuẩn:

```
com.example.appfood/
├── model/              # Dữ liệu (Shop, User, Dish, Promotion, Order, Review)
├── repository/         # Truy xuất dữ liệu (Firebase operations)
├── controller/         # Xử lý logic nghiệp vụ (Validation, business rules)
└── (Activities)        # Giao diện người dùng
```

## 🎯 Thành phần đã hoàn thành

### ✅ Model Layer
- `Shop.java` - Model cửa hàng (đầy đủ)
- `User.java` - Model người dùng (sẵn sàng phát triển)
- `Dish.java` - Model món ăn
- `Promotion.java` - Model khuyến mãi
- `Order.java` - Model đơn hàng
- `Review.java` - Model đánh giá

### ✅ Repository Layer
- `ShopRepository.java` + `ShopRepositoryImpl.java` - CRUD cho Shop
- `UserRepository.java` + `UserRepositoryImpl.java` - CRUD cho User (sẵn sàng)

### ✅ Controller Layer
- `ShopController.java` - Xử lý logic Shop (validation, business rules)
- `UserController.java` - Xử lý logic User (sẵn sàng)

### ✅ View Layer đã cập nhật
- `RegisterStep2Activity.java` - Đã tích hợp ShopController
- Tất cả Adapter đã import model từ package `model`

## 🚀 Cách sử dụng

### Ví dụ: Tạo cửa hàng mới

```java
// 1. Lấy instance của Controller
ShopController shopController = ShopController.getInstance();

// 2. Tạo Shop object
Shop shop = new Shop(storeName, businessType, address, 
                     representativeName, phoneNumber, email);
shop.setIdNumber(idNumber);
shop.setBankName(bankName);
// ... set các thuộc tính khác

// 3. Gọi Controller để tạo shop
shopController.createShop(shop, new ShopRepository.OnOperationCallback() {
    @Override
    public void onSuccess() {
        Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        // Navigate to next screen
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(context, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
});
```

### Ví dụ: Lấy danh sách cửa hàng

```java
ShopController shopController = ShopController.getInstance();

shopController.getAllShops(new ShopRepository.OnDataLoadedCallback<List<Shop>>() {
    @Override
    public void onSuccess(List<Shop> shops) {
        // Hiển thị danh sách lên RecyclerView
        adapter.updateData(shops);
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(context, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
});
```

## 📊 Firebase Database Structure

```
appfood-database/
├── shops/                  # Cửa hàng (đã cập nhật từ "stores")
│   ├── {shopId}/
│   │   ├── storeName
│   │   ├── businessType
│   │   ├── address
│   │   ├── phoneNumber
│   │   ├── email
│   │   ├── idNumber
│   │   ├── bankAccount
│   │   └── ...
│
├── users/                  # Người dùng (sẽ phát triển)
├── dishes/                 # Món ăn
├── promotions/            # Khuyến mãi
├── orders/                # Đơn hàng
└── reviews/               # Đánh giá
```

## 🔄 Migration từ code cũ

### Trước đây (trực tiếp Firebase):
```java
DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
HashMap<String, Object> storeInfo = new HashMap<>();
storeInfo.put("storeName", storeName);
// ...
mDatabase.child("stores").child(storeId).setValue(storeInfo);
```

### Bây giờ (MVC):
```java
ShopController controller = ShopController.getInstance();
Shop shop = new Shop(storeName, ...);
controller.createShop(shop, callback);
```

## 📝 Lợi ích

1. **Code rõ ràng hơn** - Dễ đọc, dễ hiểu
2. **Dễ bảo trì** - Thay đổi database không ảnh hưởng đến View
3. **Validation tập trung** - Tất cả validation ở Controller
4. **Tái sử dụng** - Controller có thể dùng cho nhiều View
5. **Sẵn sàng mở rộng** - Dễ thêm tính năng User, Dish, Order...

## 📖 Tài liệu chi tiết

Xem file `MVC_STRUCTURE.md` để biết thêm chi tiết về:
- Kiến trúc MVC đầy đủ
- Luồng dữ liệu
- Hướng dẫn thêm tính năng mới
- Best practices

## 🔜 Kế hoạch tiếp theo

1. Tạo Repository & Controller cho Dish
2. Tạo Repository & Controller cho Promotion
3. Tạo Repository & Controller cho Order
4. Tạo Repository & Controller cho Review
5. Refactor tất cả Activities để sử dụng Controller
6. Phát triển chức năng User (đăng ký, đăng nhập)
7. Thêm Authentication (Firebase Auth)
8. Thêm Image Upload

---

**Lưu ý**: Node Firebase đã được đổi từ `stores` → `shops` để thống nhất với tên Model.

