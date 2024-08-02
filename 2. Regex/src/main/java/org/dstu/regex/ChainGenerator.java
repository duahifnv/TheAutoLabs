package org.dstu.regex;

import java.util.ArrayList;
import java.util.List;

public class ChainGenerator {
    private static int groupIndex = 1;
    private static int chainIndex;
    private static int concatIndex;
    public static List<String> generateChains(AlphabetRegex regex, int chainSize) {
        List<String> chains = new ArrayList<>();
        List<Character> alphabet = regex.getAlphabet();
        StringBuilder chain;
        char concat;

        while (true) {
            // Собираем группу цепочек по числу символов в ней
            // Число цепочек в группе = размеру алфавита в степени счетчика групп
            for(int c = 0; c < Math.pow(alphabet.size(), groupIndex); c++) {
                chain = new StringBuilder();

                // Собираем цепочку
                for(int cPos = 0; cPos < groupIndex; cPos++) {
                    concatIndex = (int)( (c / (Math.pow( alphabet.size(), (groupIndex - cPos - 1) )) % alphabet.size()) );
                    concat = alphabet.get(concatIndex);
                    chain.append(concat);
                }

                chain.append(regex.getEndSymbol());
                chains.add(chain.toString());
                chainIndex++;

                // Выходим если добавили нужное число цепочек
                if (chainIndex > chainSize) return chains;
            }
            groupIndex++;
        }
    }
}
