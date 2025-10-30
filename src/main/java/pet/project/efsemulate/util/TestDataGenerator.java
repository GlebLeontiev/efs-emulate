package pet.project.efsemulate.util;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.efsemulate.model.entity.soap.LocationEntity;
import pet.project.efsemulate.model.entity.soap.card.CardEntity;
import pet.project.efsemulate.model.entity.soap.transaction.TransactionEntity;
import pet.project.efsemulate.model.entity.soap.transaction.TransactionLineItemEntity;
import pet.project.efsemulate.repository.CardRepository;
import pet.project.efsemulate.repository.LocationRepository;
import pet.project.efsemulate.repository.TransactionLineRepository;
import pet.project.efsemulate.repository.TransactionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
public class TestDataGenerator {

    private static final Random random = new Random();
    private static final Logger log = LoggerFactory.getLogger(TestDataGenerator.class);
    private final AtomicLong atomicInteger = new AtomicLong();
    private final LocationRepository locationRepository;
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    @Transactional
    public void generateTransactionsByFuelCard(List<CardEntity> fuelCards) {
        if (fuelCards == null || fuelCards.isEmpty()) return;

        List<LocationEntity> locations = locationRepository.findAll();
        if (locations.isEmpty()) return;

        List<TransactionEntity> transactionsToSave = new ArrayList<>();
        List<TransactionLineItemEntity> allLines = new ArrayList<>();

        for (CardEntity card : fuelCards) {
            LocationEntity location = locations.get(random.nextInt(locations.size()));

            int linesCount = 1 + random.nextInt(2); // 1..2
            List<TransactionLineItemEntity> lines = new ArrayList<>();
            BigDecimal netTotal = BigDecimal.ZERO;

            for (int i = 0; i < linesCount; i++) {
                int useType = 0;
                if (i == 0){
                    useType = 1;
                }

                BigDecimal quantity = randomBigDecimal(5.0, 180.0).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal retailPPU = randomBigDecimal(2.0, 6.0).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal gross = quantity.multiply(retailPPU); // quantity * retail_ppu
                BigDecimal discount = gross.multiply(randomBigDecimal(0.05, 0.20)).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal amount = gross.subtract(discount).setScale(2, RoundingMode.HALF_EVEN);
                BigDecimal ppu = quantity.compareTo(BigDecimal.ZERO) > 0
                        ? amount.divide(quantity, 4, RoundingMode.HALF_EVEN)
                        : BigDecimal.ZERO;

                boolean isFuel = useType != 0;

                TransactionLineItemEntity line = TransactionLineItemEntity.builder()
                        .lineNumber(i + 1)
                        .number(i + 1)
                        .billingFlag(0)
                        .category(isFuel ? "FUEL" : "NON_FUEL")
                        .groupCategory(null)
                        .groupNumber(0)
                        .fuelType(isFuel ? 1 : 0)
                        .serviceType(isFuel ? 0 : 1)
                        .useType(useType)
                        .prodCD(isFuel ? "DIESEL" : "SERVICE")
                        .quantity(quantity)
                        .retailPPU(retailPPU)
                        .ppu(ppu)
                        .discAmount(discount)
                        .amount(amount) // сумма без скидки disc_amount
                        .issuerDeal(BigDecimal.ZERO)
                        .issuerDealPPU(BigDecimal.ZERO)
                        .cmptAmount(BigDecimal.ZERO)
                        .cmptPPU(BigDecimal.ZERO)
                        .build();

                lines.add(line);
                netTotal = netTotal.add(amount);
            }

            TransactionEntity tx = TransactionEntity.builder()
                    .card(card)
                    .location(location)
                    .transactionDate(java.time.ZonedDateTime.now())
                    .amount(netTotal)
                    .netTotal(netTotal)
                    .fundedTotal(netTotal)
                    .prefTotal(netTotal)
                    .settleAmount(netTotal)
                    .lineItems(lines)
                    .build();

            for (TransactionLineItemEntity li : lines) li.setTransaction(tx);

            TransactionEntity save = transactionRepository.save(tx);
            card.getHeader().setLastTransaction(Math.toIntExact(save.getId()));
            cardRepository.save(card);

            transactionsToSave.add(tx);
            allLines.addAll(lines);
        }

        log.info("TestDataGenerator: created {} transactions and {} line items",
                transactionsToSave.size(), allLines.size());
    }

    private BigDecimal randomBigDecimal(double min, double max) {
        return BigDecimal.valueOf(min + (max - min) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
    }
}
