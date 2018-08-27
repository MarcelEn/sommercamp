package kassenbon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Kassenbon {
    public static void main(String[] args){
        boolean userInputLoop = true;
        Scanner sc = new Scanner(System.in);

        ArrayList<Item> items = new ArrayList();

        while(userInputLoop){
            System.out.println("add another Item? (y/n)");
            if(sc.nextLine().equals("y")){
                items.add(new Item());
            }else{
                userInputLoop= false;
            }

        }

        items.stream().forEach(element -> System.out.println(
                        "\t" + element.name + //
                        "\t" + element.price + //
                        "\t" + element.amount + //
                        "\t" + (element.amount * element.price)
        ));

        double sum = items.stream().mapToDouble(element -> element.amount * element.price).sum();

        System.out.println("Total price: " + sum);

    }
}
