package pet.project.efsemulate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pet.project.efsemulate.model.entity.soap.card.CardEntity;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findCardEntityByCardNumber(String cardNumber);

    @Query(value = "SELECT * FROM efs_fuel_cards WHERE efs_fuel_cards.status = 'ACTIVE'", nativeQuery = true)
    List<CardEntity> findActiveCards();
}
