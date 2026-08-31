package foodDelivery.app.controller;

import foodDelivery.app.dto.request.AddressRequest;
import foodDelivery.app.dto.response.AddressResponse;
import foodDelivery.app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;
    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        addressService.createAddress(
                                userId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>>
    getUserAddresses(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                addressService.getUserAddresses(userId)
        );
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {

        return ResponseEntity.ok(
                addressService.getAddressById(
                        userId,
                        addressId
                )
        );
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request
    ) {

        return ResponseEntity.ok(
                addressService.updateAddress(
                        userId,
                        addressId,
                        request
                )
        );
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {

        addressService.deleteAddress(
                userId,
                addressId
        );

        return ResponseEntity.noContent().build();
    }
}
