package hello;

import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
public class MyApplication
{
    private static final Logger log = LoggerFactory.getLogger(MyApplication.class);

    @Bean
    public CacheManager buildCacheManager()
    {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(
            CacheBuilder.newBuilder()
                .expireAfterWrite(
                    60,
                    TimeUnit.SECONDS
                )
        );
        return cacheManager;
    }

    @Component
    static class Runner implements CommandLineRunner
    {
        @Autowired
        private BookRepository bookRepository;

        @Override
        public void run(String... args) throws Exception
        {
            log.info("...Fetching books.");
            log.info("isbn-1234--->" + bookRepository.getByIsbn("isbn-1234"));
            log.info("isbn-1234--->" + bookRepository.getByIsbn("isbn-1234"));
            Thread.sleep(30000L);
            log.info("...Fetching books.");
            log.info("isbn-1234--->" + bookRepository.getByIsbn("isbn-1234"));
            log.info("isbn-1234--->" + bookRepository.getByIsbn("isbn-1234"));
            simulateSlowService();
            log.info("...Fetching books.");
            log.info("isbn-1234--->" + bookRepository.getByIsbn("isbn-1234"));
            log.info("isbn-1234--->" + bookRepository.getByIsbn("isbn-1234"));

        }

        private void simulateSlowService() {
            try {
                long time = 65000L;
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    public static void main(String[] args)
    {
        SpringApplication.run(MyApplication.class, args);
    }


}
