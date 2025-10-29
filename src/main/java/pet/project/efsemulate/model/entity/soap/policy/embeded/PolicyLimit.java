package pet.project.efsemulate.model.entity.soap.policy.embeded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyLimit {
    private Integer hours;
    private Integer limit;
    private String limitId;
    private Integer minHours;
    private Integer autoRollMap;
    private Integer autoRollMax;
}
