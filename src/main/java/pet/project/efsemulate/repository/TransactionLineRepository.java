package pet.project.efsemulate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.efsemulate.model.entity.soap.card.CardEntity;
import pet.project.efsemulate.model.entity.soap.transaction.TransactionLineItemEntity;

public interface TransactionLineRepository extends JpaRepository<TransactionLineItemEntity, Long> {
}
