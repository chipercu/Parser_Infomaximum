package org.fuzzy;

import java.io.PrintStream;
import java.math.BigInteger;

public class Util {
    public static PrintStream printStream = new PrintStream(System.out);

    public static void printMenu() {
        printStream.print("Commands: \n" +
                "1. Enter path to file \n" +
                "2. exit - close the program\n" +
                "Enter command:");
    }

    public static void wrongFileExtension(){
        printStream.println("The selected file does not match the required format. \n The program reads only .json and .csv files");
    }

    public static void printFileNotFound(){
        printStream.println("File not found!");
    }


    public static void printGroupObjectInfo(String group, int ex_count, BigInteger group_weight){
        printStream.println("Group:" + group + ", duplicates = " + ex_count + ", Total weight of all objects in the group = " + group_weight);
    }

    public static void printMaxWeight(BigInteger weight){
        printStream.println("Maximum weight of an object in a file - " + weight);
    }

    public static void printMinWeight(BigInteger weight){
        printStream.println("Minimum weight of an object in a file - " + weight);
    }



}
