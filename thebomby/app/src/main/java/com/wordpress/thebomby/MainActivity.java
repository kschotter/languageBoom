package com.wordpress.thebomby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.wordpress.thebomby.device.BluetoothBomb;
import com.wordpress.thebomby.device.BombAccessor;

public class MainActivity extends Activity {

    public static final String KEY_LANGUAGE = "KEY_LANGUAGE";

    String selectedLanguage;

    TextView eng_lang;
    TextView est_lang;
    TextView rus_lang;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eng_lang = (TextView)findViewById(R.id.lang_eng);
        est_lang = (TextView)findViewById(R.id.lang_est);
        rus_lang = (TextView)findViewById(R.id.lang_rus);

        selectedLanguage = loadLanguagePrefs();
        setSelectedLanguage();

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                CharSequence lang = textView.getText();
                selectedLanguage = String.valueOf(lang);
                setSelectedLanguage();
            }
        };

        eng_lang.setOnClickListener(clickListener);
        est_lang.setOnClickListener(clickListener);
        rus_lang.setOnClickListener(clickListener);
        BombAccessor.getBomb().check();

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CountdownActivity.class);
                intent.putExtra(KEY_LANGUAGE, selectedLanguage);
                saveLanguagePrefs(selectedLanguage);
                startActivity(intent);
            }
        });
    }

    private void setSelectedLanguage() {
        eng_lang.setTextColor(0xAAAAAAAA);
        est_lang.setTextColor(0xAAAAAAAA);
        rus_lang.setTextColor(0xAAAAAAAA);

        if("ENG".equals(selectedLanguage)) {
            eng_lang.setTextColor(0xAA1ABAF0);
        } else if ("RUS".equals(selectedLanguage)) {
            rus_lang.setTextColor(0xAA1ABAF0);
        } else {
            est_lang.setTextColor(0xAA1ABAF0);
        }
    }

    private String loadLanguagePrefs() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_LANGUAGE, "EST");
    }

    private void saveLanguagePrefs(String language) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_LANGUAGE, language);
        editor.commit();
    }
}
