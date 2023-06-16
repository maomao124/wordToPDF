package mao.wordtopdf;

import mao.wordtopdf.service.WordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WordToPdfApplication
{

    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(WordToPdfApplication.class, args);
        WordService wordService = context.getBean(WordService.class);
        wordService.exec();
    }

}
