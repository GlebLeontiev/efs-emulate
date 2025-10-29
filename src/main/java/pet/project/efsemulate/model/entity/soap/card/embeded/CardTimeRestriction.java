package pet.project.efsemulate.model.entity.soap.card.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.ZonedDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CardTimeRestriction {

    @Column(name = "day_of_week")
    private Integer day;

    @Column(name = "begin_time")
    private ZonedDateTime beginTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;
}
