package com.example.project_prm392.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.project_prm392.R;
import com.example.project_prm392.fragments.AdditionalInfoFragment;
import com.example.project_prm392.fragments.DescriptionFragment;
import com.example.project_prm392.adapters.ProductPagerAdapter;
import com.example.project_prm392.fragments.ReviewFragment;
import com.google.android.material.tabs.TabLayout;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductPagerAdapter productPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Khởi tạo ViewPager và TabLayout
        viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Khởi tạo Adapter cho ViewPager
        productPagerAdapter = new ProductPagerAdapter(getSupportFragmentManager());
        productPagerAdapter.addFragment(new DescriptionFragment(), "Description");
        productPagerAdapter.addFragment(new ReviewFragment(), "Review");
        productPagerAdapter.addFragment(new AdditionalInfoFragment(), "Additional Information");

        // Thiết lập Adapter cho ViewPager và kết nối ViewPager với TabLayout
        viewPager.setAdapter(productPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Không cần thực hiện
            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = productPagerAdapter.getItem(position);
                View view = fragment.getView();
                if (view != null) {
                    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    int height = view.getMeasuredHeight(); // Tính toán chiều cao của Fragment hiện tại
                    viewPager.getLayoutParams().height = height; // Đặt chiều cao của ViewPager bằng giá trị tính toán
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Không cần thực hiện
            }
        });

    }



}