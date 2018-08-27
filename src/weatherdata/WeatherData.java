package weatherdata;

import java.util.Arrays;

public class WeatherData {
    public static void main(String[] args){
        double [] temperature = {12, 14, 9, 12, 15, 16, 15, 11, 8, 13, 13, 15, 12};
        printAvg(temperature);
        printMaxMin(temperature);
        printBiggestDiv(temperature);
        printTable(temperature);
    }

    private static void printAvg(double[] temperature) {
        double result = Arrays.stream(temperature).sum();
        System.out.println("Average temperature: " + result/temperature.length );
    }

    private static void printMaxMin(double[] temperature) {
        double max= temperature[0];
        double min = temperature[0];
        for(int i = 0; i < temperature.length; i++){
            if(max < temperature[i])
                max = temperature[i];
            if(min > temperature[i])
                min = temperature[i];
        }

        System.out.println("Max: " + max + " Min: " + min);
    }

    private static void printBiggestDiv(double[] temperature) {
        double biggestDiv = 0;
        double currentDiv;
        for(int i = 1; i < temperature.length; i++){
            currentDiv = temperature[i] - temperature[i-1];
            currentDiv = Math.sqrt(currentDiv * currentDiv);
            if(currentDiv > biggestDiv)
                biggestDiv = currentDiv;
        }

        System.out.println("Biggest temperature difference: " + biggestDiv);
    }

    private static void printTable(double[] temperature) {
        for(int i = 0; i < temperature.length; i++){
            System.out.println(i + "|" + temperature[i]);
        }
    }
}
