package com.example.headsup.gameRound;

import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.example.headsup.R;
import com.example.headsup.categories.Category;
import com.example.headsup.databinding.ActivityGameRoundBinding;

public class GameRoundActivity extends AppCompatActivity implements SensorEventListener {

    public static final String CATEGORY = "CATEGORY";
    public static final String GAME_ROUND_FRAG_TAG = "GAME_ROUND_FRAG_TAG";
    public static final int GAME_ROUND_ERROR = -1;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityGameRoundBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_game_round);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) finishActivity(GAME_ROUND_ERROR);

        Category category = (Category) bundle.get(CATEGORY);
        if (category == null) finishActivity(GAME_ROUND_ERROR);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.gameRoundContainer, GameRoundFragment.newInstance(category), GAME_ROUND_FRAG_TAG)
                .commit();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //x axis: event.values[0]
        //y axis: event.values[1]
        //z axis: event.values[2]
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (event.values[0] < 3.0 && event.values[2] < -8.0) {
                GameRoundFragment grf = (GameRoundFragment) manager.findFragmentByTag(GAME_ROUND_FRAG_TAG);
                if (grf != null) {
                    grf.handleGuessGood();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
