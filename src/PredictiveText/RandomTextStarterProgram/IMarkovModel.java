package PredictiveText.RandomTextStarterProgram;

/**
 * Write a description of interface IMarkovModel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface IMarkovModel {
    void setTraining(String text);
    
    String getRandomText(int numChars);

    void setRandom(int seed);
}
