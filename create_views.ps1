# Script to create all View files with basic structure
# Save this as create_views.ps1 and run it

$baseDir = "C:\Users\vumph\AndroidStudioProjects\appfood\app\src\main\java\com\example\appfood\store\view"

# Create directories
New-Item -ItemType Directory -Force -Path "$baseDir" | Out-Null
New-Item -ItemType Directory -Force -Path "$baseDir\adapter" | Out-Null
New-Item -ItemType Directory -Force -Path "$baseDir\fragment" | Out-Null

# Activity templates
$activities = @(
    "MainActivity",
    "HomeActivity",
    "RegisterStep1Activity",
    "RegisterStep2Activity",
    "DishListActivity",
    "AddDishActivity",
    "EditDishActivity",
    "PromotionListActivity",
    "AddPromotionActivity",
    "EditPromotionActivity",
    "OrdersManagerActivity",
    "NewOrdersActivity",
    "ConfirmedOrdersActivity",
    "ReviewListActivity",
    "ReviewDetailActivity",
    "SalesReportActivity"
)

foreach ($activity in $activities) {
    $content = @"
package com.example.appfood.store.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appfood.R;

/**
 * $activity - Store View
 */
public class $activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Set content view and initialize components
        // setContentView(R.layout.activity_${activity.ToLower().Replace('activity', '')});
    }
}
"@
    $filePath = "$baseDir\$activity.java"
    Set-Content -Path $filePath -Value $content -Encoding UTF8
    Write-Host "Created: $activity.java" -ForegroundColor Green
}

# Adapter templates
$adapters = @("DishAdapter", "PromotionAdapter", "OrdersPagerAdapter")

foreach ($adapter in $adapters) {
    $content = @"
package com.example.appfood.store.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appfood.R;
import java.util.List;

/**
 * $adapter - Adapter for Store View
 */
public class $adapter extends RecyclerView.Adapter<$adapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: Implement view holder creation
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Bind data to view holder
    }

    @Override
    public int getItemCount() {
        // TODO: Return item count
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: Initialize views
        }
    }
}
"@
    $filePath = "$baseDir\adapter\$adapter.java"
    Set-Content -Path $filePath -Value $content -Encoding UTF8
    Write-Host "Created: adapter\$adapter.java" -ForegroundColor Green
}

# Fragment templates
$fragments = @("NewOrdersFragment", "ConfirmedOrdersFragment")

foreach ($fragment in $fragments) {
    $content = @"
package com.example.appfood.store.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appfood.R;

/**
 * $fragment - Fragment for Store View
 */
public class $fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: Inflate layout and initialize components
        // return inflater.inflate(R.layout.fragment_${fragment.ToLower().Replace('fragment', '')}, container, false);
        return null;
    }
}
"@
    $filePath = "$baseDir\fragment\$fragment.java"
    Set-Content -Path $filePath -Value $content -Encoding UTF8
    Write-Host "Created: fragment\$fragment.java" -ForegroundColor Green
}

Write-Host "`nAll View files created successfully!" -ForegroundColor Cyan
Write-Host "Total: $($activities.Count) Activities, $($adapters.Count) Adapters, $($fragments.Count) Fragments" -ForegroundColor Cyan

