#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script to create all View files and Shop.java without BOM encoding issues
"""
import os

base_dir = r"C:\Users\vumph\AndroidStudioProjects\appfood\app\src\main\java\com\example\appfood\store"

# Shop.java content
shop_java = """package com.example.appfood.store.model;

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

# Activity template
activity_template = """package com.example.appfood.store.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appfood.R;

public class {class_name} extends AppCompatActivity {{
    @Override
    protected void onCreate(Bundle savedInstanceState) {{
        super.onCreate(savedInstanceState);
        // TODO: setContentView(R.layout.activity_{layout_name});
    }}
}}
"""

# Activities to create
activities = [
    "MainActivity", "HomeActivity", "RegisterStep1Activity", "RegisterStep2Activity",
    "DishListActivity", "AddDishActivity", "EditDishActivity",
    "PromotionListActivity", "AddPromotionActivity", "EditPromotionActivity",
    "OrdersManagerActivity", "NewOrdersActivity", "ConfirmedOrdersActivity",
    "ReviewListActivity", "ReviewDetailActivity", "SalesReportActivity"
]

# Create Shop.java
shop_path = os.path.join(base_dir, "model", "Shop.java")
with open(shop_path, 'w', encoding='utf-8', newline='\n') as f:
    f.write(shop_java)
print(f"✓ Created: Shop.java")

# Create Activities
for activity in activities:
    layout_name = activity.replace("Activity", "").lower()
    content = activity_template.format(class_name=activity, layout_name=layout_name)
    file_path = os.path.join(base_dir, "view", f"{activity}.java")
    with open(file_path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(content)
    print(f"✓ Created: {activity}.java")

# Adapter templates
adapters = {
    "DishAdapter": "Dish",
    "PromotionAdapter": "Promotion",
    "OrdersPagerAdapter": None
}

for adapter_name, model_type in adapters.items():
    if adapter_name == "OrdersPagerAdapter":
        content = """package com.example.appfood.store.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.appfood.store.view.fragment.NewOrdersFragment;
import com.example.appfood.store.view.fragment.ConfirmedOrdersFragment;

public class OrdersPagerAdapter extends FragmentStateAdapter {
    public OrdersPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new NewOrdersFragment();
            case 1: return new ConfirmedOrdersFragment();
            default: return new NewOrdersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
"""
    else:
        content = f"""package com.example.appfood.store.view.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appfood.store.model.{model_type};
import java.util.ArrayList;
import java.util.List;

public class {adapter_name} extends RecyclerView.Adapter<{adapter_name}.ViewHolder> {{
    private List<{model_type}> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {{
        return null; // TODO
    }}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {{
        // TODO
    }}

    @Override
    public int getItemCount() {{
        return items.size();
    }}

    static class ViewHolder extends RecyclerView.ViewHolder {{
        public ViewHolder(@NonNull android.view.View itemView) {{
            super(itemView);
        }}
    }}
}}
"""

    file_path = os.path.join(base_dir, "view", "adapter", f"{adapter_name}.java")
    with open(file_path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(content)
    print(f"✓ Created: adapter/{adapter_name}.java")

# Fragment templates
fragments = ["NewOrdersFragment", "ConfirmedOrdersFragment"]

for fragment in fragments:
    content = f"""package com.example.appfood.store.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class {fragment} extends Fragment {{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {{
        return null; // TODO
    }}
}}
"""
    file_path = os.path.join(base_dir, "view", "fragment", f"{fragment}.java")
    with open(file_path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(content)
    print(f"✓ Created: fragment/{fragment}.java")

print("\n" + "="*50)
print("✓ All files created successfully!")
print("="*50)
print(f"\nTotal files created:")
print(f"  - 1 Model (Shop.java)")
print(f"  - {len(activities)} Activities")
print(f"  - {len(adapters)} Adapters")
print(f"  - {len(fragments)} Fragments")
print(f"\nTotal: {1 + len(activities) + len(adapters) + len(fragments)} files")

