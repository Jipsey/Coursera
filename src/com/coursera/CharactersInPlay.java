package com.coursera;

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CharactersInPlay {

    private ArrayList<String> characters;
    private ArrayList<Integer> counts;
    String characterName;


    CharactersInPlay() {
        characters = new ArrayList<>();
        counts = new ArrayList<>();

    }

    void update(String person) {

        int cnt = 1;
        int index;
        if (characters.isEmpty() || !characters.contains(person)) {
            characters.add(person);
            counts.add(cnt);
        } else if (characters.contains(person)) {
            index = characters.indexOf(person);// получаем индекс в листе данного персонажа
            cnt = counts.get(index);           // получаем значение счетчика для данного персонажа
            counts.set(index, ++cnt);
        }
    }

    void findAllCharacters() {
        FileResource fr = new FileResource();
        String[] arrWords;
        for (String line : fr.lines()) {
            arrWords = line.split("\\.", 0);
            for (String word : arrWords) {

                if (!word.isEmpty() && isUpperCase(word)) {
                    update(word);
                    break;
                }
            }
        }
        findMainCharacter();
    }

    private boolean isUpperCase(String s) {

        String[] arrStr = s.split("\\s");
        char ch;
        for (String sWord : arrStr) {
            if(!sWord.isEmpty())
            for (int i = 0; i < sWord.length(); i++) {
                ch = sWord.charAt(i);
                if (!Character.isUpperCase(ch) && Character.isAlphabetic(ch))
                    return false;
            }
        }
        return true;
    }

    private void findMainCharacter() {
        int index = 0;
        int num = 0;
        for (int i : counts) {
            if (i > num) {
                num = i;
                index = counts.indexOf(i);
            }
        }
        System.out.println("The main character is: " + characters.get(index) + " " + "occurs " + counts.get(index) + " times");
        System.out.println("-------------------------------");
        sortArray();
        for (int i = 0; i < characters.size(); i++) {

            System.out.println(characters.get(i) + "  " + counts.get(i));
        }
    }

    private  void  sortArray(){
        ArrayList<Integer> newCounts = new ArrayList<>();
        ArrayList<String> newCharacters = new ArrayList<>();
        newCounts.addAll(counts);

        int index;
        Collections.sort(newCounts);

        for(int occ:newCounts){

            index = counts.indexOf(occ);
            counts.remove(index);
            newCharacters.add(characters.remove(index));
        }
        characters.addAll(newCharacters);
        counts.addAll(newCounts);
    }
}
