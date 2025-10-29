package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getCard", namespace = "http://com.tch.cards.service")
public class GetCardRequest {
    @XmlElement(required = true)
    private String clientId;
    @XmlElement(required = true)
    private String cardNumber;


}
