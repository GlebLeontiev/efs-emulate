package pet.project.efsemulate.model.entity.soap;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LocationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "chain_id")
    private String chainId;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "phone")
    private String phone;
}
