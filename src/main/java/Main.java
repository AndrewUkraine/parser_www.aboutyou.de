
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        System.out.println("Input keyword");
        Scanner sc = new Scanner(System.in);
        String brand = sc.nextLine();
        System.out.println("Input quantity of pages for scanning...");
        int quantityPages = sc.nextInt();




        Parser strategy = new Parser();
        List<Offer> offers = strategy.parser(brand, quantityPages);
        XMLView xmlView = new XMLView();
        xmlView.update(offers);
        offers.forEach(System.out::println);


    }
}

