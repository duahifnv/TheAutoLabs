import grammar.Grammar;
import json.JSONmanager;

public class Main {
    public static void main(String[] args) {
        String jsonPath = "D:\\DSTU\\автоматы\\TheAutoLabs\\Grammar7\\json\\grammar8.json";
        Grammar grammar = JSONmanager.parseObject(jsonPath, Grammar.class);
        System.out.println(grammar);
    }
}