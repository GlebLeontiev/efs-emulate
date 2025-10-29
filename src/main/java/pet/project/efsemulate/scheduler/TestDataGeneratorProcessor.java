package pet.project.efsemulate.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pet.project.efsemulate.model.entity.soap.card.CardEntity;
import pet.project.efsemulate.repository.CardRepository;
import pet.project.efsemulate.service.FuelCardServiceImpl;
import pet.project.efsemulate.util.TestDataGenerator;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataGeneratorProcessor {

    private final FuelCardServiceImpl fuelCardService;
    private final TestDataGenerator testDataGenerator;
    private final CardRepository cardRepository;

    @Scheduled(initialDelayString = "${scheduler.initial-delay}", fixedRateString = "${scheduler.fixed-rate}")
    public void processActiveFuelCards(){
        List<CardEntity> activeCards = cardRepository.findActiveCards();

        testDataGenerator.generateTransactionsByFuelCard(activeCards);
    }

}
