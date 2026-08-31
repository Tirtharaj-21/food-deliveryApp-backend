package foodDelivery.app.service;

import foodDelivery.app.dto.response.UserResponse;

public interface UserService {
    UserResponse getCurrentUser(String email);
}
