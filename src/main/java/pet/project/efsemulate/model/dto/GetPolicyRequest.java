package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getPolicy", namespace = "http://com.tch.cards.service")
public class GetPolicyRequest {
    @XmlElement(required = true)
    private String clientId;
    @XmlElement(required = true)
    private int policyNumber;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public int getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(int policyNumber) { this.policyNumber = policyNumber; }
}
