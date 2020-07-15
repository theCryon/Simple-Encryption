package encryptdecrypt;



public class Main {
    public static String cypher(String text){
        char[] coded = text.toCharArray();
        String reversed = "zyxwvutsrqponmlkjihgfedcba";
        char[] revT = reversed.toCharArray();
        int tmp;
        for (int i = 0; i < coded.length; i++){
            if (coded[i] > 96 && coded[i] < 123){
                tmp = coded[i]-97;
                coded[i] = revT[tmp];
            }
        }
        String codedS = "";
        for (char c : coded) codedS += c;
        return codedS;
    }

    public static void main(String[] args) {
        String text = "we found a treasure!";
        String coded = cypher(text);
        System.out.println(coded);
    }
}
