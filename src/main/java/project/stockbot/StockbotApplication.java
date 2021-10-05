package project.stockbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.stockbot.parsers.StockParser;

import java.io.IOException;

@SpringBootApplication
public class StockbotApplication {

    public static void main(String[] args) throws IOException {
        StockParser stockParser=new StockParser("aapl");
        System.out.println(stockParser.find());
        System.out.println(stockParser.findShort());
        SpringApplication.run(StockbotApplication.class, args);
    }

}
