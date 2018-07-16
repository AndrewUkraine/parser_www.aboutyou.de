
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        System.out.println("Input keyword");
        Scanner sc = new Scanner(System.in);
        String brand = sc.nextLine();
       // System.out.println("Input quantity of pages for scanning...");
       // int quantityPages = sc.nextInt();




        Parser parser = new Parser();
        parser.setPattern(brand);
        parser.getUrlSetFromSearchByPattern();


       parser.amounOfProducts();


        List<Offer> offers = parser.parser2(parser.getUrlSetFromSearchByPattern()) ;
        XMLView xmlView = new XMLView();
        xmlView.update(offers);
       offers.forEach(System.out::println);

        long runTime = (System.nanoTime() - parser.startTime) / 10000000;
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Run-time = " + runTime / 100 + " sec");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Amount of triggered HTTP request " + parser.httpRequests);
        System.out.println("---------------------------------------------------------------------------");
        System.out.println ("Memory Footprint: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " kilobytes.");
        System.out.println("---------------------------------------------------------------------------");

    }
}

