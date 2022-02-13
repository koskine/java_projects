import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class WordGame {
    private ArrayList<String> wordList;
    private boolean active;
    private WordGameState currentGame;
    private String currentWord;

    public static class WordGameState {
        private String word;
        private int mistakes;
        private int mistakeLimit;
        private int missingChars;

        private WordGameState(String word, int max, int mistakes, int missing) {
            this.word = word;
            this.mistakes = mistakes;
            this.mistakeLimit = max;
            this.missingChars = missing;
        }

        public String getWord() {
            return this.word;
        }

        public int getMistakes() {
            return this.mistakes;
        }

        public int getMistakeLimit() {
            return this.mistakeLimit;
        }

        public int getMissingChars() {
            return this.missingChars;
        }

        
    }

    public WordGame(String wordFilename) throws IOException {
        this.wordList = new ArrayList<String>();
        try(var file = new BufferedReader(new FileReader(wordFilename))) {
            String newWord;
            while((newWord = file.readLine()) != null){
                this.wordList.add(newWord);
            }
        }
    }

    public void initGame(int wordIndex, int mistakeLimit) {
        int mistakes = 0;
        String word = this.wordList.get(wordIndex % this.wordList.size());
        int wordLength = word.length();
        String wordToPlayer = "_".repeat(wordLength);
        this.currentGame = new WordGameState(wordToPlayer, mistakeLimit, mistakes, wordLength);
        this.active = true;
        this.currentWord = word;
    }

    public boolean isGameActive() {
        return this.active;
    }

    public WordGameState getGameState() throws GameStateException {
        if (!this.active){
            throw new GameStateException("There is currently no active word game!");
        }
        return this.currentGame;
    }

    public WordGameState guess(char c) throws GameStateException {
        if (!this.active){
            throw new GameStateException("There is currently no active word game!");
        }
        int currentMistakes = this.currentGame.getMistakes();
        int mistakesLimit = this.currentGame.getMistakeLimit();
        int missing = this.currentGame.getMissingChars();
        String wordToPlayer = this.currentGame.getWord();
        char d = Character.toLowerCase(c);
        if (wordToPlayer.indexOf(d) != -1) {
            int newMistakes = currentMistakes + 1;
            this.currentGame = new WordGameState(wordToPlayer, mistakesLimit, newMistakes, missing);
            return this.currentGame;
        }
        else if (this.currentWord.indexOf(d) == -1) {
            int newMistakes = currentMistakes + 1;
            if (currentMistakes >= mistakesLimit) {
                this.active = false;
                this.currentGame = new WordGameState(this.currentWord, mistakesLimit, newMistakes, missing);
            }
            else {
                this.currentGame = new WordGameState(wordToPlayer, mistakesLimit, newMistakes, missing);
            }
            return this.currentGame;
        }
        else {
            // vaihdetaan pelaajalle näytettävästä sanasta kaikki oikeat kirjaimet näkyväksi
            char[] playerWordAsArray = wordToPlayer.toCharArray();
            for (int i = 0; i < this.currentWord.length(); i++) {               
                if (d == this.currentWord.charAt(i)) {
                    playerWordAsArray[i] = d;
                    missing--;
                }
            }
            wordToPlayer = String.valueOf(playerWordAsArray);
            if (missing == 0) {
                this.active = false;
            }
            this.currentGame = new WordGameState(wordToPlayer, mistakesLimit, currentMistakes, missing);
            return this.currentGame;
        }
    }

    public WordGameState guess(String word) throws GameStateException {
        if (!this.active){
            throw new GameStateException("There is currently no active word game!");
        }
        int currentMistakes = this.currentGame.getMistakes();
        int mistakesLimit = this.currentGame.getMistakeLimit();
        int missing = this.currentGame.getMissingChars();
        String wordToPlayer = this.currentGame.getWord();
        if (word.equalsIgnoreCase(this.currentWord)) {
            missing = 0;
            this.currentGame = new WordGameState(word, mistakesLimit, currentMistakes, missing);
            this.active = false;
            return this.currentGame;
        }
        else {
            int newMistakes = currentMistakes + 1;
            if (currentMistakes >= mistakesLimit) {
                this.active = false;
                this.currentGame = new WordGameState(this.currentWord, mistakesLimit, newMistakes, missing);
            }
            else {
                this.currentGame = new WordGameState(wordToPlayer, mistakesLimit, newMistakes, missing);
            }
            return this.currentGame;
        }
    } 
}
