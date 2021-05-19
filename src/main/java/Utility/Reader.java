package Utility;

import java.util.*;

public class Reader {

    private static Scanner reader = buildScanner();

    private final static String WARNING_THE_INPUT_IS_IN_THE_WRONG_FORMAT = "Warning! The input is in the wrong format";
    private final static String WARNING_THE_VALUE_MUST_BE_GREATER_OR_EQUAL_TO = "Warning! The value must be greater or equal to ";
    private final static String WARNING_THE_INPUT_IS_EMPTY = "Warning! The input is empty";
    private final static String WARNING_THE_VALUE_MUST_BE_LOWER_OR_EQUAL_TO = "Warning! The value must be lower or equal to ";
    private final static String WARNING_THE_ALLOW_CHARACTERS_ARE = "Warning! The allow characters are: ";

    private final static char YES = 'S';
    private final static char NO = 'N';


    private static Scanner buildScanner() {
        Scanner create = new Scanner(System.in);
        //create.useDelimiter(System.getProperty("line.separator"));
        create.useDelimiter(System.lineSeparator() + "|\n");
        return create;
    }

    public static String ReadString(String message) {
        System.out.print(message);
        return reader.next();
    }

    public static String readNotEmpityString(String message) {
        boolean finished = false;
        String read = null;
        do {
            read = ReadString(message);
            read = read.trim();
            if (read.length() > 0)
                finished = true;
            else
                System.out.println(WARNING_THE_INPUT_IS_EMPTY);
        } while (!finished);

        return read;
    }

    public static char leggiChar(String message) {
        boolean finished = false;
        char valRead = '\0';
        do {
            System.out.print(message);
            String lettura = reader.next();
            if (lettura.length() > 0) {
                valRead = lettura.charAt(0);
                finished = true;
            } else {
                System.out.println(WARNING_THE_INPUT_IS_EMPTY);
            }
        } while (!finished);
        return valRead;
    }

    public static char leggiUpperChar(String message, String allowed) {
        boolean finished = false;
        char valRead = '\0';
        do {
            valRead = leggiChar(message);
            valRead = Character.toUpperCase(valRead);
            if (allowed.indexOf(valRead) != -1)
                finished = true;
            else
                System.out.println(WARNING_THE_ALLOW_CHARACTERS_ARE + allowed);
        } while (!finished);
        return valRead;
    }


    public static int readNumber(String message) {
        boolean finished = false;
        int valRead = 0;
        do {
            System.out.print(message);
            try {
                valRead = reader.nextInt();
                finished = true;
            } catch (InputMismatchException e) {
                System.out.println(WARNING_THE_INPUT_IS_IN_THE_WRONG_FORMAT);
                String trash = reader.next();
            }
        } while (!finished);
        return valRead;
    }


    public static int leggiIntero(String message, int minimo, int massimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            valoreLetto = readNumber(message);
            if (valoreLetto >= minimo && valoreLetto <= massimo)
                finito = true;
            else if (valoreLetto < minimo)
                System.out.println(WARNING_THE_VALUE_MUST_BE_GREATER_OR_EQUAL_TO + minimo);
            else
                System.out.println(WARNING_THE_VALUE_MUST_BE_LOWER_OR_EQUAL_TO + massimo);
        } while (!finito);

        return valoreLetto;
    }


    public static double readDouble(String message) {
        boolean finito = false;
        double valoreLetto = 0;
        do {
            System.out.print(message);
            try {
                valoreLetto = reader.nextDouble();
                finito = true;
            } catch (InputMismatchException e) {
                System.out.println(WARNING_THE_INPUT_IS_IN_THE_WRONG_FORMAT);
                String trash = reader.next();
            }
        } while (!finito);
        return valoreLetto;
    }


    public static boolean yesOrNo(String message) {
        String myMessage = message + "(" + YES + "/" + NO + ")";
        char valoreLetto = leggiUpperChar(myMessage, String.valueOf(YES) + String.valueOf(NO));

        if (valoreLetto == YES)
            return true;
        else
            return false;
    }

}
