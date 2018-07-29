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
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); /*устанавливает свойство FORMATTED_OUTPUT в TRUE. В результат будут добавлены переносы строки и пробелы, чтобы код был читабельным для человека, а не весь текст в одну строку.*/
            jaxbMarshaller.marshal(offers1, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
