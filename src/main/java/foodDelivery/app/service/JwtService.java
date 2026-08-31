package foodDelivery.app.service;

public interface JwtService {
    String generateToken(String email);

    String extractEmail(String token);
}
