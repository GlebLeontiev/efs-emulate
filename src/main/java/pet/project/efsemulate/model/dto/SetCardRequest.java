package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.WSCard;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "setCardRequest", namespace = "http://com.tch.cards.service")
public class SetCardRequest {
    @XmlElement(required = true)
    private String clientId;
    @XmlElement(required = true)
    private WSCard card;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public WSCard getCard() { return card; }
    public void setCard(WSCard card) { this.card = card; }
}
