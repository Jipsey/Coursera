package PredictiveText.RandomTextStarterProgram;
/**
 * Write a description of class MarkovZero here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.FileResource;

import java.util.Random;

public class MarkovZero extends AbstractMarkovModel{

	public MarkovZero() {
		myRandom = new Random();
	}
	
	public void setRandom(int seed){
		myRandom = new Random(seed);
	}

	public void setTextFromFile() {

		String str = "C:\\Users\\Sanek\\IdeaProjects\\Coursera\\src\\PredictiveText\\" +
				"RandomTextStarterProgram\\data\\confucius.txt";
		FileResource fr = new FileResource(str);
		myText = fr.asString()
				.replace("\n", " ");
		setTraining(myText);
	}
	
	public void setTraining(String s){
		myText = s.trim();
	}
	
	public String getRandomText(int numChars){
		if (myText == null){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(int k=0; k < numChars; k++){
			int index = myRandom.nextInt(myText.length());
			sb.append(myText.charAt(index));
		}
		
		return sb.toString();
	}


	public String toString(){
		return "Markov model of order 0" ;

	}
}
