package com.wordpress.thebomby;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wordpress.thebomby.device.BombAccessor;
import com.wordpress.thebomby.device.BombListener;

import java.util.Random;

public class GameActivity extends Activity implements BombListener {

    GameState state;
    TextView wordText;

    RelativeLayout restartView;
    private View nextButton;
    private View skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String language = getIntent().getStringExtra(MainActivity.KEY_LANGUAGE);
        state = new GameState(language);
        setContentView(R.layout.activity_game);
        wordText = (TextView) findViewById(R.id.wordText);
        restartView = (RelativeLayout) findViewById(R.id.restartView);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);
        startBomb();
        updateWord();
        useHandler();
    }

    private void updateWord() {
        String word = state.nextWord();
        wordText.setText(word);
        BombAccessor.getBomb().showWord(word);
    }

    private void startBomb() {
        BombAccessor.getBomb().start(this);
    }

    Handler mHandler;

    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 10000L + new Random(System.currentTimeMillis()).nextInt(50000));
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            Log.e("Handlers", "Calls");
            long currentTime = System.currentTimeMillis();
            if(currentTime > state.getTime()) {
                BombAccessor.getBomb().timerDone();
                wordText.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);
                skipButton.setVisibility(View.GONE);

                restartView.setVisibility(View.VISIBLE);
                restartView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        restartView.setVisibility(View.INVISIBLE);
                    }
                });

                mHandler.removeCallbacks(mRunnable);
            } else {
                mHandler.postDelayed(mRunnable, 1000);
            }
        }
    };

    @Override
    public void nextWord() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateWord();
            }
        });
    }

    @Override
    public void skipWord() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateWord();
            }
        });
    }

}
