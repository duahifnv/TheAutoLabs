import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class AutoLabs {
    private List<Character> alph = new ArrayList<Character>();
    public AutoLabs() {
        System.out.println("ТЕОРИЯ АВТОМАТОВ И ФОРМАЛЬНЫХ ЯЗЫКОВ\n");
    }
    private char CharInput(String commentary) {
        Scanner scan = new Scanner(System.in);
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

    public void SetAlph() {
        System.out.println("Введите ваш алфавит (0, чтобы закончить):");
        char user_input;
        int count = 1;
        while (true) {
            user_input = this.CharInput(String.format("Введите %d-ый символ: ", count));
            if(user_input == '0') break;
            if(this.alph.contains(user_input)) {
                System.out.println("Данный символ уже имеется в алфавите!");
                continue;
            }
            this.alph.add(user_input);
            count++;
        }
        this.alph.sort(null);
    }

    public void PrintAlph() {
        System.out.printf("Ваш алфавит: %s%n", this.alph.toString());
    }

    public List<Character> GetAlph() {
        return this.alph;
    }
}