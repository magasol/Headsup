package com.example.headsup.gameRound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.headsup.R;
import com.example.headsup.databinding.FragmentGameRoundOverBinding;

public class GameRoundOverFragment extends Fragment {

    private static final int RIGHT_ANSWER_POINTS = 3;
    private static final int WRONG_ANSWER_POINTS = -1;

    private FragmentGameRoundOverBinding binding;
    private int rightAnswers;
    private int wrongAnswers;

    public static GameRoundOverFragment newInstance(int rightAnswers, int wrongAnswers) {
        GameRoundOverFragment grf = new GameRoundOverFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("rightAnswers", rightAnswers);
        bundle.putInt("wrongAnswers", wrongAnswers);
        grf.setArguments(bundle);
        return grf;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if (getArguments() != null) {
            rightAnswers = getArguments().getInt("rightAnswers");
            wrongAnswers = getArguments().getInt("wrongAnswers");
        }

        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_game_round_over, parent, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.cardViewItemGameRoundOver.setBackgroundResource(R.drawable.applause);
        binding.textViewRightAnswers
                .setText(String.format("%s %s", getString(R.string.good_game_over), String.valueOf(rightAnswers)));
        binding.textViewWrongAnswers
                .setText(String.format("%s %s", getString(R.string.fail_game_over), String.valueOf(wrongAnswers)));

        int points = RIGHT_ANSWER_POINTS * rightAnswers + WRONG_ANSWER_POINTS * wrongAnswers;
        binding.textViewPoints.setText(String.format("%s %s", getString(R.string.points_game_over), String.valueOf(points)));
    }
}
