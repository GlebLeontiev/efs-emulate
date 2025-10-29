package pet.project.efsemulate.model.entity.soap.card.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CardLimit {

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "limit_amount")
    private Integer limit;

    @Column(name = "limit_id")
    private String limitId;

    @Column(name = "min_hours")
    private Integer minHours;
}
