package sommercamp.engler.simplechat;

import sommercamp.engler.simplechat.boundary.Display;
import sommercamp.engler.simplechat.boundary.InputConsole;

import java.util.Scanner;

public class SimpleChatMain {
    public static void main(String[] args){
        String userInput = "";
        Scanner sc = new Scanner(System.in);

        while(!(userInput.equals("1") || userInput.equals("2"))){
            System.out.println("Is this the Console or the Display? (1/2)");
            userInput = sc.nextLine();
        }

        if(userInput.equals("1"))
            new InputConsole();
        else
            new Display();

    }
}
