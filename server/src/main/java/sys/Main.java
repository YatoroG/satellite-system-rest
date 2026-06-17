package sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import sys.repository.ConstellationRepository;
import sys.service.ConstellationService;
import sys.utils.SpaceOperationException;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class Main {
    public static void main(String[] args) throws SpaceOperationException {
        SpringApplication.run(Main.class, args);
    }
}