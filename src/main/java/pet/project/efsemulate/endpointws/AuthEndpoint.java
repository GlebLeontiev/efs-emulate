package pet.project.efsemulate.endpointws;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pet.project.efsemulate.model.dto.LoginRequest;
import pet.project.efsemulate.model.dto.LoginResponse;
import pet.project.efsemulate.model.dto.LogoutRequest;
import pet.project.efsemulate.model.dto.LogoutResponse;
import pet.project.efsemulate.service.AuthService;

@Endpoint
@RequiredArgsConstructor
public class AuthEndpoint {

    private static final String NS = "http://com.tch.cards.service";

    private final AuthService authService;

    @PayloadRoot(namespace = NS, localPart = "login")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {
        String token = authService.login(request.getUser(), request.getPassword());
        LoginResponse resp = new LoginResponse();
        resp.setResult(token);
        return resp;
    }

    @PayloadRoot(namespace = NS, localPart = "logout")
    @ResponsePayload
    public LogoutResponse logout(@RequestPayload LogoutRequest request) {
        authService.invalidateClientId(request.getClientId());
        return new LogoutResponse();
    }
}
