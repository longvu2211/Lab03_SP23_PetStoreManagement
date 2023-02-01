package data_cabinet;

import data.Pet;
import interfaces.PetDao;
import ui.Menu;
import util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PetDaoImpl implements PetDao {
    private Scanner sc = new Scanner(System.in);
    List<Pet> pets = new ArrayList<>();
    private String fileName = "Pets.txt";

    public PetDaoImpl() {
        pets = getPetsFromFile(fileName);
    }

    @Override
    public void addAPet() {
        //Declaration
        String id, description, date;
        String category = null;
        String response;
        double unitPrice;
        int pos, choice;
        List<String> tmp = new ArrayList<>();

        do {
            do {
                id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                        "The format of id is Pxxx (X stands for a digit)", "^P\\d{3}$");
                pos = searchAPetByID(id);
                if (pos >= 0)
                    System.out.println("This id has already existed! Please enter the another id");
            } while (pos != -1);

            do {
                description = Tools.getString("Enter the description: ", "This field is required!");
            } while (description.length() < 3 || description.length() > 50);
            //The format of the date is m/d/yy, or mm//dd/yyyy
            date = Tools.getStringFormat("Enter the date: ",
                    "This field is required!", "^(1[0-2]|0?[1-9])/(3[01]|[12][0-9]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$");

            do {
                unitPrice = Tools.getADouble("Enter the price: ", "This field is required!");
            } while (unitPrice <= 0);

            Menu subMenu = new Menu();
            System.out.println("Here is the list of pet's categories");
            subMenu.addNewOption("\t1. Dog");
            subMenu.addNewOption("\t2. Cat");
            subMenu.addNewOption("\t3. Parrot");
            //display the submenu and get user choice
            subMenu.printMenu();
            choice = subMenu.getUserChoice();
            if (choice == 1)
                category = "Dog";
            else if (choice == 2)
                category = "Cat";
            else
                category = "Parrot";

            pets.add(new Pet(id, description, date, unitPrice, category));
            System.out.println("A new pet is added successfully!");
            System.out.print("Continue to add a new pet[Y/N]: ");
            response = sc.nextLine().toUpperCase();
        } while (response.startsWith("Y"));
        //Save to file
        for (Pet x: pets) {
            tmp.add(x.getId() + "," + x.getDescription() + "," + x.getDate() + "," +
                    x.getUnitPrice() + "," + x.getCategory());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    @Override
    public void searchAPet() {
        String id;
        Pet x;
        int pos;

        id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                "The format of the id is Pxxx (X stands for a digit)", "^P\\d{3}$");
        pos = searchAPetByID(id);
        x = searchAPetObjByID(id);

        if (pos == -1)
            System.out.println("The pet does not exist!");
        else {
            System.out.println("Here is the pet that you want to search");
            x.showProfile();
        }
    }

    @Override
    public void updateAPet() {
        String id;
        int pos;
        Pet x;
        List<String> tmp = new ArrayList<>();

        id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                "The format of the id is Pxxx (X stands for a digit)", "^P\\d{3}$");
        pos = searchAPetByID(id);
        x = searchAPetObjByID(id);

        if (pos == -1)
            System.out.println("The pet does not exist!");
        else {
            System.out.println("Here is the pet's information before updated");
            x.showProfile();
            System.out.print("Continue to update the information[Y/N]: ");
            String response = sc.nextLine().toUpperCase();
            if (response.startsWith("Y")) {
                String newDescription = Tools.getString("Enter the description: ", "This field is required!");
                String newDate = Tools.getStringFormat("Enter the date: ",
                        "This field is required!", "^(1[0-2]|0?[1-9])/(3[01]|[12][0-9]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$");
                double newPrice = Tools.getADouble("Enter the price: ", "This field is required!");

                x.setDescription(newDescription);
                x.setDate(newDate);
                x.setUnitPrice(newPrice);
                System.out.println("The pet's information is updated successfully!");
            } else
                System.out.println("The pet's information is not updated!");
        }
        for (Pet t: pets) {
            tmp.add(t.getId() + "," + t.getDescription() + "," + t.getDate() + "," +
                    t.getUnitPrice() + "," + t.getCategory());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    @Override
    public void removeAPet() {
        String id;
        int pos;
        Pet x;
        List<String> tmp = new ArrayList<>();

        id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                "The format of the id is Pxxx (X stands for a digit)", "^P\\d{3}$");
        pos = searchAPetByID(id);
        x = searchAPetObjByID(id);

        if (pos == -1)
            System.out.println("The pet does not exist!");
        else {
            System.out.println("Here is the pet before removed");
            x.showProfile();
            System.out.print("Continue to remove[Y/N]: ");
            String response = sc.nextLine().toUpperCase();
            if (response.startsWith("Y")) {
                //TODO: check whether the pet has already existed in the order detail
                // if existed -> not deletion
                pets.remove(pos);
                System.out.println("The pet is removed successfully!");
            } else
                System.out.println("The pet is not removed!");
        }
        //Save to file
        for (Pet t: pets) {
            tmp.add(t.getId() + "," + t.getDescription() + "," + t.getDate() + "," + t.getUnitPrice() + t.getCategory());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    @Override
    public void displayPets() {
        if (pets.isEmpty())
            System.out.println("The list is empty!");
        for (Pet x: pets) {
            x.showProfile();
        }
    }

    private int searchAPetByID(String id) {
        if (pets.isEmpty())
            return -1;
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    private Pet searchAPetObjByID(String id) {
        if (pets.isEmpty())
            return null;
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equalsIgnoreCase(id))
                return pets.get(i);
        }
        return null;
    }

    private List<Pet> getPetsFromFile(String fName) {
        List<Pet> petList = new ArrayList<>();
        List<String> tmp = Tools.readLineFromFile(fName);

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, ",");
            String id = stk.nextToken();
            String description = stk.nextToken();
            String date = stk.nextToken();
            double unitPrice = Double.parseDouble(stk.nextToken());
            String category = stk.nextToken();

            petList.add(new Pet(id, description, date, unitPrice, category));
        }
        return petList;
    }


    public static void main(String[] args) {

        PetDaoImpl tc = new PetDaoImpl();
//        tc.addAPet();
        tc.displayPets();
//        tc.removeAPet();
    }

}
