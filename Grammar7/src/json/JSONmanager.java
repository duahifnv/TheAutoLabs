package json;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JSONmanager {
    public static <T> T parseObject(String jsonPath, Class<T> clazz) {
        T json;
        try {
            json = JSONparser.getObject(new File(jsonPath), clazz);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new Error("Не удалось обработать JSON");
        }
        return json;
    }
    public static <T> ArrayList<T> parseList(String jsonPath, Class<T> clazz) {
        ArrayList<T> json;
        try {
            json = JSONparser.getList(new File(jsonPath), clazz);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new Error("Не удалось обработать JSON");
        }
        return json;
    }
    // В РАЗРАБОТКЕ
    public <T> ArrayList<T> parseMap(String jsonPath) {
        Map<String, ArrayList<T>> json;
        try {
            json = JSONparser.getMap(new File(jsonPath));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new Error("Не удалось обработать JSON");
        }
        List<String> objectLabels = List.copyOf(json.keySet());
        ArrayList<String> numObjectLabels = new ArrayList<>();
        for (int i = 0; i < objectLabels.size(); i++) {
            numObjectLabels.add(String.format("%d.%s", i+1, objectLabels.get(i)));
        }
        System.out.println("Найдены объекты: " + String.join(", ", numObjectLabels));
        System.out.print("Введите номер ключа: ");
        Scanner scan = new Scanner(System.in);
        int keyIndex = scan.nextInt() - 1;
        if (keyIndex > objectLabels.size() - 1) {
            throw new InputMismatchException("Указан неверный номер");
        }
        return json.get(objectLabels.get(keyIndex));
    }
}
