package pet.project.efsemulate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.efsemulate.model.entity.soap.transaction.TransactionEntity;

import java.time.ZonedDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByTransactionDateBetween(ZonedDateTime beg, ZonedDateTime end);
}
