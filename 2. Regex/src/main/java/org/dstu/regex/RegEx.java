package org.dstu.regex;

import java.util.ArrayList;
import java.util.List;

public class RegEx{
    private String re;
    private String special_alph="[|]?*+.!";
    private String endSymbol;

    private ArrayList<String> chains = new ArrayList<>();
    private ArrayList<Character> re_alph = new ArrayList<Character>();
    private List<Character> user_alph = new ArrayList<Character>();

    private int chain_count;

    public RegEx(List<Character> user_alph, String re, int chain_count) {
        this.user_alph = user_alph;

        this.re = re;
        this.chain_count = chain_count;

        Formate();
        SetReAlph();
        MatchAlph();

    }

    private void SetReAlph() {
        for (char ch : this.re.toCharArray()) {
            if (special_alph.indexOf(ch) == -1 && !re_alph.contains(ch)) {
                re_alph.add(ch);
            }
        }
    }

    private void MatchAlph() {
        if (user_alph.size() > re_alph.size()) {
            throw new Error("Алфавит " + this.user_alph + " содержит символы не из регулярного выражения!");
        }
        if (user_alph.size() < re_alph.size()) {
            throw new Error("В алфавите " + this.user_alph + " не хватает символов!");
        }
        System.out.println(re_alph);
        for (char ch : this.user_alph) {
            if (re_alph.indexOf(ch) == -1) {
                throw new Error("Алфавит " + this.user_alph + " содержит символы не из регулярного выражения!");
            }
        }
    }

    public void Formate() {
        if (this.re.charAt(0) == '^') {
            this.re = this.re.substring(1);
        }
        if (this.re.charAt(this.re.length() - 1) == '$') {
            this.re = this.re.substring(0, this.re.length() - 1);
        }
        int prevIdx = re.indexOf('+');
        this.endSymbol = re.substring(prevIdx + 1);
    }

    public void Generate() {
        String chain = "";
        char concat;

        int groupIdx = 1;
        int chainIdx = 0;
        int concatIdx = 0;

        while (true) {
            // Собираем группу цепочек по числу символов в ней
            // Число цепочек в группе = размеру алфавита в степени счетчика групп
            for(int c = 0; c < Math.pow(user_alph.size(), groupIdx); c++) {
                chain = "";

                // Собираем цепочку
                for(int cPos = 0; cPos < groupIdx; cPos++) {
                    concatIdx = (int)( (c / (Math.pow( user_alph.size(), (groupIdx - cPos - 1) )) % user_alph.size()) );
                    concat = user_alph.get(concatIdx);
                    chain += concat;
                }

                chain += this.endSymbol;
                this.chains.add(chain);
                chainIdx++;

                // Выходим если добавили нужное число цепочек
                if (chainIdx > chain_count) return;
            }
            groupIdx++;
        }
    }
    public void PrintChains() {
        System.out.printf("%d цепочек по регулярному выражению %s:%n", this.chain_count, this.re);
        System.out.println(this.chains);
    }
}
