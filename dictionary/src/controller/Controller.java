package controller;

import management.DictionaryManagement;
import trie.exception.AddWordException;

public class Controller {
    static protected DictionaryManagement management;
    static {
        management = new DictionaryManagement();
        try {
            management.insertFromFile("dictionary/resourses/data/merged.txt");
        } catch (AddWordException e) {
            System.out.println(e.getMessage());
            management.insertFromFile("dictionary/resourses/data/merged-backup.txt");
        }

        try {
            management.insertMyWordListFromFile("dictionary/resourses/data/myList.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
