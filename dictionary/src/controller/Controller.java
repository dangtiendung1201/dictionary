package controller;

import management.DictionaryManagement;
public class Controller {
    static protected DictionaryManagement management;
    static {
        management = new DictionaryManagement();
        try {
            management.insertFromFile("dictionary/resourses/data/merged.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
