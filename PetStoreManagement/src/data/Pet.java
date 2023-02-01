package data;

public class Pet {
    //Declaration
    private String id;
    private String description;
    private String date;
    private double unitPrice;
    private String category;

    public Pet() {}

    public Pet(String id, String description, String date, double unitPrice, String category) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

//    public void setCategory(String category) {
//        this.category = category;
//    }

    @Override
    public String toString() {
        return String.format("|%-6s|%-30s|%-15s|%-6.1f|%-8s|", id, description, date, unitPrice, category);
    }

    public void showProfile() {
        System.out.printf("|%-6s|%-30s|%-15s|%-6.1f|%-8s|%n", id, description, date, unitPrice, category);
    }

}
