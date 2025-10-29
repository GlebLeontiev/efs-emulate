package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getTransactions", namespace = "http://com.tch.cards.service")
public class GetTransactionsRequest {
    @XmlElement(required = true)
    private String clientId;
    @XmlElement
    private XMLGregorianCalendar begDate;
    @XmlElement
    private XMLGregorianCalendar endDate;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public XMLGregorianCalendar getBegDate() { return begDate; }
    public void setBegDate(XMLGregorianCalendar begDate) { this.begDate = begDate; }
    public XMLGregorianCalendar getEndDate() { return endDate; }
    public void setEndDate(XMLGregorianCalendar endDate) { this.endDate = endDate; }
}
