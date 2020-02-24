package com.example.headsup.categories;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headsup.R;
import com.example.headsup.databinding.ActivityCategoriesBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private CategoriesAdapter adapter;
    private List<Category> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoriesBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_categories);

        categoriesList = new ArrayList<>();
        adapter = new CategoriesAdapter(this, categoriesList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerViewCategories.setLayoutManager(mLayoutManager);
        binding.recyclerViewCategories.setAdapter(adapter);

        prepareCategories();
    }

    private void prepareCategories() {
        categoriesList.add(new Category(R.string.children_stories, "children_stories"));
        categoriesList.add(new Category(R.string.famous_people,"famous_people"));
        categoriesList.add(new Category(R.string.books,"books"));
        categoriesList.add(new Category(R.string.songs, "songs"));
        categoriesList.add(new Category(R.string.movies, "movies"));
    }
}
