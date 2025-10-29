package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.WSCard;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getCardResponse", namespace = "http://com.tch.cards.service")
public class GetCardResponse {
    @XmlElement
    private WSCard result;

    public WSCard getResult() { return result; }
    public void setResult(WSCard result) { this.result = result; }
}
