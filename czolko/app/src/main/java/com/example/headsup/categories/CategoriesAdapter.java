package com.example.headsup.categories;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.headsup.R;
import com.example.headsup.categories.CategoriesAdapter.CategoriesViewHolder;
import com.example.headsup.gameRound.GameRoundActivity;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    private Context mContext;
    private List<Category> categoriesList;

    class CategoriesViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout linearLayout;

        CategoriesViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.TextViewItemCategory);
            linearLayout = view.findViewById(R.id.linearLayoutItemCategory);
        }
    }

    CategoriesAdapter(Context mContext, List<Category> categoriesList) {
        this.mContext = mContext;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesViewHolder holder, int position) {
        Category category = categoriesList.get(position);
        holder.name.setText(category.nameId);

        int imageId = mContext
                .getResources()
                .getIdentifier(category.imageName, "drawable", mContext.getPackageName());
        Glide
                .with(mContext)
                .load(imageId)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.linearLayout.setBackground(resource);
                    }
                });

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, GameRoundActivity.class);
            intent.putExtra(GameRoundActivity.CATEGORY, category);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}
