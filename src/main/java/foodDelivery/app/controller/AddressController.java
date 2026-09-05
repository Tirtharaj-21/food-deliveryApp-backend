package foodDelivery.app.controller;

import foodDelivery.app.dto.request.AddressRequest;
import foodDelivery.app.dto.response.AddressResponse;
import foodDelivery.app.dto.response.ApiResponse;
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
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;
    @PostMapping("/save/{userId}")
    public ResponseEntity<ApiResponse<Long>> createAddress(@PathVariable Long userId,
                                                         @Valid @RequestBody AddressRequest request) {

        Long addressId = addressService.createAddress(userId, request);
        ApiResponse<Long> response = ApiResponse.<Long>builder()
                .success(true)
                .data(addressId)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getUserAddresses(@PathVariable Long userId) {

        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }

    @GetMapping("/getAddressById/{userId}/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long userId,
                                                          @PathVariable Long addressId) {

        return ResponseEntity.ok(addressService.getAddressById(userId, addressId));
    }

    @PutMapping("/updateAddress/{userId}/{addressId}")
    public ResponseEntity<ApiResponse<String>> updateAddress(@PathVariable Long userId,
                                                         @PathVariable Long addressId,
                                                         @Valid @RequestBody AddressRequest request) {

        addressService.updateAddress(userId, addressId, request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .data("Address modified")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAddress/{userId}/{addressId}")
    public ResponseEntity<ApiResponse<String>> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {

        addressService.deleteAddress(userId, addressId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .data("Address deleted")
                .build();
        return ResponseEntity.ok(response);
    }
}
