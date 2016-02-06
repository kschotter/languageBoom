package com.wordpress.thebomby;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    GameState(String language) {
        if(language == "ENG") {
            words = DefaultWords.WORDS_ENG;
        } else if(language == "RUS") {
            words = DefaultWords.WORDS_RUS;
        }
        words = DefaultWords.WORDS_EST;
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
