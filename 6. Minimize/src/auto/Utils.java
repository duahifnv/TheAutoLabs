package auto;

import java.util.*;

/**
 * Вспомогательные утилиты
 */
public class Utils {
    // Вывод таблицы на экран
    public static void PrintTable(int n_columns, List<String> labels,
     List<List<String>> params, String title, List<Integer> fieldSizes) {
        System.out.println(title);
        if (labels.size() != n_columns || fieldSizes.size() != n_columns) {
            System.out.println("Количество столбцов не совпадает с количеством параметров");
            return;
        }
        String dashStroke = "";
        for (Integer size : fieldSizes) {
            dashStroke += "-".repeat(size);
            dashStroke += "+";
        }
        // Заголовки
        System.out.println(dashStroke);
        for (int i = 0; i < n_columns; i++) {
            String format = "%-" + fieldSizes.get(i % n_columns) + "s";
            System.out.printf(format, labels.get(i));
            System.out.print("|");
        }
        System.out.println();
        // Значения
        System.out.println(dashStroke);
        for (int i = 0; i < params.size(); i++) {
            for (int j = 0; j < n_columns; j++) {
                String format = "%-" + fieldSizes.get(j % n_columns) + "s";
                System.out.printf(format, params.get(i).get(j));
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println(dashStroke);
    }
    // Метод превращения ассоциативного двумерного списка в двумерную матрицу
    public static List<List<String>> MapToMatrix(Map<Vertex, List<Vertex>> map) {
        List<List<String>> params = new ArrayList<>();
        List<Vertex> labels = new ArrayList<>(map.keySet());

        int n_rows = map.size();
        for (int i = 0; i < n_rows; i++) {
            List<String> new_row = new ArrayList<>();
            Vertex label;
            label = labels.get(i);
            new_row.add(label.getIdx().toString());
            for (Vertex vertex : map.get(label)) {
                String name;
                if (vertex == null) name = "-";
                else name = vertex.getIdx().toString();
                new_row.add(name);
            }
            params.add(new_row);
        }
        return params;
    }
    // Метод построения таблицы и ее дальнейшего вывода
    public static void BuildTable(List<String> autoLabels, Map<Vertex, List<Vertex>> automata, String title) {
        List<List<String>> autoParams = Utils.MapToMatrix(automata);
        List<Integer> aFieldSizes = new ArrayList<>(Collections.nCopies(autoLabels.size(), 10));
        Utils.PrintTable(autoLabels.size(), autoLabels, autoParams,
                title, aFieldSizes);
    }
}
