package com.wordpress.thebomby;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {


    private long time;

    private String[] words;

    private int currentWord = 0;

    GameState(String language) {
        if("ENG".equals(language)) {
            words = DefaultWords.WORDS_ENG;
        } else if("RUS".equals(language)) {
            words = DefaultWords.WORDS_RUS;
        } else {
            words = DefaultWords.WORDS_EST;
        }
    }

    public String nextWord() {
        if(currentWord == words.length) {
            currentWord = 0;
        }
        String word = words[currentWord++];
        return word;
    };

    public String currentWord() {
        return words[currentWord];
    }

}
