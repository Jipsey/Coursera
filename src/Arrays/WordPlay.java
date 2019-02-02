package Arrays;


public class WordPlay {

    public boolean isVowel(char ch) {
        char[] arr = {'a', 'e', 'i', 'o', 'u'};

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Character.toLowerCase(ch))
                return true;
        }
        return false;
    }


    public String replaceVowels(String phrase, char ch) {

        String temp = phrase.toLowerCase();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < phrase.length(); i++) {
            if (isVowel(temp.charAt(i))) sb.append(ch);
            else sb.append(phrase.charAt(i));
        }
        return sb.toString();
    }

    public String emphrasize(String phrase, char ch) {

        String temp = phrase.toLowerCase();
        StringBuilder sb = new StringBuilder();
        char n = '*';
        char m = '+';

        for (int i = 0; i < phrase.length(); i++) {
            char x = temp.charAt(i);
            if (x == ch && i % 2 == 0)
                { sb.append(n);}

            else if (x == ch && i % 2 != 0)
               {sb.append(m);}

            else sb.append(phrase.charAt(i));
        }

        return sb.toString();
    }
}
