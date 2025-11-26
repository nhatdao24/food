# 🔧 Sửa lỗi nút Run không ấn được

## Làm theo thứ tự:

### Bước 1: Sync Gradle (QUAN TRỌNG NHẤT)
1. Trong Android Studio, nhìn lên thanh menu trên cùng
2. Click: **File** → **Sync Project with Gradle Files**
3. Hoặc tìm icon Gradle (hình con voi) trên toolbar và click
4. Đợi 10-30 giây cho đến khi thanh progress ở dưới chạy xong

### Bước 2: Kiểm tra Run Configuration
1. Nhìn thanh toolbar, tìm dropdown bên cạnh nút Run (▶)
2. Click vào dropdown đó
3. Chọn **Edit Configurations...**
4. Kiểm tra:
   - **Module**: phải là `app` hoặc `giaodien.app`
   - **Launch**: chọn `Default Activity` HOẶC `Specified Activity` → chọn `MainActivity`
5. Click **Apply** → **OK**

### Bước 3: Rebuild Project
1. Click menu: **Build** → **Clean Project**
2. Đợi xong
3. Click menu: **Build** → **Rebuild Project**  
4. Đợi build hoàn thành

### Bước 4: Chọn thiết bị
1. Nhìn thanh toolbar, tìm dropdown "No devices"
2. Click vào đó
3. Chọn:
   - **Emulator** (nếu đã tạo) HOẶC
   - **Device Manager** → Create Virtual Device → chọn Pixel 5 → Next → chọn API 34 → Finish

### Bước 5: Chạy app
- Nhấn nút Run (▶) màu xanh
- Hoặc nhấn phím: **Shift + F10**

---

## ❌ Nếu vẫn không được:

### Giải pháp 1: Invalidate Caches
```
File → Invalidate Caches... → chọn cả 3 options → Invalidate and Restart
```
Đợi Android Studio khởi động lại (khoảng 1-2 phút)

### Giải pháp 2: Chạy bằng command line
Mở Terminal trong Android Studio (tab dưới cùng) và chạy:
```bash
gradlew.bat installDebug
```

### Giải pháp 3: Kiểm tra SDK
```
File → Settings → Appearance & Behavior → System Settings → Android SDK
```
Đảm bảo đã cài:
- ✅ Android 14.0 (API 34)
- ✅ Android SDK Build-Tools
- ✅ Android Emulator

---

## 🎯 Checklist:

- [ ] Đã sync Gradle
- [ ] Đã chọn Module = app
- [ ] Đã chọn thiết bị/emulator
- [ ] Đã Rebuild Project
- [ ] Nút Run đã sáng lên

**Hãy thử Bước 1 trước tiên - Sync Gradle là quan trọng nhất!**

