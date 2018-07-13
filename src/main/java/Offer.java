import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlSeeAlso({Offers.class})
@XmlType(propOrder = {"name", "brand", "color", "price", "initialPrice", "description", "articleId", "shippingCosts"})
public class Offer {
    private String name;
    private String brand;
    private String color;
    private String price;
    private String initialPrice;
    private String description;
    private String articleId;
    private String shippingCosts;

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Offer offer = (Offer) object;

        if (name != null ? !name.equals(offer.name) : offer.name != null) return false;
        if (brand != null ? !brand.equals(offer.brand) : offer.brand != null) return false;
        if (color != null ? !color.equals(offer.color) : offer.color != null) return false;
        if (price != null ? !price.equals(offer.price) : offer.price != null) return false;
        if (initialPrice != null ? !initialPrice.equals(offer.initialPrice) : offer.initialPrice != null) return false;
        if (description != null ? !description.equals(offer.description) : offer.description != null) return false;
        if (articleId != null ? !articleId.equals(offer.articleId) : offer.articleId != null) return false;
        if (shippingCosts != null ? !shippingCosts.equals(offer.shippingCosts) : offer.shippingCosts != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (initialPrice != null ? initialPrice.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (shippingCosts != null ? shippingCosts.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    @XmlElement
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    @XmlElement
    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(String price) {
        this.price = price;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    @XmlElement
    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleId() {
        return articleId;
    }

    @XmlElement
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getShippingCosts() {
        return shippingCosts;
    }

    @XmlElement
    public void setShippingCosts(String shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    @Override
    public String toString() {
        return "Offer{" +
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
}
