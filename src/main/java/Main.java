import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

 /* public static Set<String> getUrlSetFromSearchByPattern(String pattern) {
        Set<String> offersURLs = new HashSet<String>();
        int[] categories = new int[]{138113};
        for (int category : categories) {
            offersURLs.add(
                            "https://www.aboutyou.de/suche?"
                                    + "term=" + pattern.replaceAll(" ", "+")
                                    + "&category=" + category );
        }
        return offersURLs;
    }*/

    public static void main(String[] args) throws IOException {
         int httpRequests = 0;
         int prodAmount = 0;
        long startTime = System.nanoTime();

        String url = "https://www.aboutyou.de/about/brand/nike?category=20201";

        int i = 0;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("html body main#app section.styles__layout--1QK8W.styles__stretchLayout--3omnx div div.styles__container--2cj5w div.row div.col-sm-9.col-md-10 div div div.styles__container--1bqmB div.row div.styles__container--1bqmB div.styles__tile--2s8XN.col-sm-6.col-md-4.col-lg-4 div a");
        for (Element element : elements) {
            String url3 = "https://www.aboutyou.de" + element.attr("href");  //ФОРМИРУЕМ УРЫЛ ПО КОТОРОЙ МЫ ПОЛУАЕМ

            Document doc3 = Jsoup.connect(url3).get(); //ПОЛУЧИЛИ СТРАНИЦУ ВАКАНСИИ

            Elements elements1 = doc3.select(".productPrices");
            Elements elements2 = doc3.select(".priceStyles__strike--PSBGK");
           // Elements elements0 = doc3.select(".styles__campaignBadgeText--z4YFj"); //скадка
            Elements elements3 = doc3.select("h1"); //бренд
            Elements elements4 = doc3.select(".styles__title--UFKYd");
            Elements elements5 = doc3.select(".styles__title--3Jos_");
            Elements elements6 = doc3.select(".styles__articleNumber--1UszN");
            Elements elements7 = doc3.select(".styles__textElement--3QlT_");
            Elements elements8 = doc3.select(".styles__label--1cfc7");

            httpRequests++;

            String c = cleanHTML(
                    "Стоимость со скидкой: " + elements1.text() + "---"
                            + "Предыдущая цена: " + elements2.text() + "---"
                           // + "Скидка: " + discountCleaner(elements0.text()) + "---"
                            + "Бренд: " + brandCleaner(elements3.text()) + "---"
                            + "Цвет: " + elements4.text() + "---"
                            + "Название товара: " + elements5.text() + "---"
                            + "Артикул: " + elements6.text() + "---"
                            + "Описание товара: " + elements7.text() + "---"
                            + "Стоимость доставки: " + elements8.text());

            System.out.println(c);
        }
        long runTime = (System.nanoTime() - startTime) / 10000000;
        System.out.println("Run-time = " + runTime / 100 + " sec");
        System.out.println("Amount of triggered HTTP request " + httpRequests);
        //System.out.println ("Amount of extracted products: " + offers.getList().size() + ".");
       // System.out.println ("Memory Footprint: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " kilobytes.");
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

    private static  String brandCleaner (String a){
        return a.substring(0, a.indexOf("|"));
    }

    private static  String discountCleaner (String a){
        return a
                .replaceAll("[i]", "");
    }
}

