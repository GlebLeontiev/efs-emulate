package pet.project.efsemulate.model.entity.soap.policy.embeded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.ZonedDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyTimeRestriction {
    private Integer day;
    private ZonedDateTime beginTime;
    private ZonedDateTime endTime;
}
