public class Db {
    private static String name;
    private static String brand;
    private static String color;
    private static String price;
    private static String initialPrice;
    private static String description;
    private static String articleId;
    private static String shippingCosts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "Db{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", price='" + price + '\'' +
                ", initialPrice='" + initialPrice + '\'' +
                ", description='" + description + '\'' +
                ", articleId='" + articleId + '\'' +
                ", shippingCosts='" + shippingCosts + '\'' +
                '}';
    }

    public String getShippingCosts() {
        return shippingCosts;
    }

    public void setShippingCosts(String shippingCosts) {
        this.shippingCosts = shippingCosts;
    }
}
