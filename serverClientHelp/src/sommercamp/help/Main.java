package sommercamp.help;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        new Main();
    }

    private Main(){
        if(userWantAServer())
            new ServerProcess();
        else
            new ClientProcess();
    }

    private boolean userWantAServer() {
        String userInput = "";

        while(!(userInput.equals("1") || userInput.equals("2"))){
            System.out.println("Server (1) or Client (2)");
            userInput = sc.nextLine();
        }

        return userInput.equals("1");
    }
}
