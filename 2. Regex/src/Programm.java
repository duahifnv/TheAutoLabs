import java.util.Scanner;

public class Programm {
    public static void main(String[] args) {
        AutoLabs lab = new AutoLabs();
        lab.SetAlph();
        lab.PrintAlph();

        String[] exps = {"^[a|b|c]+!$", "^[a|b|c]+cc$"};

        Scanner scan = new Scanner(System.in);
        System.out.println("Введите номер регулярного выражения:");
        System.out.printf("0. %s%n", exps[0]);
        System.out.printf("1. %s%n", exps[1]);

        int regexIdx = scan.nextInt();
        regexIdx = (regexIdx == 0) ? 0 : 1;

        System.out.println("Введите число выводимых цепочек:");
        int chain_count = scan.nextInt();
        if (chain_count < 1) {
            throw new Error("Число цепочек должно быть больше 0.");
        }

        RegEx regex = new RegEx(lab.GetAlph(), exps[regexIdx], chain_count);
        regex.Generate();
        regex.PrintChains();

        scan.close();
    }
}
