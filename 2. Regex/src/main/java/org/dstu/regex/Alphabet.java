package org.dstu.regex;

import java.util.ArrayList;
import java.util.List;

public final class Alphabet implements Input {
    @Override
    public List<Character> getAlphabet() {
        List<Character> alph = new ArrayList<>();
        System.out.println("Введите ваш алфавит (0, чтобы закончить):");
        char user_input;
        int count = 1;
        while (true) {
            user_input = getChar(String.format("Введите %d-ый символ: ", count));
            if (user_input == '0') break;
            if (alph.contains(user_input)) {
                System.out.println("Данный символ уже имеется в алфавите!");
                continue;
            }
            alph.add(user_input);
            count++;
        }
        alph.sort(null);
        return alph;
    }
}
