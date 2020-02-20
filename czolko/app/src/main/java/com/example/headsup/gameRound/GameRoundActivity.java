package com.example.headsup.gameRound;

import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import com.example.headsup.R;
import com.example.headsup.categories.Category;
import com.example.headsup.databinding.ActivityGameRoundBinding;

public class GameRoundActivity extends AppCompatActivity implements SensorEventListener {

    public static final String CATEGORY = "CATEGORY";
    public static final int GAME_ROUND_ERROR = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityGameRoundBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_game_round);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) finishActivity(GAME_ROUND_ERROR);

        Category category = (Category) bundle.get(CATEGORY);
        if (category == null) finishActivity(GAME_ROUND_ERROR);

        binding.cardViewItemGameRound.setBackgroundResource(category.imageId);
        binding.cardViewItemGameRound.setOnClickListener(view -> handleGuessFail());

        binding.itemGameRound.textViewItemGameRound.setText(category.nameId);
        binding.itemGameRound.textViewCountDownGameRound.setText("3");

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void handleGuessFail() {
        CardView cv = findViewById(R.id.cardViewItemGameRound);
        CardView failCardView = findViewById(R.id.cardViewItemGameRoundFail);
        cv.setVisibility(View.GONE);
        failCardView.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            failCardView.setVisibility(View.GONE);
            cv.setVisibility(View.VISIBLE);
        }, 500);
    }

    private void handleGuessGood() {
        CardView cv = findViewById(R.id.cardViewItemGameRound);
        CardView goodCardView = findViewById(R.id.cardViewItemGameRoundGood);
        cv.setVisibility(View.GONE);
        goodCardView.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            goodCardView.setVisibility(View.GONE);
            cv.setVisibility(View.VISIBLE);
        }, 500);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //x axis: event.values[0]
        //y axis: event.values[1]
        //z axis: event.values[2]
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (event.values[0] < 3.0 && event.values[2] < -8.0) {
                handleGuessGood();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
