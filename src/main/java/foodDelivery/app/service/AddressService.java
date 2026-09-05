package foodDelivery.app.service;

import foodDelivery.app.dto.request.AddressRequest;
import foodDelivery.app.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    Long createAddress(Long userId, AddressRequest request);

    List<AddressResponse> getUserAddresses(Long userId);

    AddressResponse getAddressById(Long userId, Long addressId);

    void updateAddress(Long userId, Long addressId, AddressRequest request);

    void deleteAddress(Long userId, Long addressId);
}