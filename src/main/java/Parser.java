import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;


public class Parser {

    volatile static int httpRequests = 0;
    long startTime = System.nanoTime();
    int numberOfPage = 1;

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public static void setA(int a) {
        Parser.a = a;
    }

    private static int a;

   /* int Kinder = 138113;
    int Frauen = 20201;
    int  Männer = 20202;
    int [] all = {138113, 20201, 20202};*/


    //make our https links - link #1 and push to urlPagenation
    public synchronized ArrayList<String> getUrlSetFromSearchByPattern() { //brand
        ArrayList<String> offersURLs = new ArrayList<String>();
        int[] categories = new int[]{a}; /*138113, 20201, 20202;*/
        for (int category : categories) {
            offersURLs.add("https://www.aboutyou.de/suche?"
                    + "term=" + getPattern().replaceAll(" ", "+")
                    + "&category=" + category);
        }
        return offersURLs;
    }


    public synchronized List<Offer> parser2(ArrayList<String> arrayList) {
        List<Offer> offers = new ArrayList<>();

        for (String category : arrayList) {


            Document doc0 = null;
            try {
                doc0 = Jsoup.connect(category).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Waiting. Processing of parsing of the page....for page: " + category);
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
                //.replaceAll("[spn]", "")
                .replaceAll("\n+", "");
    }

    private static String brandCleaner(String a) {
        if (a.length() != 0) {
            return a.substring(0, a.indexOf("|"));
        } else return null;
    }

    //count total of products
    public int amounOfProducts() {
        int qOfpages = 0;

        for (String url : getUrlSetFromSearchByPattern()) {

            Document doc0 = null;
            try {
                doc0 = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Elements elements10 = doc0.getElementsByClass("styles__brandProductCount--1VNm6");
            String qGoods = elements10.text();
            if (qGoods.length() != 0) {
                String tq = qGoods.replaceAll("[Produkte]", "");
                String tq2 = tq.replaceAll(" ", "");
                System.out.println("Amount of extracted products: " + tq2 + " in Category " + url);
                System.out.println("---------------------------------------------------------------------------");
            }
         else
             {
            elements10 = doc0.getElementsByClass("styles__productsCount--16QoZ");
            String qGoods2 = elements10.text();
            String tq = qGoods2.replaceAll("[Produkte]", "");
            String tq2 = tq.replaceAll(" ", "");
            System.out.println("Amount of extracted products: " + tq2 + " in Category " + url);
                 System.out.println("---------------------------------------------------------------------------");
        }
    }
        return qOfpages;
    }

    //get our link #1 and make links for each pages -> push to parser2
    public ArrayList<String> urlPagenation() {

        boolean ar = true;

        ArrayList<String> arrayList5 = new ArrayList<>();
        arrayList5.add(getUrlSetFromSearchByPattern().toString().replaceAll("^\\[|\\]$", ""));

        while (ar)
        {
            String newURL = arrayList5.get(arrayList5.size() - 1);


            Document doc0 = null;

            try {
                doc0 = Jsoup.connect(newURL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements10 = doc0.body().getElementsByClass("styles__buttonNext--3YXvj").first().getElementsByAttribute("href");
            if (elements10.size() == 0) {
                ar = false;
                //styles__buttonLink--BgPaW
            }
            for (Element element : elements10) {
                String url2 = element.attr("href");

               // if (url2.contains("/about/brand/")) {
                    String url3 = "https://www.aboutyou.de" + url2;
                    arrayList5.add(url3);

               // }
            }
        }
        return arrayList5;
    }
}

