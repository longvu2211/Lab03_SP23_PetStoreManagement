package ui;

import util.Tools;

import java.util.ArrayList;

public class Menu {
    private ArrayList<String> optionList = new ArrayList<>();

    public Menu() {}

    public void addNewOption(String newOption) {
        int pos = find(newOption);
        if (pos >= 0) {
            System.out.println("This function is already existed!");
        } else
            optionList.add(newOption);
    }

    // this method is used to check whether an option existed in the optionList
    public int find(String option) {
        if (optionList.isEmpty())
            return -1;
        for (int i = 0; i < optionList.size(); i++) {
            if (optionList.get(i).equalsIgnoreCase(option))
                return i;
        }
        return -1;
    }

    public void printMenu() {
        if (optionList.isEmpty()) {
            System.out.println("There is no item in the menu");
            return;
        }
        for (String x : optionList) {
            System.out.println(x);
        }
    }

    public int getUserChoice() {
        int maxOption = optionList.size();
        String inputMsg = "Choose [1.." + maxOption + "]: ";
        String errorMsg = "You are required to choose the option 1.." + maxOption;
        return Tools.getAnInteger(inputMsg, errorMsg, 1, maxOption);
    }
}
