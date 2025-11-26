# Script tự động xóa file cũ sau khi migration MVC

Write-Host "🔍 Kiểm tra file cũ cần xóa..." -ForegroundColor Yellow

$rootPath = "C:\Users\vumph\AndroidStudioProjects\appfood\app\src\main\java\com\example\appfood"

# Danh sách file cần xóa ở root package (đã có trong view/)
$filesToDelete = @(
    "AddDishActivity.java",
    "AddPromotionActivity.java",
    "ConfirmedOrdersActivity.java",
    "DishListActivity.java",
    "EditDishActivity.java",
    "EditPromotionActivity.java",
    "HomeActivity.java",
    "MainActivity.java",
    "NewOrdersActivity.java",
    "OrdersManagerActivity.java",
    "PromotionListActivity.java",
    "RegisterStep1Activity.java",
    "RegisterStep2Activity.java",
    "ReviewDetailActivity.java",
    "ReviewListActivity.java",
    "SalesReportActivity.java",
    "DishAdapter.java",
    "PromotionAdapter.java",
    "OrdersPagerAdapter.java",
    "NewOrdersFragment.java",
    "ConfirmedOrdersFragment.java",
    "Dish.java",           # Đã có trong model/
    "Promotion.java"       # Đã có trong model/
)

Write-Host "`n📝 File sẽ bị xóa:" -ForegroundColor Cyan
$filesToDelete | ForEach-Object {
    $fullPath = Join-Path $rootPath $_
    if (Test-Path $fullPath) {
        Write-Host "  ❌ $_" -ForegroundColor Red
    } else {
        Write-Host "  ⚠️  $_ (không tồn tại)" -ForegroundColor Gray
    }
}

Write-Host "`n⚠️  CẢNH BÁO: Script này sẽ XÓA các file trên!" -ForegroundColor Red
Write-Host "Đảm bảo bạn đã tạo xong tất cả file mới trong package view/" -ForegroundColor Red
$confirm = Read-Host "`nBạn có chắc chắn muốn xóa? (yes/no)"

if ($confirm -eq "yes") {
    Write-Host "`n🗑️  Đang xóa file..." -ForegroundColor Yellow

    $deletedCount = 0
    $notFoundCount = 0

    foreach ($file in $filesToDelete) {
        $fullPath = Join-Path $rootPath $file
        if (Test-Path $fullPath) {
            try {
                Remove-Item $fullPath -Force
                Write-Host "  ✅ Đã xóa: $file" -ForegroundColor Green
                $deletedCount++
            }
            catch {
                Write-Host "  ❌ Lỗi khi xóa: $file - $_" -ForegroundColor Red
            }
        } else {
            $notFoundCount++
        }
    }

    Write-Host "`n📊 Kết quả:" -ForegroundColor Cyan
    Write-Host "  ✅ Đã xóa: $deletedCount file" -ForegroundColor Green
    Write-Host "  ⚠️  Không tìm thấy: $notFoundCount file" -ForegroundColor Gray
    Write-Host "`n✨ Hoàn thành!" -ForegroundColor Green

} else {
    Write-Host "`n❌ Đã hủy. Không có file nào bị xóa." -ForegroundColor Yellow
}

Write-Host "`n📌 Lưu ý:" -ForegroundColor Cyan
Write-Host "  - Kiểm tra lại cấu trúc MVC trong package view/" -ForegroundColor White
Write-Host "  - Build lại project để đảm bảo không có lỗi" -ForegroundColor White
Write-Host "  - Chạy app để test các chức năng" -ForegroundColor White

