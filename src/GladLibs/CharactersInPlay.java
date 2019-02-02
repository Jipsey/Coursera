package GladLibs;
import edu.duke.FileResource;
import java.util.ArrayList;
import java.util.Iterator;


public class CharactersInPlay {

    private ArrayList <String> characters;
    private ArrayList <Integer> freq;

    public CharactersInPlay() {
        characters = new ArrayList<String>();
        freq = new ArrayList <Integer>();
    }

    public void update (String person){

        if(!characters.contains(person)){
            characters.add(person);
            freq.add(characters.indexOf(person),1);
        }
        else {
            int index = characters.indexOf(person);
            int value = freq.get(index);
            freq.set(index,++value);
        }
    }

    public void findAllCharacters(){
        FileResource res = new FileResource();
        for (String s : res.lines())
        {
            s = clearCharacter(s);
           if(isUpperString(s))
               update(s);}
    }

    public int findIndexOfMax() {
        int index = 0;
        int value = 0;
        Iterator<Integer> iterator = freq.iterator();

        while (iterator.hasNext()) {

            if (value < iterator.next()) {
                value = iterator.next();
                index = freq.indexOf(value);
            }

        }

        return index;
    }



    public void tester (int min, int max){
        findAllCharacters();

        for (int i = 0; i <= characters.size() -1 ; i++) {
            if (freq.get(i) > min && freq.get(i) < max) {
                 System.out.println(characters.get(i) + "\t" + freq.get(i));
            }

        }
        System.out.println(characters.get(findIndexOfMax()));
        System.out.println(freq.get(findIndexOfMax()));

    }

   private String clearCharacter(String s){
        StringBuilder sb = new StringBuilder();


       for (int i = 0; i < s.length() ; i++) {
           char ch = s.charAt(i);

           if ( ch =='.')
               break;

           sb.append(ch);
       }

       if ( !s.isEmpty() ) {
           System.out.println(s);
           while (sb.length() > 1 && (sb.charAt(0) == ' ' || sb.charAt(sb.length() - 1) == ' ')) {
               sb = sb.deleteCharAt(0);
               if (sb.charAt(sb.length() - 1) == ' ')
                   sb = sb.deleteCharAt(sb.length() - 1);
           }
       }
        return sb.toString();
   }

   private boolean isUpperString(String s){

       boolean answer = true;

       for (int i = 0; i <s.length() ; i++) {
           char ch = s.charAt(i);
           if (!Character.isUpperCase(ch) && ch != ' '){
               answer = false;
               break;
           }
          }
       if (s.length() == 0  ) answer = false;
       return answer;
   }
}

