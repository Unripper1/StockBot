package project.stockbot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.stockbot.parsers.StockParser;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 * This example bot is an echo bot that just repeats the messages sent to him
 *
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String username;
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(String.valueOf(chatId));
            String text = message.getText();
            response.setReplyMarkup(replyKeyboardMarkup);
            if(message.getText().equals("/start")){
                response.setText("Привет! \nОтпрявляй мне тикеры компаний (например для Apple - aapl, для Ford - f и т.д.), и получай следующую дату отчета. Допиши full (например aapl full) и получи календарь отчетов и прогнозы прибыли!");
            }
            if(message.getText().equals("/info")){
                response.setText("Отпрявляй мне тикеры компаний (например для Apple - aapl, для Ford - f и т.д.), и получай следующую дату отчета. Допиши full (например aapl full) и получи календарь отчетов и прогнозы прибыли!");
            }
            else {
                if(text.contains("full")) {
                    text = text.replace("full", "");
                    text = text.replace(" ", "");
                    try {
                        response.setText(new StockParser(text).find());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        response.setText(new StockParser(text).findShort());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }
}