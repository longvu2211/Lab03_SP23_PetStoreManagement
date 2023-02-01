package data_cabinet;

import data.Order;
import data.Pet;
import interfaces.OrderDao;
import ui.Menu;
import util.Tools;

import java.util.*;

public class OrderDaoImpl implements OrderDao {
    private final Scanner sc = new Scanner(System.in);
    List<Order> orders = new ArrayList<>();
    List<Pet> pets = getPetsFromFile();
    private String fileName = "Orders.txt";

    public OrderDaoImpl() {
        orders = getOrdersFromFile(fileName);
    }

    @Override
    public void addAnOrder() {
        //Declaration for Order Header
        String ordHId, date, cusName;
        //Declaration for Order Detail
        String ordDId, petId;
        int quantity;
        int pos_01, pos_02;
        int choice;
        double price;
        List<String> tmp = new ArrayList<>();
        //Input validation
        do {
            ordHId = Tools.getStringFormat("Enter the header's id[XXXX]: ",
                    "The format of the id is xxxx (X stands for a digit)", "\\d{4}$");
            pos_01 = searchAnOrderByOrderHeaderId(ordHId);
            if (pos_01 >= 0)
                System.out.println("This id has already existed! Please input the another id");
        } while (pos_01 != -1);
        date = Tools.getStringFormat("Enter the date: ",
                "This field is required!", "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$");
        cusName = Tools.getString("Enter the name: ", "This field is required!");
        //TODO: Provide options for user to continue to add more pets
        //[Y/N]:
        do {
            ordDId = Tools.getStringFormat("Enter the detail's id[DXXX]: ",
                    "The format of the id is Dxxx (X stands for a digit)", "^D\\d{3}$");
            pos_02 = searchAnOrderByOrderDetailId(ordDId);
            if (pos_02 >= 0)
                System.out.println("This id has already existed! Please input the another id");
        } while (pos_02 != -1);
        //Choosing a pet's id using submenu
        System.out.println("Here is the list of pets");
        Menu subMenu = new Menu();
        for (int i = 0; i < pets.size(); i++) {
            subMenu.addNewOption(i + 1 + "." + pets.get(i).toString());
        }
        subMenu.printMenu();
        System.out.println("Enter the number to choose a pet's id");
        choice = subMenu.getUserChoice();
        petId = pets.get(choice - 1).getId();
        do {
            quantity = Tools.getAnInteger("Enter the quantity: ", "This field is required!");
        } while (quantity <= 0);
        price = pets.get(choice - 1).getUnitPrice();
        orders.add(new Order(ordHId, date, cusName, ordDId, petId, quantity, (quantity * price)));
        System.out.println("A new order is added successfully!");

        //Save to file
        for (Order x: orders) {
            tmp.add(x.getOrdHId() + "," + x.getDate() + "," + x.getCusName() + ","
                + x.getOrdDId() + "," + x.getPetId() + "," + x.getQuantity() + "," + x.getTotal());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    @Override
    public void listOrdersByDate() {
        String startDate, endDate;
        startDate = Tools.getStringFormat("Enter the start date: ",
                "This field is required!", "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$");
        endDate = Tools.getStringFormat("Enter the end date: ",
                "This field is required!", "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$");
        // Split startDate
        String[] splitStartDate = startDate.split("/");
        int monthStart = Integer.parseInt(splitStartDate[0]);
        int dateStart = Integer.parseInt(splitStartDate[1]);
        int yearStart = Integer.parseInt(splitStartDate[2]);
        // Split endDate
        String[] splitEndDate = endDate.split("/");
        int monthEnd = Integer.parseInt(splitEndDate[0]);
        int dateEnd = Integer.parseInt(splitEndDate[1]);
        int yearEnd = Integer.parseInt(splitEndDate[2]);

        for (Order x: orders) {
            String[] dateSplitOrd = x.getDate().split("/");
            int month = Integer.parseInt(dateSplitOrd[0]);
            int date = Integer.parseInt(dateSplitOrd[1]);
            int year = Integer.parseInt(dateSplitOrd[2]);

            if(
                (year > yearStart && year < yearEnd) ||
                ((year == yearStart && year == yearEnd) && (month > monthStart && month < monthEnd)) ||
                ((year == yearStart && year == yearEnd) && (month == monthStart && month == monthEnd) && (date >= dateStart && date <= dateEnd))
            )
                x.showProfile();
        }
    }

    @Override
    public void sortOrdersByChosenField() {
        Menu subMenu = new Menu();
        subMenu.addNewOption("1. Sort by order's id");
        subMenu.addNewOption("2. Sort by order's date");
        subMenu.addNewOption("3. Sort by customer's name");
        subMenu.addNewOption("4. Sort by order's total");
        subMenu.addNewOption("5. Exit");
        int choice = 0;
        do {
            subMenu.printMenu();
            choice = subMenu.getUserChoice();
            switch (choice) {
                case 1:
                    System.out.print("Ascending order or Descending order[A/D]: ");
                    String response01 = sc.nextLine().toUpperCase();
                    sortOrdersById();
                    if (response01.startsWith("A")) {
                        for (Order x: orders) {
                            x.showProfile();
                        }
                    } else {
                        for (int i = orders.size() - 1; i >= 0; i--) {
                            orders.get(i).showProfile();
                        }
                    }
                    break;
                case 2:
                    System.out.print("Ascending order or Descending order[A/D]: ");
                    String response02 = sc.nextLine().toUpperCase();
                    sortOrdersByDate();
                    if (response02.startsWith("A")) {
                        for (Order x: orders) {
                            x.showProfile();
                        }
                    } else {
                        for (int i = orders.size() - 1; i >= 0 ; i--) {
                            orders.get(i).showProfile();
                        }
                    }
                    break;
                case 3:
                    System.out.print("Ascending order or Descending order[A/D]: ");
                    String response03 = sc.nextLine().toUpperCase();
                    sortOrdersByCustomerName();
                    if (response03.startsWith("A")) {
                        for (Order x: orders) {
                            x.showProfile();
                        }
                    } else {
                        for (int i = orders.size() - 1; i >= 0 ; i--) {
                            orders.get(i).showProfile();
                        }
                    }
                    break;
                case 4:
                    System.out.print("Ascending order or Descending order[A/D]: ");
                    String response04 = sc.nextLine().toUpperCase();
                    sortOrdersByTotal();
                    if (response04.startsWith("A")) {
                        for (Order x: orders) {
                            x.showProfile();
                        }
                    } else {
                        for (int i = orders.size() - 1; i >= 0 ; i--) {
                            orders.get(i).showProfile();
                        }
                    }
                    break;
                case 5:
                    break;
            }
        } while (choice != 5);
    }
    // Create a temporary list -> Ideal
    private void sortOrdersById() {
        Comparator ordBalance = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getOrdHId().compareTo(o2.getOrdHId());
            }
        };
        Collections.sort(orders, ordBalance);
    }

    private void sortOrdersByDate() {
        Comparator dateBalance = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getDate().compareToIgnoreCase(o2.getDate());
            }
        };
        Collections.sort(orders, dateBalance);
    }

    private void sortOrdersByCustomerName() {
        Comparator nameBalance = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getCusName().compareToIgnoreCase(o2.getCusName());
            }
        };
    }

    private void sortOrdersByTotal() {
        Comparator priceBalance = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                if (o1.getTotal() > o2.getTotal())
                    return 1;
                else if (o1.getTotal() < o2.getTotal())
                    return -1;
                else
                    return 0;
            }
        };
        Collections.sort(orders, priceBalance);
    }
    private int searchAnOrderByOrderHeaderId(String id) {
        if (orders.isEmpty())
            return -1;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrdHId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    private int searchAnOrderByOrderDetailId(String id) {
        if (orders.isEmpty())
            return -1;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrdDId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    private List<Order> getOrdersFromFile(String fName) {
        List<Order> ordList = new ArrayList<>();
        List<String> tmp = Tools.readLineFromFile(fName);

        for (String x: tmp) {
            StringTokenizer stk = new StringTokenizer(x, ",");
            String ordHId = stk.nextToken();
            String date = stk.nextToken();
            String cusName = stk.nextToken();
            String ordDId = stk.nextToken();
            String petId = stk.nextToken();
            int quantity = Integer.parseInt(stk.nextToken());
            double total = Double.parseDouble(stk.nextToken());

            ordList.add(new Order(ordHId, date, cusName, ordDId, petId, quantity, total));
        }
        return ordList;
    }

    private List<Pet> getPetsFromFile() {
        String fName = "Pets.txt";
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
        OrderDaoImpl tc = new OrderDaoImpl();
//        tc.addAnOrder();
//        tc.listOrdersByDate();
//        tc.sortOrdersByChosenField();
    }
}
