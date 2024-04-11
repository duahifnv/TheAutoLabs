package auto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ввод алфавита
 */
public class Alphabet {
    Scanner scan = new Scanner(System.in);
    private final List<Character> alph = new ArrayList<>();
    // Ввод символа
    private char CharInput(String commentary) {
        String ch_input;
        while (true)
        {
            System.out.print(commentary);
            ch_input = scan.nextLine();
            if (ch_input.length() != 1)
            {
                System.out.println("Необходимо ввести один символ!");
                continue;
            }
            break;
        }
        return ch_input.charAt(0);
    }
    // Ввод алфавита
    public void SetAlph() {
        System.out.println("Введите ваш алфавит (-, чтобы закончить):");
        char user_input;
        for (int i = 1; true; i++) {
            user_input = this.CharInput(String.format("Введите %d-ый символ: ", i));
            if(user_input == '-') break;
            if(this.alph.contains(user_input)) {
                System.out.println("Данный символ уже имеется в алфавите");
                continue;
            }
            this.alph.add(user_input);
        }
        this.alph.sort(null);
    }
    // Вывод алфавита
    public void PrintAlph() {
        System.out.printf("Ваш алфавит: %s%n", this.alph);
    }
    public List<Character> GetAlph() {
        return this.alph;
    }
}