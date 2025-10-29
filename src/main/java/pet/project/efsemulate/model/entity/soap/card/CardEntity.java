package pet.project.efsemulate.model.entity.soap.card;

import jakarta.persistence.*;
import lombok.*;
import pet.project.efsemulate.model.entity.User;
import pet.project.efsemulate.model.entity.soap.card.embeded.CardHeader;
import pet.project.efsemulate.model.entity.soap.card.embeded.CardLimit;
import pet.project.efsemulate.model.entity.soap.card.embeded.CardTimeRestriction;

import java.util.List;

@Entity
@Table(name = "efs_fuel_cards")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CardEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    @Embedded
    private CardHeader header;

    @OneToMany(mappedBy = "cardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CardInfoEntity> info;

    @Embedded
    private CardLimit limit; //TODO if needed replace Embedded to separate entity

    @Embedded
    private CardTimeRestriction timeRestriction; //TODO if needed replace Embedded to separate entity

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
//    private String locationGroups;
//
//    private String locations;
}
