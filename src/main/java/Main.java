
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        System.out.println("Input name of brand...");
        Scanner sc = new Scanner(System.in);
        String brand = sc.nextLine();

        Parser parser = new Parser();
        parser.parser(brand);


        /*XMLView xmlView = new XMLView();
        xmlView.update(offers);*/

    }
}

