
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

        int httpRequests = 0;
        int numberPage = 1;
        long startTime = System.nanoTime();
        String brand = sc.nextLine();


        String url = "https://www.aboutyou.de/about/brand/" + brand + "?category=20201&sort=topseller&page=1";

        for (int i = 1; i <= 1; i++) {
            String url1 = url + i;
            System.out.println("new page**************************************************************************" + " Page Namber " + numberPage++);

            Document doc = null;
            try {
                doc = Jsoup.connect(url1).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements elements = null;
            if (doc != null) {
                elements = doc.select("html body main#app section.styles__layout--1QK8W.styles__stretchLayout--3omnx div div.styles__container--2cj5w div.row div.col-sm-9.col-md-10 div div div.styles__container--1bqmB div.row div.styles__container--1bqmB div.styles__tile--2s8XN.col-sm-6.col-md-4.col-lg-4 div a");
            }
            if (elements != null) {
                for (Element element : elements) {
                    String url3 = "https://www.aboutyou.de" + element.attr("href");

                    Document doc3 = Jsoup.connect(url3).get();


                    Elements elements1 = doc3.select(".productPrices"); //price
                    Elements elements2 = doc3.select(".priceStyles__strike--PSBGK"); //initialPrice
                    Elements elements3 = doc3.select("h1"); //brand
                    Elements elements4 = doc3.select(".styles__title--UFKYd"); //color
                    Elements elements5 = doc3.select(".styles__title--3Jos_"); // name
                    Elements elements6 = doc3.select(".styles__articleNumber--1UszN"); //articul
                    Elements elements7 = doc3.select(".styles__textElement--3QlT_"); //description
                    Elements elements8 = doc3.select(".styles__label--1cfc7"); //shippingCosts

                    httpRequests++;

                    List<Offer> offers = new ArrayList<>();

                    Offer offer = new Offer();

                    offer.setName(cleanHTML(elements5.text()));
                    offer.setBrand(cleanHTML(brandCleaner(elements3.text())));
                    offer.setColor(cleanHTML(elements4.text()));
                    offer.setPrice(cleanHTML(elements1.text()));
                    offer.setInitialPrice(cleanHTML(elements2.text()));
                    offer.setDescription(cleanHTML(elements7.text()));
                    offer.setArticleId(cleanHTML(elements6.text()));
                    offer.setShippingCosts(cleanHTML(elements8.text()));


                    offers.add(offer);
                    offers.forEach(System.out::println);



                    if (httpRequests % 5 == 0) {
                        System.out.println("Sleep for 5 sec");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }




            long runTime = (System.nanoTime() - startTime) / 10000000;
            System.out.println("Run-time = " + runTime / 100 + " sec");
            System.out.println("Amount of triggered HTTP request " + httpRequests);
            System.out.println("Amount of page " + numberPage);
            //System.out.println ("Amount of extracted products: " + offers.getList().size() + ".");
            System.out.println ("Memory Footprint: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " kilobytes.");
        }

       /* XMLView xmlView = new XMLView();
        xmlView.update(offers);*/


    }


    private static String cleanHTML(String html) {
        return html
                .replaceAll("[<](/)?div[^>]*[>]", "")
                .replaceAll(" class?=\"[^>]*[\"]", "")
                .replaceAll(" data-reactid?=\"[^>]*[\"]", "")
                .replaceAll("<!--.*?-->", "")
                .replaceAll(" +", " ")
                .replaceAll("\n ", "\n")
                .replaceAll("[ab]", "")
                .replaceAll("[spn]", "")
                .replaceAll("\n+", "");
    }

    private static String brandCleaner(String a) {
        return a.substring(0, a.indexOf("|"));
    }

    private static String discountCleaner(String a) {
        return a
                .replaceAll("[i]", "");
    }
}

