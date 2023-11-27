package controller;

import management.DictionaryManagement;

public class Controller {
    static protected DictionaryManagement management;
    static {
        management = new DictionaryManagement();
        try {
            management.insertFromFile("dictionary/resourses/data/merged.txt");
            System.out.println("Add successfully " + management.allDictionaryWord().size()
            + " words from merged.txt!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Using back up!");
            management = new DictionaryManagement();
            management.insertFromFile("dictionary/resourses/data/merged-backup.txt");
        }

        try {
            management.insertMyWordListFromFile("dictionary/resourses/data/myList.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
