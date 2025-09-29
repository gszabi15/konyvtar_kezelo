package org.gszabi15.view;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class View {
    static {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        } catch (Exception ignored) {

        }
    }
    static Scanner scanner = new Scanner(System.in);
    public static String prefix = "> ";

    public View() { }

    public static void print(String out) {
        System.out.println(out);
    }

    public static void waitChar() {
        java.io.Console console = System.console();
        if (console != null) {
            console.readPassword("");
        } else {
            scanner.nextLine();
        }
    }

    public static String getInput(String pref) {
        System.out.print(pref);
        return scanner.nextLine();
    }

    public static String getInput() {
        return getInput(prefix);
    }

    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name").toLowerCase();

            if (operatingSystem.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            //System.out.println("Failed to clear console: " + e.getMessage());
        }
    }
}