package BreakingTheVegenereCipher;

import java.io.File;
import java.util.*;

import edu.duke.*;

public class VigenereBreaker {

    private int[] key;
    private int validWords;


    public String sliceString(String message, int whichSlice, int totalSlices) {

        StringBuilder sb = new StringBuilder();
        for (; whichSlice < message.length(); whichSlice += totalSlices) {
            sb = sb.append(message.charAt(whichSlice));
        }

        return sb.toString();
    }

    // метод возвращает [] int заполненный ключом из метода CaesarCracker.getKey().
    // полученный из каждого смещения зашифрованного сообщения (метод sliceString()),
    // зная длину ключа, начиная с индекса 0 (i=0).
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);

        for (int i = 0; i < klength; i++) {
            String sliced = sliceString(encrypted, i, klength);
            int x = cc.getKey(sliced);
            key[i] = x;
        }
        return key;
    }

    public void breakVigenere(String filePath) {

        String decryptedMessage;
        FileResource fr = new FileResource(filePath);
        HashMap<String, HashSet<String>> mapOfDictionaries
                = readDictionaries();

        decryptedMessage = breakForAllLanguages(fr.asString(), mapOfDictionaries);
        System.out.println(decryptedMessage);
        printKey(key);
    }

    private HashMap<String, HashSet<String>> readDictionaries() {

        HashMap<String, HashSet<String>> mapOfDictionaries = new HashMap<>();
        String languageName;

        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {

             languageName = f.getName();
             HashSet<String> setOfWords = readDictionary(f);
             mapOfDictionaries.put(languageName,setOfWords);

        }
        return mapOfDictionaries;
    }


    private void printKey(int[] x) {


        System.out.println("\n" + "-------------------------");
        System.out.print("The key is: ");
        for (int i : x) {
            System.out.print(i + ", ");
        }
        System.out.println("\n" + "-------------------------");
        System.out.println("The size of the key: " + x.length + " symbols.");
        System.out.println("-------------------------");

        System.out.println(validWords + " words");
    }


    public HashSet<String> readDictionary(File file) {

        HashSet<String> set = new HashSet<>();
        FileResource fr = new FileResource(file);
        for (String dictionaryWord : fr.lines()) {
            if( set.isEmpty() ||!set.contains(dictionaryWord))
             set.add(dictionaryWord.toLowerCase());
        }

        return set;
    }

    public int countWords(String message, HashSet<String> dictionary) {

        int counter = 0;

        for (String word : message.split(("\\W+"))) {
            word = word.toLowerCase();
            if (dictionary.contains(word))
                counter++;
        }

        return counter;
    }


    // метод подбирающий ключ перебором возможной длины ключа от 1 до 100
    // другой вариант, это перебор от 1 до encrypted.size().
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {

        int keyCounter = 0;
        String decryptedMessage = "";
        char mostCommonChar = mostCommonCharIn(dictionary);

        for (int i = 1; i <= 100; i++) {

            int[] key = tryKeyLength(encrypted, i, mostCommonChar);
            VigenereCipher vc = new VigenereCipher(key);
            String temp = vc.decrypt(encrypted);

            int wordsCounter = countWords(temp, dictionary);
            if (keyCounter < wordsCounter) {
                keyCounter = wordsCounter;
                decryptedMessage = temp;
                this.key = key;
                validWords = wordsCounter;
            }
        }
        return decryptedMessage;
    }

    private HashMap<Character, Integer> makeMapfromString(String str) {

        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < str.length(); i++) {
            map.put(str.charAt(i), 1);
        }
        return map;
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {

        String alph = "abcdefghijklmnopqrstuvwxyz";
        HashMap<Character, Integer> alphabetMap = makeMapfromString(alph);
        char mostCommonChar = '?';
        int num = 0;


        for (String word : dictionary) {
            alphabetMap = updateMap(alphabetMap, word);
        }

        for (Map.Entry<Character, Integer> entry : alphabetMap.entrySet()) {

            int cnt = entry.getValue();
            if (num < cnt) {
                num = cnt;
                mostCommonChar = entry.getKey();
            }
        }

        return mostCommonChar;
    }



    private HashMap<Character, Integer> updateMap(HashMap<Character, Integer> incMap, String word) {
        HashMap<Character, Integer> alphabetMap = new HashMap<>(incMap);


        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (alphabetMap.containsKey(ch)) {
                int cnt = alphabetMap.get(ch);
                alphabetMap.put(ch, ++cnt);
            }
        }

        return alphabetMap;
    }


    public String breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {

        String decrypted = "";
        HashSet<String> setOfWords;
        int num = 0;
        String langName = "***UNKNOWN***";

        for (Map.Entry<String, HashSet<String>> entry :
                languages.entrySet()) {
            setOfWords = entry.getValue();
            String temp = breakForLanguage(encrypted, setOfWords);
            int cnt = countWords(temp, setOfWords);
            if (num < cnt) {
                num = cnt;
                decrypted = temp;
                langName = entry.getKey();
            }
        }

        System.out.println(langName + " language detected.");
        return decrypted;
    }

}
