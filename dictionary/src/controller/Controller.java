package controller;

import management.DictionaryManagement;
public class Controller {
    static protected DictionaryManagement management;
    static {
        management = new DictionaryManagement();
        management.insertFromFile("dictionary/resourses/data/WordList.txt");
    }
}
