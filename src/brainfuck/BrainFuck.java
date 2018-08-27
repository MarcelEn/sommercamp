package brainfuck;


import java.util.ArrayList;
import java.util.Scanner;

public class BrainFuck {
    private char[] code;
    private ArrayList<Character> data = new ArrayList();
    private int pointer = 0;
    private int codeIterator;
    private Scanner scanner = new Scanner(System.in);

    public BrainFuck(String code){
        this.code = code.toCharArray();
        data.add((char) 0);
        processCode();
    }

    private void processCode(){
        int value;
        for(codeIterator = 0; codeIterator < code.length; codeIterator++){
            switch (code[codeIterator]){
                case '>':
                    pointer++;
                    arrangeArrayLengthIfNeeded();
                    break;
                case '<':
                    pointer--;
                    break;
                case '+':
                    value = data.get(pointer);
                    value++;
                    data.set(pointer, (char) value);
                    break;
                case '-':
                    value = data.get(pointer);
                    value--;
                    data.set(pointer, (char) value);
                    break;
                case '.':
                    System.out.print(data.get(pointer));
                    break;
                case ',':
                    data.set(pointer, scanner.nextLine().toCharArray()[0]);
                    break;
                case '[':
                    if(data.get(pointer) == 0)
                        jumpForwardToClosingBracket();
                    break;
                case ']':
                    if(data.get(pointer) != 0)
                        jumpBackToOpeningBracket();
            }
        }
    }

    private void jumpForwardToClosingBracket() {
        int closingBracketCount = 1;
        Character value;

        while(closingBracketCount!=0){
            codeIterator++;
            value = code[codeIterator];

            if(value.equals('['))
                closingBracketCount++;

            if(value.equals(']'))
                closingBracketCount--;


        }
    }

    private void jumpBackToOpeningBracket() {
        int openingBracketCount = 1;
        Character value;
        while(openingBracketCount!=0){
            codeIterator--;
            value = code[codeIterator];

            if(value.equals('['))
                openingBracketCount--;

            if(value.equals(']'))
                openingBracketCount++;

        }
    }

    private void arrangeArrayLengthIfNeeded() {
        while(pointer >= data.size()) {
            data.add((char) 0);
        }
    }
}
