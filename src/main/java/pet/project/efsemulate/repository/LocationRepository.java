package pet.project.efsemulate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.efsemulate.model.entity.soap.LocationEntity;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findByStateContainingIgnoreCaseAndCityContainingIgnoreCaseAndNameContainingIgnoreCaseAndCountryContainingIgnoreCaseAndChainIdContainingIgnoreCase(
            String state, String city, String name, String country, String chainId
    );
}
