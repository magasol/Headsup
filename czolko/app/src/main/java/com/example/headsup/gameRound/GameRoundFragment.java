package com.example.headsup.gameRound;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.headsup.R;
import com.example.headsup.categories.Category;
import com.example.headsup.databinding.FragmentGameRoundBinding;

public class GameRoundFragment extends Fragment {

    private FragmentGameRoundBinding binding;
    private Category category;

    public static GameRoundFragment newInstance(Category category) {
        GameRoundFragment grf = new GameRoundFragment();
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("category", category);
        grf.setArguments(bundle);
        return grf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if (getArguments() != null)
            category = (Category) getArguments().getSerializable("category");

        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_game_round, parent, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.cardViewItemGameRound.setBackgroundResource(category.imageId);
        binding.cardViewItemGameRound.setOnClickListener(v -> handleGuessFail());

        binding.itemGameRound.textViewItemGameRound.setText(category.nameId);
        binding.itemGameRound.textViewCountDownGameRound.setText("3");
    }

    public void handleGuessGood() {
        binding.cardViewItemGameRound.setVisibility(View.GONE);
        binding.cardViewItemGameRoundGood.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            binding.cardViewItemGameRoundGood.setVisibility(View.GONE);
            binding.cardViewItemGameRound.setVisibility(View.VISIBLE);
        }, 600);
    }

    private void handleGuessFail() {
        binding.cardViewItemGameRound.setVisibility(View.GONE);
        binding.cardViewItemGameRoundFail.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            binding.cardViewItemGameRoundFail.setVisibility(View.GONE);
            binding.cardViewItemGameRound.setVisibility(View.VISIBLE);
        }, 600);
    }
}
