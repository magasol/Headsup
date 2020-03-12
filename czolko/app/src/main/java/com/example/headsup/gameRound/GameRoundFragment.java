package com.example.headsup.gameRound;

//timer implementation https://stackoverflow.com/questions/4597690/how-to-set-timer-in-android

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.headsup.R;
import com.example.headsup.database.Category;
import com.example.headsup.database.Guess;
import com.example.headsup.database.HeadsupDatabase;
import com.example.headsup.databinding.FragmentGameRoundBinding;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;

public class GameRoundFragment extends Fragment {

    private FragmentGameRoundBinding binding;
    private Category category;
    private GameRoundActivityListener gameRoundActivityListener;

    private List<Guess> guesses;
    private int guessIndex = 0;
    private int rightAnswers = 0;
    private int wrongAnswers = 0;

    private int gameRoundTime;
    private long startTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = gameRoundTime * 1000 - (System.currentTimeMillis() - startTime);
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if (seconds >= 0 || minutes > 0) {
                binding.itemGameRound.textViewCountDownGameRound
                        .setText(String.format(Locale.getDefault(), "%d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 500);
            } else {
                gameOver();
            }
        }
    };

    private MediaPlayer mpRight;
    private MediaPlayer mpWrong;

    static GameRoundFragment newInstance(Category category, int gameRoundTime) {
        GameRoundFragment grf = new GameRoundFragment();
        Bundle bundle = new Bundle(2);
        bundle.putSerializable("category", category);
        bundle.putSerializable("gameRoundTime", gameRoundTime);
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
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable("category");
            gameRoundTime = getArguments().getInt("gameRoundTime");
        } else {
            gameOver();
        }

        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_game_round, parent, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int imageId = Objects.requireNonNull(getContext())
                .getResources()
                .getIdentifier(category.imageName, "drawable", getContext().getPackageName());
        binding.cardViewItemGameRound.setBackgroundResource(imageId);
        binding.cardViewItemGameRound.setOnClickListener(v -> handleGuessFail());

        loadNewGuessesList();
    }

    @Override
    public void onResume() {
        super.onResume();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        mpRight = MediaPlayer.create(getContext(), R.raw.right);
        mpWrong = MediaPlayer.create(getContext(), R.raw.wrong);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        mpRight.release();
        mpWrong.release();
    }

    void handleGuessGood() {
        Executors.newSingleThreadExecutor().execute(() -> mpRight.start());

        binding.cardViewItemGameRound.setVisibility(View.GONE);
        binding.cardViewItemGameRoundGood.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            binding.cardViewItemGameRoundGood.setVisibility(View.GONE);
            binding.cardViewItemGameRound.setVisibility(View.VISIBLE);
            loadNewGuess();
        }, 600);

        rightAnswers++;
    }

    private void handleGuessFail() {
        Executors.newSingleThreadExecutor().execute(() -> mpWrong.start());

        binding.cardViewItemGameRound.setVisibility(View.GONE);
        binding.cardViewItemGameRoundFail.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            binding.cardViewItemGameRoundFail.setVisibility(View.GONE);
            binding.cardViewItemGameRound.setVisibility(View.VISIBLE);
            loadNewGuess();
        }, 600);

        wrongAnswers++;
    }

    private void loadNewGuessesList() {
        HeadsupDatabase hd = HeadsupDatabase.getInstance(this.getActivity());
        Executors.newSingleThreadExecutor().execute(() -> {
            guesses = hd.guessDao().getRandomByCategory(category.name);
            if (guesses == null)
                gameOver();
            loadNewGuess();
        });
    }

    private void loadNewGuess() {
        if (guessIndex == guesses.size())
            gameOver();
        String guessName = guesses.get(guessIndex).name;
        guessIndex++;
        binding.itemGameRound.textViewItemGameRound.setText(guessName);
    }

    private void gameOver() {
        gameRoundActivityListener.gameRoundOver(rightAnswers, wrongAnswers);
    }
}
