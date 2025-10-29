package pet.project.efsemulate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pet.project.efsemulate.exception.UnauthorizedException;
import pet.project.efsemulate.model.entity.User;
import pet.project.efsemulate.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private static final Map<String, LocalDateTime> clientIdToExpirationDateMap = new ConcurrentHashMap<>();
    private static final Map<String, Long> clientIdToUserIdMap = new ConcurrentHashMap<>();

    private final UserRepository userRepository;

    public void validateClientId(String clientId) {
        LocalDateTime expirationDate = clientIdToExpirationDateMap.get(clientId);
        if (expirationDate == null || expirationDate.isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException();
        }
    }

    public String login(String username, String password) {
        User user = userRepository.findByNameAndPassword(username, password)
                .orElseThrow(() ->{
                    log.info("User with name {} and password {} is not found", username, password);
                    throw new UnauthorizedException();
                });
        String clientId = createClientId();
        clientIdToUserIdMap.put(clientId,user.getId());
        log.info("User {} successfully authenticated, client id {} was generated", user.getName(), clientId);
        return clientId;
    }

    public String createClientId() {
        String clientId = randomStringGenerator();
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);
        clientIdToExpirationDateMap.put(clientId, expirationDate);
        return clientId;
    }

    public void invalidateClientId(String clientId) {
        LocalDateTime remove = clientIdToExpirationDateMap.remove(clientId);
        if (remove != null){
            log.info("Client id {} was invalidated", clientId);
        } else {
            log.info("Client id {} failed to be invalidated", clientId);
        }
    }

    public Long getUserIdByClientId(String clientId){
        return clientIdToUserIdMap.get(clientId);
    }


    @Scheduled(cron = "* * 1 * * *")
    public void removeExpiredClientIds() {
        LocalDateTime now = LocalDateTime.now();
        clientIdToExpirationDateMap.entrySet().removeIf(entry -> {
            if(entry.getValue().isBefore(now)){
                clientIdToUserIdMap.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }

    private String randomStringGenerator() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
