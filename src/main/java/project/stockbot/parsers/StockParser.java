package project.stockbot.parsers;

import com.vdurmont.emoji.EmojiParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class StockParser {
    Document doc;
    public StockParser(String name) throws IOException {
        doc = Jsoup.connect("https://www.tipranks.com/stocks/"+name+"/earnings-calendar")
                .userAgent("Chrome/91.0.4472.124")
                .referrer("http://www.google.com")
                .get();
    }
    public String find() throws IOException {
        int n=0;
        String finstr=doc.select("#tr-stock-page-content > div.maxW1200.grow1.flexc__.flexc__.displayflex > div.flexc__.displayflex.py4.colorblack-5.mobile_w12.mobile_displayflex.mobile_px3 > div > h1").text()+"\n"+"\n";
        finstr=finstr.replace("Earnings Date & Forecast","Даты Отчетов и Прогнозы");
        Elements earndate = doc.select("#tr-stock-page-content > div.maxW1200.grow1.flexc__.flexc__.displayflex > div.minW80.z1.flexr__f.maxW1200.mobile_maxWparent > div:nth-child(3) > div.flexc__.mt3.bgwhite.displayflex.border1.borderColorwhite-8.shadow1.positionrelative.grow1 > div.w12.p0.displayflex.positionrelative.grow1 > div > div._3CluHzHd8xFWivEyS-H4vZ.B_WXH1qugMiqzvGEhmMnQ.borderColorgray.bb2_solid > div > div.rt-tbody");
        for (Element element : earndate.select("div.rt-tr-group")) {
            int k = 0;
            if(n==5){
                break;
            }
            for (Element element2 : element.select("div.rt-td.rt-left")) {
                if (k == 0)
                    finstr += "Дата отчета: " + element2.text() + "\n";
                if (k == 1)
                    finstr += "Финансовый квартал: " + element2.text() + "\n";
                if (k == 2) {
                    try {
                        if (Double.valueOf(element2.text().split(" / ")[0]) > Double.valueOf(element2.text().split(" / ")[1]))
                            finstr += "Прогноз/ПНА: " + element2.text() + EmojiParser.parseToUnicode(":red_circle:") + "\n";
                        if (Double.valueOf(element2.text().split(" / ")[0]) < Double.valueOf(element2.text().split(" / ")[1]))
                            finstr += "Прогноз/ПНА: " + element2.text() + EmojiParser.parseToUnicode(":white_check_mark:") + "\n";
                    }
                    catch (Exception e){
                        finstr += "Прогноз/ПНА: " + element2.text()  + "\n";
                    }
                }
                if (k == 3)
                    finstr += "Прошлогодняя ПНА: " + element2.text() + "\n";
                if (k == 4) {
                    if(element2.text().contains("-")) {
                        finstr += "Разн. ПНА за год: " + element2.text() +  EmojiParser.parseToUnicode(":red_circle:") +"\n" + "\n";
                    }
                    else
                    if(element2.text().contains("―")) {
                        finstr += "Разн. ПНА за год: " + element2.text()  +"\n" + "\n";
                    }
                    else
                        finstr += "Разн. ПНА за год: " + element2.text() +  EmojiParser.parseToUnicode(":white_check_mark:") +"\n" + "\n";
                    k=0;
                }
                k++;
            }
            n++;
        }
        return finstr;
    }
    public String findShort() throws IOException {
        String finstr = doc.select("#tr-stock-page-content > div.maxW1200.grow1.flexc__.flexc__.displayflex > div.flexc__.displayflex.py4.colorblack-5.mobile_w12.mobile_displayflex.mobile_px3 > div > h1").text() + "\n" + "\n";
        finstr = finstr.replace("Earnings Date & Forecast", "Дата следующего отчета");
        finstr += doc.select("#tr-stock-page-content > div.maxW1200.grow1.flexc__.flexc__.displayflex > div.minW80.z1.flexr__f.maxW1200.mobile_maxWparent > div:nth-child(1) > div.flexc__.mt3.bgwhite.displayflex.border1.borderColorwhite-8.shadow1.positionrelative.grow1 > div.w12.p3.displayflex.positionrelative.grow1 > div > div:nth-child(1) > div.flexc__.displayflex.mb6 > span").text()+"\n";
        finstr += doc.select("#tr-stock-page-content > div.maxW1200.grow1.flexc__.flexc__.displayflex > div.minW80.z1.flexr__f.maxW1200.mobile_maxWparent > div:nth-child(1) > div.flexc__.mt3.bgwhite.displayflex.border1.borderColorwhite-8.shadow1.positionrelative.grow1 > div.w12.p3.displayflex.positionrelative.grow1 > div > div:nth-child(1) > div.fontWeightnormal.fontSize7small").text();
        finstr=finstr.replace("Report Date","");
        finstr=finstr.replace("Not Confirmed","");

        return finstr;
    }

}
