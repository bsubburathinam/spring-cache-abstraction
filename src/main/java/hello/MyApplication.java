package hello;

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

@SpringBootApplication
@EnableCaching
public class MyApplication
{
    private static final Logger log = LoggerFactory.getLogger(MyApplication.class);

    @Bean
    public CacheManager buildCacheManager()
    {
        return new GuavaCacheManager();
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
            log.info("isbn-1234--->" + bookRepository.getByIsbn("isbn-1234"));
        }
    }

    public static void main(String[] args)
    {
        SpringApplication.run(MyApplication.class, args);
    }
}
