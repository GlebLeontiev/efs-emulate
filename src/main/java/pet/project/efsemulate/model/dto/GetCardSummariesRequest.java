package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.WSCardSummaryReq;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getCardSummaries", namespace = "http://com.tch.cards.service")
public class GetCardSummariesRequest {
    @XmlElement(required = true)
    private String clientId;
    @XmlElement(required = true)
    private WSCardSummaryReq request;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public WSCardSummaryReq getRequest() { return request; }
    public void setRequest(WSCardSummaryReq request) { this.request = request; }
}
