package com.example.headsup.gameRound;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.headsup.R;
import com.example.headsup.database.Category;
import com.example.headsup.databinding.FragmentGameRoundStartBinding;

import java.util.Objects;

public class GameRoundStartFragment extends Fragment {

    private static final int[] GAME_ROUND_TIMES = {46, 61, 91};

    private FragmentGameRoundStartBinding binding;
    private Category category;
    private GameRoundActivityListener gameRoundActivityListener;

    static GameRoundStartFragment newInstance(Category category) {
        GameRoundStartFragment grf = new GameRoundStartFragment();
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("category", category);
        grf.setArguments(bundle);
        return grf;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameRoundActivityListener = (GameRoundActivityListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if (getArguments() != null)
            category = (Category) getArguments().getSerializable("category");

        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_game_round_start, parent, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int imageId = Objects.requireNonNull(getContext())
                .getResources()
                .getIdentifier(category.imageName, "drawable", getContext().getPackageName());
        binding.cardViewItemGameRoundStart.setBackgroundResource(imageId);
        binding.buttonNextGameRoundStart.setOnClickListener(v -> startGameRound());
    }

    private void startGameRound() {
        int roundTimeId;
        if(binding.roundTime45.isChecked()) roundTimeId=GAME_ROUND_TIMES[RoundTime.time45s.ordinal()];
        else if(binding.roundTime60.isChecked()) roundTimeId=GAME_ROUND_TIMES[RoundTime.time60s.ordinal()];
        else roundTimeId=GAME_ROUND_TIMES[RoundTime.time90s.ordinal()];

        gameRoundActivityListener.startGameRound(roundTimeId);
    }
}
