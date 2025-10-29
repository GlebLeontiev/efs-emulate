package pet.project.efsemulate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.efsemulate.model.entity.soap.policy.PolicyDescriptionEntity;

public interface PolicyDescriptionRepository extends JpaRepository<PolicyDescriptionEntity, Long> {
}
