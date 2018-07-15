import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Parser  {

  public  synchronized List<Offer> parser (String brand, int quantityPage){

      List<Offer> offers = new ArrayList<>();

      int[] categories = new int[]{138113, 20201, 20202};
      for (int category : categories) {
      final String URL = "https://www.aboutyou.de/suche?" + "term=" + brand.replaceAll(" ", "+") + "&category=" + category + "&page=";

      int httpRequests = 0;
      int numberPage = 1;
      long startTime = System.nanoTime();

        for (int i = 1; i <=quantityPage; i++) {
        String url1 = URL + i;
      

        Document doc = null;
        try {
            doc = Jsoup.connect(url1).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements;
            elements =  doc.body().getElementsByClass("styles__container--1bqmB").first().getElementsByAttribute("href");
            for (Element element : elements) {
                String url = element.attr("href");

                if (url.contains("/p/")) {
                    String url3 = "https://www.aboutyou.de" + url;

                    Document doc3 = null;
                    try {
                        doc3 = Jsoup.connect(url3)
                                .timeout(2000)  //Set the total request timeout duration.
                                .get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Elements elements1 = doc3.select(".productPrices"); //price
                    Elements elements2 = doc3.select(".priceStyles__strike--PSBGK"); //initialPrice
                    Elements elements3 = doc3.select("h1"); //brand
                    Elements elements4 = doc3.select(".styles__title--UFKYd"); //color
                    Elements elements5 = doc3.select(".styles__title--3Jos_"); // name
                    Elements elements6 = doc3.select(".styles__articleNumber--1UszN"); //articul
                    Elements elements7 = doc3.select(".styles__textElement--3QlT_"); //description
                    Elements elements8 = doc3.select(".styles__label--1cfc7"); //shippingCosts

                    httpRequests++;

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

                    /*if (httpRequests % 5 == 0) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }*/
                }
            }

        }

        long runTime = (System.nanoTime() - startTime) / 10000000;
        System.out.println("Run-time = " + runTime / 100 + " sec");
        System.out.println("Amount of triggered HTTP request " + httpRequests);
        System.out.println("Amount of page " + numberPage);
        System.out.println ("Memory Footprint: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " kilobytes.");
    }

return offers;
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
      if (a.length()!=0){
        return a.substring(0, a.indexOf("|"));}
        else return null;
    }
}
