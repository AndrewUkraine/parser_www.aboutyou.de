import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;


public class XMLView {
    private final String filePath = "./offers.xml";

    public void update(List<Offer> offers) {
        try {
            Offers offers1 = new Offers();
            offers1.setOfferList(offers);
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(Offers.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(offers1, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
