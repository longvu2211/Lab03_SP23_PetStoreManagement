package data;

public class Order {
    //Order Header
    private String ordHId;
    private String date;
    private String cusName;
    //Order Detail
    private String ordDId;
    private String petId;
    private int quantity;
    private double total;

//    static String formatDate = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    public Order() {}

    public Order(String ordHId, String date, String cusName, String ordDId, String petId, int quantity, double total) {
        this.ordHId = ordHId;
        this.date = date;
        this.cusName = cusName;
        this.ordDId = ordDId;
        this.petId = petId;
        this.quantity = quantity;
        this.total = total;
    }

    public String getOrdHId() {
        return ordHId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getOrdDId() {
        return ordDId;
    }

    public String getPetId() {
        return petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }
    public void showProfile() {
        System.out.printf("|%-6s|%-15s|%-30s|%-6s|%-6s|%-4d|%-7.1f|%n",
                ordHId, date, cusName, ordDId, petId, quantity, total);
    }

}
