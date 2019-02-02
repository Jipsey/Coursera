package Arrays;

import static Arrays.CaesarCipher.mergeWords;

public class CaesarCiperTwoKeys {

    private CaesarCipher cc1;
    private CaesarCipher cc2;
    private CaesarBreaker cb;

    public CaesarCiperTwoKeys(int key1,int key2) {
        cc1 = new CaesarCipher(key1);
        cc2 = new CaesarCipher(key2);
        cb = new CaesarBreaker(key1,key2);
    }

    public String encryptTwoKeys(String input) {

        String stringKey1 = cc1.encrypt(cb.halfString(input,0));
        String stringKey2 = cc2.encrypt(cb.halfString(input,1));

        return mergeWords(stringKey1, stringKey2);
    }

}
