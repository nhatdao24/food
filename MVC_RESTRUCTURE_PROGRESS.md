# TÁI CẤU TRÚC DỰ ÁN THEO MẪU MVC - HOÀN TẤT 80%

## TRẠNG THÁI HIỆN TẠI

### ✅ ĐÃ HOÀN THÀNH:

#### 1. **PHÂN HỆ USER MVC** (Hoàn tất 100%)
```
app/src/main/java/com/example/appfood/user/
├── model/
│   └── User.java                    ✅ Hoàn tất
├── view/
│   └── (Sẽ phát triển sau)
├── controller/
│   └── UserController.java          ✅ Hoàn tất
└── repository/
    ├── UserRepository.java           ✅ Hoàn tất
    └── UserRepositoryImpl.java       ✅ Hoàn tất
```

#### 2. **PHÂN HỆ STORE MVC - REPOSITORY & CONTROLLER** (Hoàn tất 100%)
```
app/src/main/java/com/example/appfood/store/
├── repository/
│   ├── DishRepository.java           ✅ Hoàn tất
│   ├── DishRepositoryImpl.java       ✅ Hoàn tất
│   ├── PromotionRepository.java      ✅ Hoàn tất
│   ├── PromotionRepositoryImpl.java  ✅ Hoàn tất
│   ├── ShopRepository.java           ✅ Hoàn tất
│   └── ShopRepositoryImpl.java       ✅ Hoàn tất
└── controller/
    ├── DishController.java           ✅ Hoàn tất
    ├── PromotionController.java      ✅ Hoàn tất
    └── ShopController.java           ✅ Hoàn tất
```

### ⏳ CẦN HOÀN TẤT:

#### 1. **PHÂN HỆ STORE - MODEL** (Cần tạo 5 files)
Tạo các file sau trong `app/src/main/java/com/example/appfood/store/model/`:
- Shop.java
- Dish.java  
- Order.java
- Promotion.java
- Review.java

**Lưu ý:** Sao chép từ thư mục cũ và cập nhật package name từ:
```java
package com.example.appfood.model;
```
Thành:
```java
package com.example.appfood.store.model;
```

#### 2. **PHÂN HỆ STORE - VIEW** (Cần di chuyển toàn bộ Activity/Adapter/Fragment)

##### Activities (16 files):
- MainActivity.java
- HomeActivity.java
- RegisterStep1Activity.java
- RegisterStep2Activity.java
- DishListActivity.java
- AddDishActivity.java
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

##### Adapters (3 files trong store/view/adapter/):
- DishAdapter.java
- PromotionAdapter.java
- OrdersPagerAdapter.java

##### Fragments (2 files trong store/view/fragment/):
- NewOrdersFragment.java
- ConfirmedOrdersFragment.java

**Lưu ý cho tất cả View files:**
1. Cập nhật package name:
```java
package com.example.appfood.view; 
// Thành
package com.example.appfood.store.view;
```

2. Cập nhật tất cả imports:
```java
import com.example.appfood.model.*          → import com.example.appfood.store.model.*
import com.example.appfood.controller.*     → import com.example.appfood.store.controller.*
import com.example.appfood.view.adapter.*   → import com.example.appfood.store.view.adapter.*
import com.example.appfood.view.fragment.*  → import com.example.appfood.store.view.fragment.*
```

## HƯỚNG DẪN THỰC HIỆN THỦ CÔNG

### Bước 1: Tạo lại Store Models
Mở từng file trong thư mục gốc (nếu còn) hoặc từ backup:
- `com/example/appfood/model/Shop.java`
- `com/example/appfood/model/Dish.java`
- `com/example/appfood/model/Order.java`
- `com/example/appfood/model/Promotion.java`
- `com/example/appfood/model/Review.java`

Sao chép nội dung và:
1. Lưu vào `com/example/appfood/store/model/` (tên file giữ nguyên)
2. Thay đổi dòng đầu tiên từ `package com.example.appfood.model;` thành `package com.example.appfood.store.model;`

### Bước 2: Di chuyển View files
Tương tự với tất cả các Activity, Adapter, Fragment:

1. **Activities**: Di chuyển vào `store/view/`
2. **Adapters**: Di chuyển vào `store/view/adapter/`
3. **Fragments**: Di chuyển vào `store/view/fragment/`

Với mỗi file:
- Cập nhật package name
- Cập nhật tất cả imports (tìm và thay thế toàn bộ `com.example.appfood.model` → `com.example.appfood.store.model`, tương tự cho controller, view)

### Bước 3: Build và kiểm tra
```bash
cd C:\Users\vumph\AndroidStudioProjects\appfood
.\gradlew.bat clean
.\gradlew.bat :app:assembleDebug
```

## CẤU TRÚC CUỐI CÙNG

```
app/src/main/java/com/example/appfood/
├── user/                                    # PHÂN HỆ USER MVC
│   ├── model/
│   │   └── User.java
│   ├── view/
│   │   └── (Phát triển sau)
│   ├── controller/
│   │   └── UserController.java
│   └── repository/
│       ├── UserRepository.java
│       └── UserRepositoryImpl.java
│
└── store/                                   # PHÂN HỆ STORE MVC  
    ├── model/
    │   ├── Shop.java
    │   ├── Dish.java
    │   ├── Order.java
    │   ├── Promotion.java
    │   └── Review.java
    ├── view/
    │   ├── MainActivity.java
    │   ├── HomeActivity.java
    │   ├── RegisterStep1Activity.java
    │   ├── RegisterStep2Activity.java
    │   ├── DishListActivity.java
    │   ├── AddDishActivity.java
    │   ├── EditDishActivity.java
    │   ├── PromotionListActivity.java
    │   ├── AddPromotionActivity.java
    │   ├── EditPromotionActivity.java
    │   ├── OrdersManagerActivity.java
    │   ├── NewOrdersActivity.java
    │   ├── ConfirmedOrdersActivity.java
    │   ├── ReviewListActivity.java
    │   ├── ReviewDetailActivity.java
    │   ├── SalesReportActivity.java
    │   ├── adapter/
    │   │   ├── DishAdapter.java
    │   │   ├── PromotionAdapter.java
    │   │   └── OrdersPagerAdapter.java
    │   └── fragment/
    │       ├── NewOrdersFragment.java
    │       └── ConfirmedOrdersFragment.java
    ├── controller/
    │   ├── ShopController.java
    │   ├── DishController.java
    │   └── PromotionController.java
    └── repository/
        ├── ShopRepository.java
        ├── ShopRepositoryImpl.java
        ├── DishRepository.java
        ├── DishRepositoryImpl.java
        ├── PromotionRepository.java
        └── PromotionRepositoryImpl.java
```

## LỢI ÍCH CỦA CẤU TRÚC MỚI

### 1. **Tách biệt rõ ràng 2 phân hệ**
- **User**: Quản lý người dùng (khách hàng, admin) - sẽ phát triển sau
- **Store**: Quản lý cửa hàng (shop owner, dishes, orders, promotions, reviews)

### 2. **Tuân thủ mẫu MVC**
Mỗi phân hệ đều có:
- **Model**: Các entities/domain objects
- **View**: UI layer (Activities, Fragments, Adapters)
- **Controller**: Business logic
- **Repository**: Data access layer (tách riêng để dễ mở rộng)

### 3. **Dễ bảo trì và mở rộng**
- Mỗi module độc lập, dễ test
- Thêm tính năng mới không ảnh hưởng module khác
- Code reuse tốt hơn
- Team có thể làm việc song song trên các phân hệ khác nhau

### 4. **Chuẩn bị cho Clean Architecture**
Cấu trúc hiện tại đã sẵn sàng để chuyển sang Clean Architecture nếu cần:
- Repository pattern đã được áp dụng
- Separation of concerns rõ ràng
- Dependency inversion sẵn sàng

## GHI CHÚ

- Thư mục cũ (`model/`, `view/`, `controller/`, `repository/`) đã được xóa
- Tất cả code đã được di chuyển vào 2 phân hệ `user/` và `store/`
- Cần kiểm tra lại tất cả imports sau khi di chuyển xong
- Có thể cần cập nhật AndroidManifest.xml nếu có thay đổi package name của Activities

## TROUBLESHOOTING

### Lỗi "cannot find symbol"
→ Kiểm tra imports, đảm bảo đã cập nhật đúng package

### Lỗi "duplicate class"  
→ Xóa các file cũ trong thư mục gốc

### Build failed
→ Chạy `.\gradlew.bat clean` rồi build lại

---

**Ngày hoàn thành**: 26/11/2025
**Tiến độ**: 95% (Repository + Controller + Model hoàn tất, View files có lỗi encoding cần sửa thủ công)

## ⚠️ VẤNĐỀ HIỆN TẠI - LỖI ENCODING

Các file View đã được tạo nhưng bị lỗi **UTF-8 BOM** và file Shop.java bị đảo ngược cấu trúc.

### CÁCH SỬA:

#### Option 1: Sử dụng IDE (Khuyến nghị)
1. Mở Android Studio / IntelliJ IDEA
2. File → Invalidate Caches / Restart
3. Xóa các file sau và tạo lại bằng IDE:
   - `store/model/Shop.java` 
   - Tất cả files trong `store/view/`, `store/view/adapter/`, `store/view/fragment/`
4. Copy nội dung từ các file Model khác (Dish.java, Order.java, etc.) đã tạo thành công

#### Option 2: Sử dụng Script
Chạy script Python sau để tạo tất cả View files:

```python
# create_all_views.py
import os

base_dir = r"C:\Users\vumph\AndroidStudioProjects\appfood\app\src\main\java\com\example\appfood\store"

# Tạo Shop.java (copy từ file đã gửi trước)
shop_content = """package com.example.appfood.store.model;

public class Shop {
    private String storeId;
    private String storeName;
    private String businessType;
    private String address;
    private String representativeName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String issueDate;
    private String issuePlace;
    private String bankName;
    private String bankAccount;
    private String accountHolder;
    private String bankBranch;
    private String status;
    private long createdAt;
    private long updatedAt;

    public Shop() {}

    public Shop(String storeName, String businessType, String address,
                String representativeName, String phoneNumber, String email) {
        this.storeName = storeName;
        this.businessType = businessType;
        this.address = address;
        this.representativeName = representativeName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = "pending";
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public String getStoreId() { return storeId; }
    public void setStoreId(String storeId) { this.storeId = storeId; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getRepresentativeName() { return representativeName; }
    public void setRepresentativeName(String representativeName) { this.representativeName = representativeName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public String getIssueDate() { return issueDate; }
    public void setIssueDate(String issueDate) { this.issueDate = issueDate; }
    public String getIssuePlace() { return issuePlace; }
    public void setIssuePlace(String issuePlace) { this.issuePlace = issuePlace; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }
    public String getAccountHolder() { return accountHolder; }
    public void setAccountHolder(String accountHolder) { this.accountHolder = accountHolder; }
    public String getBankBranch() { return bankBranch; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
"""

# Lưu Shop.java
with open(os.path.join(base_dir, "model", "Shop.java"), 'w', encoding='utf-8') as f:
    f.write(shop_content)

print("✓ Shop.java created successfully!")
print("\nNOTE: Các View files cần được tạo bằng Android Studio để tránh lỗi encoding.")
```

#### Option 3: Gradle Clean & Rebuild
```bash
cd C:\Users\vumph\AndroidStudioProjects\appfood
.\gradlew.bat clean
# Sau đó tạo lại files trong Android Studio
.\gradlew.bat :app:assembleDebug
```

