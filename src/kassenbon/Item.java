package kassenbon;

import java.util.Scanner;

public class Item {
    String name;
    int amount;
    double price;

    Scanner sc = new Scanner(System.in);

    public Item(){
        System.out.println("Item name:");
        this.name = sc.nextLine();
        System.out.println("Item price:");
        this.price= sc.nextDouble();
        System.out.println("Item amount:");
        this.amount = sc.nextInt();
    }
}
