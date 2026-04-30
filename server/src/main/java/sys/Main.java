package sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import sys.repository.ConstellationRepository;
import sys.service.ConstellationService;
import sys.utils.SpaceOperationException;

@SpringBootApplication
@EnableScheduling
public class Main {
    public static void main(String[] args) throws SpaceOperationException {
        System.out.println("ЗАПУСК СИСТЕМЫ УПРАВЛЕНИЯ СПУТНИКОВОЙ ГРУППИРОВКОЙ");
        System.out.println("============================================================");

        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);

        ConstellationRepository repository = ctx.getBean(ConstellationRepository.class);
        ConstellationService operationCenterService = ctx.getBean(ConstellationService.class);
    }
}