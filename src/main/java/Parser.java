import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Parser {

    volatile static int httpRequests = 0;
    long startTime = System.nanoTime();

   private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    //храним ссылку + меняет категорию + назначаем поиск //есть 3 ссылки с разными категориями
    public  ArrayList<String> getUrlSetFromSearchByPattern() { //brand
        ArrayList<String> offersURLs = new ArrayList<String>();
        int[] categories = new int[]{138113, 20201, 20202};
        for (int category : categories) {
            offersURLs.add("https://www.aboutyou.de/suche?"
                    + "term=" + getPattern() .replaceAll(" ", "+")
                    + "&category=" + category);
        }
        return offersURLs;
    }


    public  synchronized List<Offer> parser2(ArrayList<String> arrayList ) {
       // Set<String> offersURLs = new HashSet<>();

        List<Offer> offers = new ArrayList<>();

        for ( String category : arrayList) {


            //find of total of goods
            Document doc0 = null;
            try {
                doc0 = Jsoup.connect(category).get();
            } catch (IOException e) {
                e.printStackTrace();
            }



            System.out.println("---------------------------------------------------------------------------");
            System.out.println("Wait. Processing request....");
            System.out.println("---------------------------------------------------------------------------");
            try {
                doc0 = Jsoup.connect(category).get();
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

   /* private  int amounOfProducts() {
        int x3=0;

        Document doc0 = null;
        try {
            doc0 = Jsoup.connect().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements10 = doc0.getElementsByClass("styles__brandProductCount--1VNm6");
        String qGoods = elements10.text();
        if (qGoods.length() != 0) {
            String tq = qGoods.replaceAll("[Produkte]", "");
            String tq2 = tq.replaceAll(" ", "");
            if (tq2.length() > 0) {
                float x = Integer.parseInt(tq2);
                float x2 = x / 99;
                 x3 = ((int) Math.ceil(x2));
                System.out.println(x3);

                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Amount of extracted products: " + tq + "in Category " + category);
                System.out.println("---------------------------------------------------------------------------");
            }
            else return 0;
        }


        else {
            elements10 = doc0.getElementsByClass("styles__productsCount--16QoZ");
            String qGoods2 = elements10.text();
            String tq = qGoods2.replaceAll("[Produkte]", "");
            String tq2 = tq.replaceAll(" ", "");
            if (tq2.length() > 0) {
                float x = Integer.parseInt(tq2);
                float x2 = x / 99;
                 x3 = ((int) Math.ceil(x2));
                System.out.println(x3);
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Amount of extracted products: " + tq + "in Category " + category);
                System.out.println("---------------------------------------------------------------------------");
            }
            else return 0;
        }


        return x3;
    }*/
}

  /*if (qGoods.length() == 0) {
                System.out.println("***************************************************************************");
                System.out.println("No one goods found. Restart the program.");
                System.out.println("***************************************************************************");
                break;

            }*/