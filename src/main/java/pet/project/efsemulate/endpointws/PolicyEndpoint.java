package pet.project.efsemulate.endpointws;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pet.project.efsemulate.model.dto.GetPolicyDescriptionsRequest;
import pet.project.efsemulate.model.dto.GetPolicyDescriptionsResponse;
import pet.project.efsemulate.model.dto.GetPolicyRequest;
import pet.project.efsemulate.model.dto.GetPolicyResponse;
import pet.project.efsemulate.service.AuthService;
import pet.project.efsemulate.service.FuelCardServiceImpl;
import service.cards.tch.com.types.WSPolicy;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSPolicyDescriptionArray;

@Endpoint
@RequiredArgsConstructor
public class PolicyEndpoint {
    private static final String NS = "http://com.tch.cards.service";

    private final FuelCardServiceImpl fuelCardService;
    private final AuthService authService;

    @PayloadRoot(namespace = NS, localPart = "getPolicyDescriptions")
    @ResponsePayload
    public GetPolicyDescriptionsResponse getPolicyDescriptions(@RequestPayload GetPolicyDescriptionsRequest request) {
        WSPolicyDescriptionArray arr = fuelCardService.getPolicyDescriptions(request.getClientId());
        GetPolicyDescriptionsResponse resp = new GetPolicyDescriptionsResponse();
        resp.setResult(arr);
        return resp;
    }

    @PayloadRoot(namespace = NS, localPart = "getPolicy")
    @ResponsePayload
    public GetPolicyResponse getPolicy(@RequestPayload GetPolicyRequest request) {
        WSPolicy policy = fuelCardService.getPolicy(request.getClientId(), request.getPolicyNumber());
        GetPolicyResponse resp = new GetPolicyResponse();
        resp.setResult(policy);
        return resp;
    }
}
