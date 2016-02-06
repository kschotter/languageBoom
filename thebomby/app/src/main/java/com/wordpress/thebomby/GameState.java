package com.wordpress.thebomby;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    GameState(String language) {
        if(language.equals("ENG")) {
            words = DefaultWords.WORDS_ENG;
        } else if(language.equals("RUS")) {
            words = DefaultWords.WORDS_RUS;
        } else {
            words = DefaultWords.WORDS_EST;
        }
    }

    String[] words;

    int currentWord = -1;

    public String nextWord() {
        return words[++currentWord];
    };

    public String currentWord() {
        return words[currentWord];
    }

}
