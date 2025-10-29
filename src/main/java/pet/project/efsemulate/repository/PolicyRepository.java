package pet.project.efsemulate.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.efsemulate.model.entity.soap.policy.PolicyEntity;

public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> { }
