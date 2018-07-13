import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;



@XmlRootElement
public class Offers {
    @XmlElement(name = "offer")
    private List<Offer> offerList;

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    @Override
    public String toString() {
        return "Offers{" +
                "offerList=" + offerList +
                '}';
    }
}
