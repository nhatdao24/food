# Sơ đồ cấu trúc MVC - AppFood

## 1. Tổng quan cấu trúc Package

```
┌─────────────────────────────────────────────────────────────┐
│                    com.example.appfood                       │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────┐    ┌──────────────┐    ┌──────────────┐   │
│  │   MODEL     │    │  REPOSITORY  │    │  CONTROLLER  │   │
│  │             │◄───│              │◄───│              │   │
│  │  - Shop     │    │  - ShopRepo  │    │  - ShopCtrl  │   │
│  │  - User     │    │  - UserRepo  │    │  - UserCtrl  │   │
│  │  - Dish     │    │              │    │              │   │
│  │  - Promo    │    │              │    │              │   │
│  │  - Order    │    │              │    │              │   │
│  │  - Review   │    │              │    │              │   │
│  └─────────────┘    └──────────────┘    └──────────────┘   │
│                                                 ▲            │
│                                                 │            │
│  ┌──────────────────────────────────────────────┘            │
│  │                                                           │
│  │         VIEW LAYER (Activities & Adapters)               │
│  │  ┌─────────────────────────────────────────────┐        │
│  │  │  - RegisterStep1Activity                     │        │
│  │  │  - RegisterStep2Activity (✅ đã tích hợp)   │        │
│  │  │  - HomeActivity                              │        │
│  │  │  - DishListActivity                          │        │
│  │  │  - PromotionListActivity                     │        │
│  │  │  - OrdersManagerActivity                     │        │
│  │  │  - ReviewListActivity                        │        │
│  │  │  - SalesReportActivity                       │        │
│  │  │  - DishAdapter, PromotionAdapter             │        │
│  │  └─────────────────────────────────────────────┘        │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## 2. Luồng dữ liệu MVC - Tạo Shop

```
┌──────────────────────────────────────────────────────────────────┐
│                    USER ACTION: Nhập thông tin Shop              │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  VIEW: RegisterStep2Activity                                     │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  1. Thu thập dữ liệu từ EditText                           │ │
│  │  2. Tạo Shop object                                         │ │
│  │  3. Gọi shopController.createShop(shop, callback)          │ │
│  └────────────────────────────────────────────────────────────┘ │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  CONTROLLER: ShopController                                      │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  1. Validate dữ liệu Shop:                                  │ │
│  │     - Kiểm tra tên, email, phone format                    │ │
│  │     - Kiểm tra trường bắt buộc                             │ │
│  │  2. Kiểm tra tên Shop đã tồn tại chưa                      │ │
│  │  3. Nếu OK → gọi repository.createShop()                   │ │
│  │  4. Nếu Lỗi → callback.onFailure(errorMessage)            │ │
│  └────────────────────────────────────────────────────────────┘ │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  REPOSITORY: ShopRepositoryImpl                                  │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  1. Tạo shopId từ Firebase push()                          │ │
│  │  2. Set createdAt, updatedAt timestamp                     │ │
│  │  3. Lưu vào Firebase: shops/{shopId}                       │ │
│  │  4. Xử lý success/failure callback                         │ │
│  └────────────────────────────────────────────────────────────┘ │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  FIREBASE REALTIME DATABASE                                      │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  shops/                                                     │ │
│  │    └── {shopId}/                                            │ │
│  │          ├── storeName: "Quán Ăn ABC"                      │ │
│  │          ├── businessType: "Nhà hàng"                      │ │
│  │          ├── address: "123 Đường XYZ"                      │ │
│  │          ├── phoneNumber: "0987654321"                     │ │
│  │          ├── email: "abc@gmail.com"                        │ │
│  │          ├── idNumber: "123456789"                         │ │
│  │          ├── bankAccount: "1234567890"                     │ │
│  │          ├── status: "active"                              │ │
│  │          ├── createdAt: 1700000000                         │ │
│  │          └── updatedAt: 1700000000                         │ │
│  └────────────────────────────────────────────────────────────┘ │
└────────────────────────────┬─────────────────────────────────────┘
                             │
                             ▼
                    ┌────────────────┐
                    │   onSuccess    │
                    └────────┬───────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  VIEW: Cập nhật UI                                               │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  - Hiển thị Toast "Đăng ký thành công!"                    │ │
│  │  - Navigate to HomeActivity                                 │ │
│  │  - Clear back stack                                         │ │
│  └────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

## 3. Cấu trúc Class Diagram - Shop Module

```
┌─────────────────────────────────────────────────┐
│              Shop (Model)                        │
├─────────────────────────────────────────────────┤
│  - storeId: String                               │
│  - storeName: String                             │
│  - businessType: String                          │
│  - address: String                               │
│  - representativeName: String                    │
│  - phoneNumber: String                           │
│  - email: String                                 │
│  - idNumber: String                              │
│  - issueDate: String                             │
│  - issuePlace: String                            │
│  - bankName: String                              │
│  - bankAccount: String                           │
│  - accountHolder: String                         │
│  - bankBranch: String                            │
│  - status: String                                │
│  - createdAt: long                               │
│  - updatedAt: long                               │
├─────────────────────────────────────────────────┤
│  + getters() / setters()                         │
└─────────────────────────────────────────────────┘
                    ▲
                    │ uses
                    │
┌─────────────────────────────────────────────────┐
│      ShopRepository (Interface)                  │
├─────────────────────────────────────────────────┤
│  + getAllShops(callback)                         │
│  + getShopById(id, callback)                     │
│  + createShop(shop, callback)                    │
│  + updateShop(shop, callback)                    │
│  + deleteShop(id, callback)                      │
│  + isShopNameExists(name, callback)              │
└─────────────────────────────────────────────────┘
                    ▲
                    │ implements
                    │
┌─────────────────────────────────────────────────┐
│      ShopRepositoryImpl                          │
├─────────────────────────────────────────────────┤
│  - databaseReference: DatabaseReference          │
│  - instance: ShopRepositoryImpl (static)         │
├─────────────────────────────────────────────────┤
│  + getInstance(): ShopRepositoryImpl             │
│  + getAllShops(callback)                         │
│  + getShopById(id, callback)                     │
│  + createShop(shop, callback)                    │
│  + updateShop(shop, callback)                    │
│  + deleteShop(id, callback)                      │
│  + isShopNameExists(name, callback)              │
└─────────────────────────────────────────────────┘
                    ▲
                    │ uses
                    │
┌─────────────────────────────────────────────────┐
│          ShopController                          │
├─────────────────────────────────────────────────┤
│  - shopRepository: ShopRepository                │
│  - instance: ShopController (static)             │
├─────────────────────────────────────────────────┤
│  + getInstance(): ShopController                 │
│  + getAllShops(callback)                         │
│  + getShopById(id, callback)                     │
│  + createShop(shop, callback)                    │
│  + updateShop(shop, callback)                    │
│  + deleteShop(id, callback)                      │
│  - validateShop(shop): String                    │
│  + validateIdNumber(id): String                  │
│  + validateBankAccount(account): String          │
└─────────────────────────────────────────────────┘
                    ▲
                    │ uses
                    │
┌─────────────────────────────────────────────────┐
│      RegisterStep2Activity (View)                │
├─────────────────────────────────────────────────┤
│  - shopController: ShopController                │
│  - etIdNumber: EditText                          │
│  - etBankAccount: EditText                       │
│  - btnComplete: Button                           │
│  - storeName, email, phone... : String           │
├─────────────────────────────────────────────────┤
│  # onCreate(bundle)                              │
│  - initViews()                                   │
│  - setupListeners()                              │
│  - validateInputs(): boolean                     │
│  - completeRegistration()                        │
└─────────────────────────────────────────────────┘
```

## 4. So sánh Code Cũ vs Code Mới

### CODE CŨ (Không có MVC):
```
┌─────────────────────────────────────┐
│  RegisterStep2Activity              │
│  ┌───────────────────────────────┐  │
│  │  - Validate dữ liệu           │  │
│  │  - Tạo HashMap                │  │
│  │  - Gọi Firebase trực tiếp     │  │
│  │  - Xử lý callback             │  │
│  │  - Cập nhật UI                │  │
│  │                               │  │
│  │  Tất cả logic trong 1 class! │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│  Firebase Database                   │
└─────────────────────────────────────┘
```

### CODE MỚI (Có MVC):
```
┌─────────────────────────────────────┐
│  RegisterStep2Activity (View)       │
│  - Chỉ xử lý UI                     │
│  - Thu thập input                   │
│  - Gọi Controller                   │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  ShopController (Controller)        │
│  - Validate dữ liệu                 │
│  - Business logic                   │
│  - Gọi Repository                   │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  ShopRepositoryImpl (Repository)    │
│  - CRUD operations                  │
│  - Firebase operations              │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  Firebase Database                   │
└─────────────────────────────────────┘
```

## 5. Callback Flow

```
Activity
   │
   │ createShop(shop, callback)
   ▼
Controller
   │
   │ validate
   ├─✗─► onFailure("Validation error")
   │
   │ createShop(shop, callback)
   ▼
Repository
   │
   │ Firebase.setValue()
   ├─✓─► onSuccess()
   │
   └─✗─► onFailure("Firebase error")
   
   │
   ▼
Activity
   │
   └─► Update UI
```

## 6. Singleton Pattern

```
┌────────────────────────────────────┐
│  ShopController (Singleton)        │
├────────────────────────────────────┤
│  private static instance           │
│                                    │
│  private ShopController() {}       │  ◄─── Private constructor
│                                    │
│  public static getInstance() {     │  ◄─── Public static method
│    if (instance == null)           │
│      instance = new...             │
│    return instance;                │
│  }                                 │
└────────────────────────────────────┘

Lợi ích:
✅ Chỉ có 1 instance duy nhất
✅ Tiết kiệm bộ nhớ
✅ Truy cập toàn cục
✅ Thread-safe với synchronized
```

---

## Legend:
- ✅ Đã hoàn thành
- ⏳ Đang phát triển
- ❌ Chưa bắt đầu
- ▲ Kế thừa/Implements
- ◄── Sử dụng/Gọi

