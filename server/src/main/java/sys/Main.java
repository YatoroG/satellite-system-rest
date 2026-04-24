package sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import sys.domains.satellites.CommunicationSatelliteParam;
import sys.domains.satellites.ImagingSatelliteParam;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.factory.SatelliteFactory;
import sys.factory.impl.CommunicationSatelliteFactory;
import sys.factory.impl.ImagingSatelliteFactory;
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

        System.out.println("СОЗДАНИЕ СПЕЦИАЛИЗИРОВАННЫХ СПУТНИКОВ:");
        System.out.println("============================================================");
        SatelliteFactory commFactory = ctx.getBean(CommunicationSatelliteFactory.class);
        SatelliteFactory imgFactory = ctx.getBean(ImagingSatelliteFactory.class);

        SatelliteParam commParam1 = new CommunicationSatelliteParam("Связь-1", 0.85, 500.0);
        SatelliteParam commParam2 = new CommunicationSatelliteParam("Связь-2", 0.75, 1000.0);
        SatelliteParam imgParam1 = new ImagingSatelliteParam("ДЗЗ-1", 0.92, 2.5);
        SatelliteParam imgParam2 = new ImagingSatelliteParam("ДЗЗ-2", 0.45, 2.5);
        SatelliteParam imgParam3 = new ImagingSatelliteParam("ДЗЗ-3", 0.15, 1.0);

        Satellite comm1 = commFactory.createSatelliteWithParameter(commParam1);
        Satellite comm2 = commFactory.createSatelliteWithParameter(commParam2);
        Satellite img1 = imgFactory.createSatelliteWithParameter(imgParam1);
        Satellite img2 = imgFactory.createSatelliteWithParameter(imgParam2);
        Satellite img3 = imgFactory.createSatelliteWithParameter(imgParam3);

        System.out.println("---------------------------------------------");

        System.out.println("СОЗДАНИЕ ГРУППИРОВОК:");
        System.out.println("============================================================");
        String groupName1 = "Группа-1";
        String groupName2 = "Группа-2";
        operationCenterService.createAndSaveConstellation(groupName1);
        operationCenterService.createAndSaveConstellation(groupName2);
        System.out.println("---------------------------------------------");

        System.out.println("ФОРМИРОВАНИЕ ГРУППИРОВОК:");
        System.out.println("============================================================");
        operationCenterService.addSatelliteToConstellation(groupName1, comm1);
        operationCenterService.addSatelliteToConstellation(groupName1, comm2);
        operationCenterService.addSatelliteToConstellation(groupName2, img1);
        operationCenterService.addSatelliteToConstellation(groupName2, img2);
        operationCenterService.addSatelliteToConstellation(groupName2, img3);
        System.out.println("---------------------------------------------");

        operationCenterService.showConstellationStatus(groupName1);
        System.out.println("---------------------------------------------");
        operationCenterService.showConstellationStatus(groupName2);
        System.out.println("---------------------------------------------");

        operationCenterService.activateAllSatellites(groupName1);
        System.out.println("---------------------------------------------");
        operationCenterService.activateAllSatellites(groupName2);
        System.out.println("---------------------------------------------");

        operationCenterService.executeConstellationMission(groupName1);
        System.out.println("---------------------------------------------");
        operationCenterService.showConstellationStatus(groupName1);
        System.out.println("---------------------------------------------");

        operationCenterService.executeConstellationMission(groupName2);
        System.out.println("---------------------------------------------");
        operationCenterService.showConstellationStatus(groupName2);
        System.out.println("---------------------------------------------");
    }
}