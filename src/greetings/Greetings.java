package greetings;

import java.util.Scanner;

public class Greetings {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Wie ist ihr Name?");
        String name = sc.nextLine();
        System.out.println("(m/w)");
        String gender = sc.nextLine();

        switch (gender){
            case "m":
                System.out.println("Hallo Herr " + name);
                break;
            case "w":
                System.out.println("Hallo Frau " + name);
                break;
            default:
                System.out.println("Hallo " + name);
        }
    }
}
