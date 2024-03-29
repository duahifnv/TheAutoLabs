import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Input {
    private String exitWord;
    protected Scanner scan = new Scanner(System.in);
    public Input(String exitWord) {
        this.exitWord = exitWord;
    }
    public int Size(String comm, int min, int max) {
        int input;
        while(true) {
            System.out.print(comm);
            try {
                input = scan.nextInt();
            } catch (Exception NumberFormatException) {
                System.out.println("Введенный символ не является числом");
                scan.nextLine();
                continue;
            }
            if (input < min || input > max) {
                System.out.println("Введенное число вне диапазона");
                continue;
            }
            break;
        }
        return input;
    }
    public Set<Integer> Set(String main_comm, String comm, int br, int min, int max) {
        System.out.print(main_comm);
        System.out.printf(" (%d, чтобы закончить)%n", br);
        Set<Integer> set = new HashSet<>();
        while (true) {
            int num = Size(comm + ": ", -1, max);
            if(num == br) {
                if (set.size() < min) {
                    System.out.printf("Введите хотя бы %d чисел%n", min);
                    continue;
                }
                break;
            }
            set.add(num);
        }
        return set;
    }
    public String Stroke(String comm, List<Character> alph) {
        String stroke = new String();
        while(true) {
            System.out.print(comm + ": ");
            stroke = scan.nextLine();
            if (stroke.equals(exitWord)) break;
            Boolean isValid = true;
            for (Character ch : stroke.toCharArray()) {
                if (!alph.contains(ch)) {
                    System.out.println("В введенном слове есть символы не из алфавита");
                    isValid = false;
                    break;
                }
            }
            if (isValid) break;
        }
        return stroke;
    }
}
