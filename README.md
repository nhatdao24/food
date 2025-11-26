# Food App - Ứng dụng đặt đồ ăn

## Giao diện đã hoàn thành

### 1. Màn hình Đăng ký (2 bước)

#### Màn hình Đăng ký Bước 1 (`RegisterStep1Activity`)
- Layout: `activity_register_step1.xml`
- Các trường:
  - Tên cửa hàng
  - Loại hình kinh doanh (Spinner)
  - Địa chỉ cụ thể
  - Họ tên người đại diện
  - Số điện thoại
  - Email
- Nút "Tiếp tục" để chuyển sang bước 2

#### Màn hình Đăng ký Bước 2 (`RegisterStep2Activity`)
- Layout: `activity_register_step2.xml`
- Các trường:
  - Số CMND/CCCD
  - Ngày cấp (Spinner)
  - Nơi cấp
  - Tên ngân hàng
  - Số tài khoản ngân hàng
  - Tên chủ tài khoản
  - Chi nhánh ngân hàng
- Nút "Hoàn tất" để hoàn thành đăng ký

## Màu sắc sử dụng

- **Primary Orange**: #FF9800
- **Primary Orange Dark**: #F57C00
- **Primary Orange Light**: #FFB74D
- **Text Primary**: #212121
- **Text Secondary**: #757575
- **Background**: #FFFFFF

## Các file resources đã tạo

### Layouts
- `activity_register_step1.xml` - Layout cho màn hình đăng ký bước 1
- `activity_register_step2.xml` - Layout cho màn hình đăng ký bước 2

### Drawables
- `bg_input_field.xml` - Background cho input fields
- `bg_button_primary.xml` - Background cho button chính
- `progress_indicator.xml` - Thanh tiến trình ở đầu màn hình

### Values
- `colors.xml` - Định nghĩa màu sắc
- `strings.xml` - Các chuỗi text
- `dimens.xml` - Các kích thước chuẩn

## Hướng dẫn chạy

1. Mở project trong Android Studio
2. Sync Gradle
3. Chạy app trên emulator hoặc thiết bị thật
4. App sẽ tự động mở màn hình Đăng ký Bước 1

## Validation đã có

- Kiểm tra rỗng cho tất cả các trường
- Validate số điện thoại (10-11 số)
- Validate email (định dạng email chuẩn)
- Validate số CMND/CCCD (9-12 số)

## Các màn hình cần thiết kế tiếp

1. Màn hình Đăng nhập
2. Màn hình Trang chủ (Home)
3. Màn hình Chi tiết món ăn
4. Màn hình Giỏ hàng (Cart)
5. Màn hình Đơn hàng (Orders)
6. Màn hình Hồ sơ (Profile)
7. v.v...

## Ghi chú

- ViewBinding đã được enable trong build.gradle.kts
- Code hiện tại chỉ focus vào UI, chưa có backend
- Sau khi hoàn thành tất cả giao diện, bạn có thể bắt đầu làm:
  - Model classes
  - Database (Room hoặc Firebase)
  - API integration
  - Authentication
  - Payment integration

## Liên hệ

Nếu cần thêm màn hình hoặc chỉnh sửa giao diện, hãy cung cấp Figma design!

