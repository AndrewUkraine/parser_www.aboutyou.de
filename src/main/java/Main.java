
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        System.out.println("Input keyword");
        Scanner sc = new Scanner(System.in);
        String brand = sc.nextLine();

        System.out.println("What category do you prefer? \n" +
                "    Kinder -  Cat.138113 Press 1 \n" +
                "    Frauen -  Cat.20201  Press 2 \n" +
                "    Männer -  Cat.20202  Press 3");
       int cat = sc.nextInt();
            if (cat==1){
                Parser.setA(138113);
            }
        if (cat==2){
            Parser.setA(20201);
        }
        if (cat==3){
            Parser.setA(20202);
        }
        else
            Parser.setA(138113);
        System.out.println("No one category is selected. Default category is Männer");


        Parser parser = new Parser();
        parser.setPattern(brand);
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

