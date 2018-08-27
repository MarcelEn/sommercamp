package cowsay;

import java.util.Arrays;

public class Cowsay {
    public static void main(String[] args){
        int length = getLength(args);
        drawLine(length, "_");
        System.out.print("|");
        Arrays.stream(args).forEach(Cowsay::printWord);
        System.out.print("|\n");
        drawLine(length, "-");
        printAnimal();
    }

    private static void drawLine(int length, String type){
        System.out.print(" ");
        for(int i = 0; i < length; i++)
            System.out.print(type);
        System.out.print("\n");
    }

    private static void printWord(String word) {
        System.out.print(word + " ");
    }


    private static void printAnimal(){
        System.out.println(
                        "\n        \\   ^__^\n" +
                        "         \\  (oo)\\_______\n" +
                        "            (__)\\       )\\/\\\n" +
                        "                ||----w |\n" +
                        "                ||     ||"
        );
    }
    private static int getLength(String[] words){
        return Arrays.stream(words).mapToInt(String::length).sum() + words.length;
    }
}
