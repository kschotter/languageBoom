package com.wordpress.thebomby;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    String[] words = DefaultWords.WORDS;

    int currentWord = -1;

    public String nextWord() {
        return words[++currentWord];
    };

    public String currentWord() {
        return words[currentWord];
    }

}
