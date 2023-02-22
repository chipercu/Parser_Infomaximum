package org.fuzzy;

import java.math.BigInteger;
import java.util.Scanner;

import static org.fuzzy.Util.printMenu;

public class Main {
    private static final String EXIT = "exit";

    private static final String DEFAULT_PATH_CSV = "C:\\Users\\FuzzY\\Desktop\\TZ_Java\\out.csv";

    public static void main(String[] args)  {

        Scanner scanner = new Scanner(System.in, "utf-8");
        do {
            printMenu();
            final String nextLine = scanner.nextLine();
            if (EXIT.equalsIgnoreCase(nextLine)){
                System.out.println(EXIT);
                return;
            }
            final String replace = nextLine.trim();
            new Parser(replace).parse();
        } while (true);

    }
}