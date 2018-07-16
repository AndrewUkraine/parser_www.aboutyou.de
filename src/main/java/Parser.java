import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Parser {

    volatile static int httpRequests = 0;
    long startTime = System.nanoTime();


    public synchronized List<Offer> parser(String brand, int quantityPage) {
        List<Offer> offers = new ArrayList<>();
        int[] categories = new int[]{20201,138113, 20202};
        for (int category : categories) {
            final String URL = "https://www.aboutyou.de/suche?" + "term=" + brand.replaceAll(" ", "+") + "&category=" + category + "&page=";

            //find of total of goods
            Document doc0 = null;
            try {
                doc0 = Jsoup.connect(URL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements elements10;
            elements10 = doc0.getElementsByClass("styles__brandProductCount--1VNm6");
            String qGoods = elements10.text();
            if (qGoods.length() != 0) {
                String tq = qGoods.replaceAll("[Produkte]", "");
                System.out.println("***************************************************************************");
                System.out.println("Amount of extracted products: " + tq + "Category " + category);
                System.out.println("***************************************************************************");
            }

            /*if (qGoods.length() == 0) {
                System.out.println("***************************************************************************");
                System.out.println("No one goods found. Restart the program.");
                System.out.println("***************************************************************************");
                break;

            }*/ else {
                elements10 = doc0.getElementsByClass("styles__productsCount--16QoZ");
                String qGoods2 = elements10.text();
                String tq = qGoods2.replaceAll("[Produkte]", "");
                System.out.println("***************************************************************************");
                System.out.println("Amount of extracted products: " + tq);
                System.out.println("***************************************************************************");
            }
            System.out.println("***************************************************************************");
            System.out.println("Wait. Processing request....");
            System.out.println("***************************************************************************");
            try {
                doc0 = Jsoup.connect(URL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Elements elements;

            elements = doc0.body().getElementsByClass("styles__container--1bqmB").first().getElementsByAttribute("href");
            for (Element element : elements) {
                String url = element.attr("href");

                if (url.contains("/p/")) {
                    String url3 = "https://www.aboutyou.de" + url;


                    try {
                        doc0 = Jsoup.connect(url3)
                                .timeout(2000)  //Set the total request timeout duration.
                                .get();
                        httpRequests++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Elements elements1 = doc0.select(".productPrices"); //price
                    Elements elements2 = doc0.select(".priceStyles__strike--PSBGK"); //initialPrice
                    Elements elements3 = doc0.select("h1"); //brand
                    Elements elements4 = doc0.select(".styles__title--UFKYd"); //color
                    Elements elements5 = doc0.select(".styles__title--3Jos_"); // name
                    Elements elements6 = doc0.select(".styles__articleNumber--1UszN"); //articul
                    Elements elements7 = doc0.select(".styles__textElement--3QlT_"); //description
                    Elements elements8 = doc0.select(".styles__label--1cfc7"); //shippingCosts



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
                //.replaceAll("[ab]", "")
                .replaceAll("[spn]", "")
                .replaceAll("\n+", "");
    }

    private static String brandCleaner(String a) {
        if (a.length() != 0) {
            return a.substring(0, a.indexOf("|"));
        } else return null;
    }
}
