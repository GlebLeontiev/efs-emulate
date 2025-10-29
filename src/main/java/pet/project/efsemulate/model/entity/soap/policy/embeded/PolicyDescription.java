package pet.project.efsemulate.model.entity.soap.policy.embeded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyDescription {
    private Integer contractId;
    private String description;
}
