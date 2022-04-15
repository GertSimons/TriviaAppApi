package nl.quad.triviaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import nl.quad.triviaapi.exceptions.TriviaApiException;
import nl.quad.triviaapi.services.TriviaApiService;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@Slf4j
public class TriviaApiApplication {
    private static final String QUESTIONS = "questions";
    private final CacheManager cacheManager;
    private final TriviaApiService triviaApiService;

    public TriviaApiApplication(CacheManager cacheManager, TriviaApiService triviaApiService) {
        this.cacheManager = cacheManager;
        this.triviaApiService = triviaApiService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TriviaApiApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void updateCache() throws TriviaApiException {
        if (cacheManager.getCacheNames().contains(QUESTIONS)) {
            cacheManager.getCache(QUESTIONS).put(QUESTIONS, triviaApiService.getAllQuestions());
            log.info("questions cache updated");
        }
    }
}
