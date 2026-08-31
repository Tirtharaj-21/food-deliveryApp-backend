package foodDelivery.app.service;

import foodDelivery.app.dto.request.LoginRequest;
import foodDelivery.app.dto.response.LoginResponse;
import foodDelivery.app.dto.request.RegisterRequest;
import foodDelivery.app.dto.response.UserResponse;
import foodDelivery.app.entity.User;
import foodDelivery.app.enumerator.UserRole;
import foodDelivery.app.enumerator.UserStatus;
import foodDelivery.app.exception.BadRequestException;
import foodDelivery.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired JwtService jwtService;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(
                    "Email already registered"
            );
        }

        if (request.getPhone() != null &&
                userRepository.existsByPhone(request.getPhone())) {

            throw new BadRequestException(
                    "Phone number already registered"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .build();

        user = userRepository.save(user);

        return mapToResponse(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new BadRequestException(
                    "Invalid email or password"
            );
        }

        return LoginResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(null)
                .build();
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
    }
}

