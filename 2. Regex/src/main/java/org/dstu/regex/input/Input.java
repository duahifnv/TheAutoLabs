package org.dstu.regex.input;

import java.util.Scanner;
import java.util.List;

public interface Input {
    List<Character> getAlphabet();

    default char getChar(String commentary) {
        Scanner scan = new Scanner(System.in);
        String ch_input;
        while (true) {
            System.out.print(commentary);
            ch_input = scan.nextLine();
            if (ch_input.length() != 1) {
                System.out.println("Необходимо ввести один символ!");
                continue;
            }
            break;
        }
        return ch_input.charAt(0);
    }
}