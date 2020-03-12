package com.example.headsup.gameRound;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.example.headsup.R;
import com.example.headsup.database.Category;
import com.example.headsup.databinding.ActivityGameRoundBinding;

public class GameRoundActivity extends AppCompatActivity implements SensorEventListener, GameRoundActivityListener {

    public static final String CATEGORY = "CATEGORY";
    public static final String GAME_ROUND_START_FRAG_TAG = "GAME_ROUND_START_FRAG_TAG";
    public static final String GAME_ROUND_FRAG_TAG = "GAME_ROUND_FRAG_TAG";
    public static final String GAME_ROUND_OVER_FRAG_TAG = "GAME_ROUND_OVER_FRAG_TAG";
    public static final int GAME_ROUND_ERROR = -1;

    FragmentManager manager;
    Category category;

    boolean readyForGoodGuess = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityGameRoundBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_game_round);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) finishActivity(GAME_ROUND_ERROR);

        category = (Category) bundle.get(CATEGORY);
        if (category == null) finishActivity(GAME_ROUND_ERROR);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        hideNavigationBar();

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.gameRoundContainer, GameRoundStartFragment.newInstance(category), GAME_ROUND_START_FRAG_TAG)
                .commit();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //x axis: event.values[0]
        //y axis: event.values[1]
        //z axis: event.values[2]
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (event.values[0] < 3.0 && event.values[2] < -8.0 && readyForGoodGuess) {
                readyForGoodGuess = false;
                GameRoundFragment grf = (GameRoundFragment) manager.findFragmentByTag(GAME_ROUND_FRAG_TAG);
                if (grf != null) {
                    grf.handleGuessGood();
                }
            } else if (event.values[0] > 8.0 && event.values[2] > -3.0)
                readyForGoodGuess = true;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void gameRoundOver(int rightAnswers, int wrongAnswers) {
        GameRoundFragment grf = (GameRoundFragment) manager.findFragmentByTag(GAME_ROUND_FRAG_TAG);
        if (grf != null)
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down)
                    .remove(grf)
                    .commit();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down)
                .add(R.id.gameRoundContainer,
                        GameRoundOverFragment.newInstance(rightAnswers, wrongAnswers),
                        GAME_ROUND_OVER_FRAG_TAG)
                .commit();
    }

    @Override
    public void startGameRound(int roundTime) {
        GameRoundStartFragment grf = (GameRoundStartFragment) manager.findFragmentByTag(GAME_ROUND_START_FRAG_TAG);
        if (grf != null)
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down)
                    .remove(grf)
                    .commit();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down)
                .add(R.id.gameRoundContainer, GameRoundFragment.newInstance(category, roundTime), GAME_ROUND_FRAG_TAG)
                .commit();
    }


    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
