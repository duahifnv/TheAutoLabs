import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {
    public static void PrintTable(int n_columns, List<String> labels,
     List<List<String>> params, List<Integer> fieldSizes) {
        if (labels.size() != n_columns || params.get(0).size() != n_columns || fieldSizes.size() != n_columns) {
            System.out.println("Количество столбцов не совпадает с количеством параметров");
            return;
        }
        for (int i = 0; i < n_columns; i++) {
            String format = "%-" + fieldSizes.get(i % n_columns) + "s";
            System.out.printf(format, labels.get(i));
            System.out.print(" | ");
        }
        System.out.println();
        for (int i = 0; i < params.size(); i++) {
            for (int j = 0; j < n_columns; j++) {
                String format = "%-" + fieldSizes.get(j % n_columns) + "s";
                System.out.printf(format, params.get(i).get(j));
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    public static List<List<String>> MapToMatrix(Map<Integer, List<List<Vertex>>> map) {
        int n_rows = map.size();
        List<List<String>> params = new ArrayList<>();
        for (int i = 0; i < n_rows; i++) {
            List<List<Vertex>> map_row = map.get(i);
            List<String> new_row = new ArrayList<>();
            new_row.add("q" + String.valueOf(i));
            for (int j = 0; j < map_row.size(); j++) {
                String cell;
                if (map_row.get(j) != null) {
                    List<Vertex> values = map_row.get(j);
                    List<String> names = new ArrayList<>();
                    for (Vertex vertex : values) {
                        names.add("q" + String.valueOf(vertex.getIdx()));
                    }
                    cell = String.join(",", names);
                }
                else cell = "-";
                new_row.add(cell);
            }
            params.add(new_row);
        }
        return params;
    }
}
