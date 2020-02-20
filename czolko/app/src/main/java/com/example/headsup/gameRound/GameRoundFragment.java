package com.example.headsup.gameRound;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.headsup.R;
import com.example.headsup.categories.Category;
import com.example.headsup.databinding.FragmentGameRoundBinding;

import java.util.Locale;

public class GameRoundFragment extends Fragment {

    private static final int GAME_ROUND_TIME = 11;

    private FragmentGameRoundBinding binding;
    private Category category;
    private GameRoundActivityListener gameRoundActivityListener;

    private int rightAnswers = 0;
    private int wrongAnswers = 0;

    private long startTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = GAME_ROUND_TIME * 1000 - (System.currentTimeMillis() - startTime);
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if (seconds >= 0 || minutes > 0) {
                binding.itemGameRound.textViewCountDownGameRound
                        .setText(String.format(Locale.getDefault(), "%d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 500);
            } else {
                gameRoundActivityListener.gameRoundOver(rightAnswers, wrongAnswers);
            }
        }
    };

    public static GameRoundFragment newInstance(Category category) {
        GameRoundFragment grf = new GameRoundFragment();
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
                .inflate(inflater, R.layout.fragment_game_round, parent, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.cardViewItemGameRound.setBackgroundResource(category.imageId);
        binding.cardViewItemGameRound.setOnClickListener(v -> handleGuessFail());

        binding.itemGameRound.textViewItemGameRound.setText(category.nameId);
    }

    @Override
    public void onResume() {
        super.onResume();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void handleGuessGood() {
        binding.cardViewItemGameRound.setVisibility(View.GONE);
        binding.cardViewItemGameRoundGood.setVisibility(View.VISIBLE);

        rightAnswers++;

        new Handler().postDelayed(() -> {
            binding.cardViewItemGameRoundGood.setVisibility(View.GONE);
            binding.cardViewItemGameRound.setVisibility(View.VISIBLE);
        }, 600);
    }

    private void handleGuessFail() {
        binding.cardViewItemGameRound.setVisibility(View.GONE);
        binding.cardViewItemGameRoundFail.setVisibility(View.VISIBLE);

        wrongAnswers++;

        new Handler().postDelayed(() -> {
            binding.cardViewItemGameRoundFail.setVisibility(View.GONE);
            binding.cardViewItemGameRound.setVisibility(View.VISIBLE);
        }, 600);
    }
}
