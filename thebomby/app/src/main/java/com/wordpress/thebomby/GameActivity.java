package com.wordpress.thebomby;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wordpress.thebomby.device.Bomb;
import com.wordpress.thebomby.device.MockBomb;

public class GameActivity extends Activity {

    GameState state = new GameState();
    Bomb bomb = new MockBomb();
    TextView wordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        wordText = (TextView) findViewById(R.id.wordText);
        updateWord();
    }

    private void updateWord() {
        wordText.setText(state.nextWord());
    }

}
