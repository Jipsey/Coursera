package Arrays;
import static Arrays.CaesarCipher.mergeWords;


public class CaesarBreaker {


    private String alph;
    private String encrAlph1;
    private String encrAlph2;


    public CaesarBreaker(int key1, int key2) {

        alph = "abcdefghijklmnopqrstuvwxyz";
        encrAlph1 = alph.substring(key1) + alph.substring(0, key1);
        encrAlph2 = alph.substring(key2) + alph.substring(0, key2);
    }

    private int[] countLetters(String s) {

        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] cnt = new int[26];
        int index;
        for (int i = 0; i < s.length(); i++) {
            char ch = Character.toLowerCase(s.charAt(i));
            index = alph.indexOf(ch);

            if (index != -1)
                cnt[index]++;
        }
        return cnt;
    }


    public void decrypWithoutTwoKeys(String s){
        String s1 = decrypt(halfString(s,0));
        String s2 = decrypt(halfString(s,1));

        System.out.println(mergeWords(s1,s2));

    }

    public String decrypt(String encrypted) {

        int maxDex = getKey(encrypted);
        int dKey = maxDex - 4;

        if (maxDex < 4) {
            dKey = 26 - (4 - maxDex);
        }

        System.out.println(" KEY " + dKey);

        CaesarCipher cc = new CaesarCipher(26 - dKey);

        return cc.encrypt(encrypted);
    }


    String halfString(String message, int start) {
        StringBuilder sb = new StringBuilder();

        for (int x = start; x < message.length(); x += 2) {

            sb.append(message.charAt(x));

        }
        return sb.toString();
    }


    public int getKey(String s) {
        WordLengths wl = new WordLengths();
        int[] arr = countLetters(s);
        int index = wl.indexOfMax(arr);
        return index;
    }

    public void decryptTwoKeys(String encrypted) {

        String s1 = halfString(encrypted, 0);
        String s2 = halfString(encrypted, 1);

        s1 = decryptWithKey(s1,1);
        s2 = decryptWithKey(s2,2);
        System.out.println(mergeWords(s1, s2));
    }



    public String decryptWithKey(String s, int numKey ) {

        StringBuilder sb = new StringBuilder();
        String temp = s.toLowerCase();
        String tempAlph;

        if(numKey <= 1 )
        tempAlph = encrAlph1;
                else
                tempAlph = encrAlph2;
        for (int i = 0; i < temp.length(); i++) {

            char tempChar = temp.charAt(i);
            if (tempChar ==' ' || tempChar == '\'') {
               sb.append(tempChar);}

                 char ch;
                 if (Character.isLetter(tempChar))
                    { ch = alph.charAt(tempAlph.indexOf(temp.charAt(i)));
                    sb.append(ch);}
        }
        return sb.toString();
    }
}
