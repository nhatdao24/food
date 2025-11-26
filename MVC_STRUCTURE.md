# Cấu trúc MVC của dự án AppFood

## Tổng quan
Dự án đã được tái cấu trúc theo mô hình **MVC (Model-View-Controller)** để dễ bảo trì, mở rộng và tuân thủ các nguyên tắc thiết kế phần mềm tốt.

## Cấu trúc thư mục

```
app/src/main/java/com/example/appfood/
├── model/                          # MODEL LAYER - Data models
│   ├── Shop.java                   # Model cho cửa hàng
│   ├── User.java                   # Model cho người dùng (sẽ phát triển sau)
│   ├── Dish.java                   # Model cho món ăn
│   ├── Promotion.java              # Model cho khuyến mãi
│   ├── Order.java                  # Model cho đơn hàng
│   └── Review.java                 # Model cho đánh giá
│
├── repository/                     # REPOSITORY LAYER - Data access
│   ├── ShopRepository.java         # Interface cho Shop data operations
│   ├── ShopRepositoryImpl.java     # Firebase implementation cho Shop
│   ├── UserRepository.java         # Interface cho User data operations
│   └── UserRepositoryImpl.java     # Firebase implementation cho User
│
├── controller/                     # CONTROLLER LAYER - Business logic
│   ├── ShopController.java         # Controller cho Shop operations
│   └── UserController.java         # Controller cho User operations
│
└── view/                          # VIEW LAYER - Activities & Adapters
    ├── MainActivity.java           # Màn hình chính
    ├── HomeActivity.java           # Màn hình trang chủ
    │
    ├── RegisterStep1Activity.java  # Đăng ký cửa hàng - Bước 1
    ├── RegisterStep2Activity.java  # Đăng ký cửa hàng - Bước 2
    │
    ├── DishListActivity.java       # Danh sách món ăn
    ├── AddDishActivity.java        # Thêm món ăn
    ├── EditDishActivity.java       # Sửa món ăn
    ├── DishAdapter.java            # Adapter cho món ăn
    │
    ├── PromotionListActivity.java  # Danh sách khuyến mãi
    ├── AddPromotionActivity.java   # Thêm khuyến mãi
    ├── EditPromotionActivity.java  # Sửa khuyến mãi
    ├── PromotionAdapter.java       # Adapter cho khuyến mãi
    │
    ├── OrdersManagerActivity.java  # Quản lý đơn hàng
    ├── NewOrdersActivity.java      # Đơn hàng mới
    ├── ConfirmedOrdersActivity.java # Đơn hàng đã xác nhận
    │
    ├── ReviewListActivity.java     # Danh sách đánh giá
    ├── ReviewDetailActivity.java   # Chi tiết đánh giá
    │
    └── SalesReportActivity.java    # Báo cáo doanh thu
```

## Chi tiết các lớp

### 1. MODEL LAYER (Lớp dữ liệu)

#### Shop.java
- **Mục đích**: Đại diện cho thông tin cửa hàng
- **Thuộc tính**:
  - Thông tin cơ bản: storeName, businessType, address, representativeName, phoneNumber, email
  - Giấy tờ tùy thân: idNumber, issueDate, issuePlace
  - Thông tin ngân hàng: bankName, bankAccount, accountHolder, bankBranch
  - Metadata: status, createdAt, updatedAt

#### User.java
- **Mục đích**: Đại diện cho người dùng (sẽ phát triển trong tương lai)
- **Thuộc tính**: userId, username, email, fullName, phoneNumber, address, userType, status

#### Dish.java
- **Mục đích**: Đại diện cho món ăn trong menu
- **Thuộc tính**: dishId, dishName, quantity, price, dishType, restaurant, description

#### Promotion.java
- **Mục đích**: Đại diện cho chương trình khuyến mãi/voucher
- **Thuộc tính**: promotionId, voucherName, discountValue, condition, endDate, quantity, status

#### Order.java
- **Mục đích**: Đại diện cho đơn hàng
- **Thuộc tính**: orderId, customerId, shopId, orderDate, totalAmount, status, etc.

#### Review.java
- **Mục đích**: Đại diện cho đánh giá của khách hàng
- **Thuộc tính**: reviewId, orderId, customerId, shopId, rating, comment, reviewDate

---

### 2. REPOSITORY LAYER (Lớp truy xuất dữ liệu)

#### ShopRepository.java (Interface)
- **Mục đích**: Định nghĩa các phương thức truy xuất dữ liệu Shop
- **Phương thức**:
  - `getAllShops()` - Lấy danh sách tất cả cửa hàng
  - `getShopById(shopId)` - Lấy cửa hàng theo ID
  - `createShop(shop)` - Tạo cửa hàng mới
  - `updateShop(shop)` - Cập nhật thông tin cửa hàng
  - `deleteShop(shopId)` - Xóa cửa hàng
  - `isShopNameExists(shopName)` - Kiểm tra tên cửa hàng đã tồn tại

#### ShopRepositoryImpl.java
- **Mục đích**: Triển khai ShopRepository sử dụng Firebase Realtime Database
- **Pattern**: Singleton
- **Firebase Node**: `shops/`

#### UserRepository.java (Interface)
- **Mục đích**: Định nghĩa các phương thức truy xuất dữ liệu User
- **Phương thức**: Tương tự ShopRepository nhưng cho User

#### UserRepositoryImpl.java
- **Mục đích**: Triển khai UserRepository sử dụng Firebase
- **Pattern**: Singleton
- **Firebase Node**: `users/`

---

### 3. CONTROLLER LAYER (Lớp xử lý nghiệp vụ)

#### ShopController.java
- **Mục đích**: Xử lý logic nghiệp vụ liên quan đến Shop
- **Pattern**: Singleton
- **Chức năng**:
  - Validate dữ liệu Shop trước khi lưu
  - Kiểm tra tên cửa hàng trùng lặp
  - Validate số CMND/CCCD
  - Validate thông tin ngân hàng
  - Gọi Repository để thực hiện CRUD operations

**Ví dụ validation**:
```java
// Validate shop name
if (shop.getStoreName() == null || shop.getStoreName().trim().isEmpty()) {
    return "Tên cửa hàng không được để trống";
}

// Validate phone number format
if (!shop.getPhoneNumber().matches("^[0-9]{10,11}$")) {
    return "Số điện thoại không hợp lệ";
}
```

#### UserController.java
- **Mục đích**: Xử lý logic nghiệp vụ liên quan đến User
- **Pattern**: Singleton
- **Chức năng**:
  - Validate dữ liệu User
  - Kiểm tra username/email trùng lặp
  - Validate mật khẩu
  - Gọi Repository để thực hiện CRUD operations

---

### 4. VIEW LAYER (Lớp giao diện)

#### Activities
Các Activity đã được di chuyển vào package `view` (hoặc giữ ở root để tương thích):
- Hiển thị giao diện người dùng
- Nhận input từ người dùng
- Gọi Controller để xử lý logic
- Cập nhật UI dựa trên kết quả

**Ví dụ: RegisterStep2Activity**
```java
// Sử dụng ShopController
ShopController shopController = ShopController.getInstance();

// Tạo Shop object
Shop shop = new Shop(storeName, businessType, address, ...);

// Gọi Controller để tạo shop
shopController.createShop(shop, new ShopRepository.OnOperationCallback() {
    @Override
    public void onSuccess() {
        // Navigate to HomeActivity
    }
    
    @Override
    public void onFailure(String errorMessage) {
        // Show error message
    }
});
```

---

## Luồng dữ liệu trong MVC

```
USER INPUT (View)
    ↓
CONTROLLER (Validation + Business Logic)
    ↓
REPOSITORY (Data Access)
    ↓
FIREBASE DATABASE
    ↓
REPOSITORY (Parse Data)
    ↓
CONTROLLER (Process Data)
    ↓
VIEW (Update UI)
```

---

## Ưu điểm của cấu trúc MVC này

### 1. **Separation of Concerns** (Tách biệt trách nhiệm)
- Model: Chỉ quản lý dữ liệu
- View: Chỉ hiển thị giao diện
- Controller: Chỉ xử lý logic nghiệp vụ
- Repository: Chỉ truy xuất dữ liệu

### 2. **Dễ bảo trì**
- Thay đổi UI không ảnh hưởng đến logic nghiệp vụ
- Thay đổi database (từ Firebase sang SQL) chỉ cần sửa Repository
- Code được tổ chức rõ ràng, dễ tìm kiếm

### 3. **Dễ mở rộng**
- Thêm chức năng mới chỉ cần tạo Model, Repository, Controller tương ứng
- Có thể tái sử dụng Controller trong nhiều View khác nhau

### 4. **Dễ test**
- Có thể test Controller độc lập với View
- Có thể mock Repository để test Controller
- Có thể test View với mock Controller

### 5. **Singleton Pattern**
- Controller và Repository sử dụng Singleton
- Tiết kiệm bộ nhớ, đảm bảo chỉ có 1 instance

---

## Hướng dẫn sử dụng

### Khi thêm tính năng mới:

1. **Tạo Model** (model/)
   ```java
   public class NewFeature {
       private String id;
       private String name;
       // getters, setters
   }
   ```

2. **Tạo Repository Interface** (repository/)
   ```java
   public interface NewFeatureRepository {
       void getAll(OnDataLoadedCallback<List<NewFeature>> callback);
       void create(NewFeature item, OnOperationCallback callback);
       // ...
   }
   ```

3. **Tạo Repository Implementation** (repository/)
   ```java
   public class NewFeatureRepositoryImpl implements NewFeatureRepository {
       // Firebase implementation
   }
   ```

4. **Tạo Controller** (controller/)
   ```java
   public class NewFeatureController {
       private NewFeatureRepository repository;
       
       public void createItem(NewFeature item, callback) {
           // Validate
           // Call repository
       }
   }
   ```

5. **Tạo Activity/Fragment** (view/)
   ```java
   public class NewFeatureActivity extends AppCompatActivity {
       private NewFeatureController controller;
       
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           controller = NewFeatureController.getInstance();
           // Setup UI
       }
   }
   ```

---

## Roadmap phát triển

### Hiện tại đã hoàn thành:
- ✅ Cấu trúc MVC cơ bản
- ✅ Shop Model, Repository, Controller
- ✅ User Model, Repository, Controller (cấu trúc sẵn)
- ✅ Dish, Promotion, Order, Review Models
- ✅ Tích hợp ShopController vào RegisterStep2Activity

### Cần phát triển tiếp:
- ⏳ Tạo Repository và Controller cho Dish, Promotion, Order, Review
- ⏳ Di chuyển tất cả Activities vào package `view`
- ⏳ Refactor các Activity còn lại để sử dụng Controller
- ⏳ Phát triển chức năng User đầy đủ
- ⏳ Thêm Authentication (Firebase Auth)
- ⏳ Thêm Image Upload cho Dish và Shop
- ⏳ Thêm Real-time notifications
- ⏳ Thêm Unit Tests cho Controller và Repository

---

## Lưu ý quan trọng

1. **Callback Pattern**: Do Firebase là asynchronous, tất cả operations đều sử dụng callback
2. **Validation**: Luôn validate ở Controller trước khi gọi Repository
3. **Error Handling**: Luôn xử lý cả onSuccess và onFailure trong callback
4. **Naming Convention**: 
   - Model: tên danh từ (Shop, User)
   - Repository: tên Model + Repository
   - Controller: tên Model + Controller
   - Activity: mô tả chức năng + Activity

---

## Liên hệ & Hỗ trợ

Nếu có thắc mắc về cấu trúc MVC, vui lòng tham khảo:
- Android Architecture Components
- Clean Architecture
- MVVM Pattern (có thể nâng cấp lên sau)

