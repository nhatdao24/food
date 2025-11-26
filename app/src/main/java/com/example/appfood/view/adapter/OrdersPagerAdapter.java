package com.example.appfood.view.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appfood.view.fragment.ConfirmedOrdersFragment;
import com.example.appfood.view.fragment.NewOrdersFragment;

/**
 * Adapter for ViewPager2 to display order tabs
 */
public class OrdersPagerAdapter extends FragmentStateAdapter {

    public OrdersPagerAdapter(@NonNull AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new NewOrdersFragment();
        } else {
            return new ConfirmedOrdersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

