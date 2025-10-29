package pet.project.efsemulate.endpointws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pet.project.efsemulate.model.dto.GetTransactionsRequest;
import pet.project.efsemulate.model.dto.GetTransactionsResponse;
import pet.project.efsemulate.service.AuthService;
import pet.project.efsemulate.service.FuelCardServiceImpl;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSTransactionArray;

import javax.xml.datatype.XMLGregorianCalendar;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class TransactionEndpoint {

    private static final String NS = "http://com.tch.cards.service";

    private final FuelCardServiceImpl fuelCardService;
    private final AuthService authService;

    @PayloadRoot(namespace = NS, localPart = "getTransactions")
    @ResponsePayload
    public GetTransactionsResponse getTransactions(@RequestPayload GetTransactionsRequest request) {
        authService.validateClientId(request.getClientId());
        XMLGregorianCalendar beg = request.getBegDate();
        XMLGregorianCalendar end = request.getEndDate();
        log.info("TransactionEndpoint: Rcv request to fetch Transactions from {} to {}", beg, end);
        WSTransactionArray arr = fuelCardService.getTransactions(request.getClientId(), beg, end);
        GetTransactionsResponse resp = new GetTransactionsResponse();
        resp.setResult(arr);
        log.info("TransactionEndpoint: Sending response with Transactions array {}", arr != null && arr.getValue() != null ? arr.getValue().size() : 9999999);
        return resp;
    }
}
