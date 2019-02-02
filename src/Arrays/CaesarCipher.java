package Arrays;


public class CaesarCipher {

    private String alphabet;
    private String encrAlphabet ;

    public CaesarCipher(int key) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        encrAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
    }

    public String encrypt(String input) {

        StringBuilder sb = new StringBuilder();
        int x;
        String temp = input.toUpperCase();
        for (int i = 0; i < input.length(); i++) {

            char t = temp.charAt(i);
            if (alphabet.contains(Character.toString(t))) {
                x = alphabet.indexOf(t);
                if (Character.isLowerCase(input.charAt(i))) {
                    sb.append(Character.toLowerCase(encrAlphabet.charAt(x)));
                } else sb.append(encrAlphabet.charAt(x));
            } else sb.append(t);
        }
        return sb.toString();
    }


    public static String mergeWords (String s1, String s2){
        StringBuilder sb = new StringBuilder();

        int length = Math.max(s1.length(),s2.length());

        for (int i = 0; i < length; i++) {

            if(i < s1.length())
            sb.append(s1.charAt(i));

            if(i < s2.length())
            sb.append(s2.charAt(i));
        }
        return sb.toString();

    }
}
