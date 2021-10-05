package project.stockbot.parsers;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class StockParserTest {
    StockParser stockParser;

    {
        try {
            stockParser = new StockParser("fb");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void find() throws IOException {
        System.out.println(stockParser.find());
        assertEquals("Facebook Даты Отчетов и Прогнозы\n" +
                "\n" +
                "Дата отчета: Nov 03, 2021\n" +
                "Финансовый квартал: 2021 (Q3)\n" +
                "Прогноз/ПНА: 3.18 / -\n" +
                "Прошлогодняя ПНА: 2.714\n" +
                "Разн. ПНА за год: ―\n" +
                "\n" +
                "Дата отчета: Jul 28, 2021\n" +
                "Финансовый квартал: 2021 (Q2)\n" +
                "Прогноз/ПНА: 3.04 / 3.61✅\n" +
                "Прошлогодняя ПНА: 1.798\n" +
                "Разн. ПНА за год: 100.95% (+1.81)✅\n" +
                "\n" +
                "Дата отчета: Apr 28, 2021\n" +
                "Финансовый квартал: 2021 (Q1)\n" +
                "Прогноз/ПНА: 2.35 / 3.29✅\n" +
                "Прошлогодняя ПНА: 1.709\n" +
                "Разн. ПНА за год: 92.80% (+1.59)✅\n" +
                "\n" +
                "Дата отчета: Jan 27, 2021\n" +
                "Финансовый квартал: 2020 (Q4)\n" +
                "Прогноз/ПНА: 3.19 / 3.88✅\n" +
                "Прошлогодняя ПНА: 2.56\n" +
                "Разн. ПНА за год: 51.64% (+1.32)✅\n" +
                "\n" +
                "Дата отчета: Oct 29, 2020\n" +
                "Финансовый квартал: 2020 (Q3)\n" +
                "Прогноз/ПНА: 1.90 / 2.71✅\n" +
                "Прошлогодняя ПНА: 2.119\n" +
                "Разн. ПНА за год: 28.08% (+0.59)✅\n\n", stockParser.find());
    }

    @Test
    void findShort() throws IOException {
        assertEquals("Facebook Дата следующего отчета\n" +
                "\n" +
                "Nov 03, 2021\n" +
                "Pre-Market ", stockParser.findShort());
    }
}