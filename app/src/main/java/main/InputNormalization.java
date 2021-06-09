package main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.logging.Logger; 
import java.util.logging.Level; 

public class InputNormalization {

    static String string;
    static boolean bool;
    static int digital;

    private InputNormalization() {
        throw new IllegalStateException("Utility class");
    }

    public static void regularize(Method method, String type, String tip, String error)
           throws InvocationTargetException, IllegalAccessException {
        System.out.println(tip);
        Scanner input = new Scanner(System.in);
        String inputWord = input.nextLine();
        Logger logger = Logger.getLogger(InputNormalization.class.getName()); 

        switch (type) {
            case "int":
                int number = 0;
                try {
                    number = Integer.parseInt(inputWord);
                } catch (NumberFormatException exception) {
                    logger.log(Level.WARNING, "Wrong type!", exception);
                    System.out.println(error);
                    method.invoke(null);
                }
                digital = number;
                break;
            case "String":
                string = inputWord;
                break;
            case "boolean":
                try {
                    if (inputWord.equalsIgnoreCase("Y")) {
                        bool = true;
                    } else if (inputWord.equalsIgnoreCase("N")) {
                        bool = false;
                    } else {
                        throw new IOException();
                    }
                } catch (IOException exception) {
                    logger.log(Level.WARNING, "Wrong type!", exception);
                    System.out.println(error);
                    method.invoke(null);
                }
                break;
            default:
        }
    }

    public static int extractHour(String dateTime) {
        String times = dateTime.split(" ")[2];
        return Integer.parseInt(times.split(":")[0]);
    }

    public static int extractMin(String dateTime) {
        String times = dateTime.split(" ")[2];
        return Integer.parseInt(times.split(":")[1]);
    }

    public static String extractWeek(String dateTime) {
        return dateTime.split(" ")[1];
    }
}
