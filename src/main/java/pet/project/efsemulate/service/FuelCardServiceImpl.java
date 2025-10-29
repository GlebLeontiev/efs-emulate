package pet.project.efsemulate.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.project.efsemulate.exception.NotFoundException;
import pet.project.efsemulate.mapper.WSMapper;
import pet.project.efsemulate.model.entity.soap.transaction.TransactionEntity;
import pet.project.efsemulate.model.entity.soap.card.CardEntity;
import pet.project.efsemulate.model.entity.soap.policy.PolicyEntity;
import pet.project.efsemulate.repository.*;
import service.cards.tch.com.types.*;
import service.cards.tch.com.types.arrays.com.tch.cards.model.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FuelCardServiceImpl {

    private final CardRepository cardRepository;
    private final PolicyRepository policyRepository;
    private final LocationRepository locationRepository;
    private final TransactionRepository transactionRepository;
    private final PolicyDescriptionRepository policyDescriptionRepository;
    private final WSMapper mapper;

    // --------------- card ---------------

    public WSCard getCard(String clientId, String cardNumber) {
        return cardRepository.findCardEntityByCardNumber(cardNumber)
                .map(mapper::toWsCard)
                .orElse(null);
    }

    
    public void setCard(String clientId, WSCard card) {
        if (card == null || card.getCardNumber() == null) return;
        CardEntity entity = mapper.toCardEntity(card);

        cardRepository.save(entity);
    }

    public WSTransactionArray getTransactions(String clientId, XMLGregorianCalendar begDate, XMLGregorianCalendar endDate) {
        ZonedDateTime beg = begDate != null ? begDate.toGregorianCalendar().toZonedDateTime() : ZonedDateTime.now().minusYears(1);
        ZonedDateTime end = endDate != null ? endDate.toGregorianCalendar().toZonedDateTime() : ZonedDateTime.now();

        List<TransactionEntity> tx = transactionRepository.findByTransactionDateBetween(beg, end);
        return mapper.toWsTransactionArray(tx);
    }

    public WSCardSummaryArray getCardSummaries(String clientId, WSCardSummaryReq request) {
        return mapper.toWsCardSummaryArray(cardRepository.findAll());
    }

    public WSPolicy getPolicy(String clientId, int policyNumber) {
        Optional<PolicyEntity> p = policyRepository.findById((long) policyNumber);
        return p.map(mapper::toWsPolicy).orElse(null);
    }
    public WSLocationArray searchLocation(String clientId, WSLocationSearch search) {
        return mapper.toWsLocationArray(
                locationRepository.findAllById(List.of(Long.valueOf(search.getLocId())))
        );
    }

    public WSPolicyDescriptionArray getPolicyDescriptions(String clientId) {
        return mapper.toWsPolicyDescriptionArray(policyDescriptionRepository.findAll());
    }
}
