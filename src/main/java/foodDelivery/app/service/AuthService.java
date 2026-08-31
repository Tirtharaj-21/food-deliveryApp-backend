package foodDelivery.app.service;

import foodDelivery.app.dto.request.LoginRequest;
import foodDelivery.app.dto.response.LoginResponse;
import foodDelivery.app.dto.request.RegisterRequest;
import foodDelivery.app.dto.response.UserResponse;
import foodDelivery.app.entity.User;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
