package com.example.headsup.categories;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headsup.R;
import com.example.headsup.database.Category;
import com.example.headsup.database.HeadsupDatabase;
import com.example.headsup.databinding.ActivityCategoriesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CategoriesActivity extends AppCompatActivity {

    private CategoriesAdapter adapter;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoriesBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_categories);

        loadCategories();
        adapter = new CategoriesAdapter(this, categories);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerViewCategories.setLayoutManager(mLayoutManager);
        binding.recyclerViewCategories.setAdapter(adapter);

    }

    private void loadCategories() {
        HeadsupDatabase hd = HeadsupDatabase.getInstance(this);
        Executors.newSingleThreadExecutor().execute(() -> {
            categories.clear();
            categories.addAll(hd.categoryDao().getAll());
            adapter.notifyDataSetChanged();
        });
    }
}
