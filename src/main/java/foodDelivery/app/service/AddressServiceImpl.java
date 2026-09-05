package foodDelivery.app.service;

import foodDelivery.app.dto.request.AddressRequest;
import foodDelivery.app.dto.response.AddressResponse;
import foodDelivery.app.entity.Address;
import foodDelivery.app.entity.User;
import foodDelivery.app.exception.ResourceNotFoundException;
import foodDelivery.app.repository.AddressRepository;
import foodDelivery.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Long createAddress(Long userId, AddressRequest request) {

        User user = getUser(userId);

        Address address = Address.builder()
                .user(user)
                .label(request.getLabel())
                .addressLine(request.getAddressLine())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        Long Addressid = addressRepository.save(address).getId();
        return Addressid;
    }

    @Override
    public List<AddressResponse> getUserAddresses(Long userId) {

        getUser(userId);

        return addressRepository
                .findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AddressResponse getAddressById(Long userId, Long addressId) {

        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found")
                );

        return mapToResponse(address);
    }

    @Override
    public void updateAddress(Long userId, Long addressId, AddressRequest request) {

        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found")
                );

        address.setLabel(request.getLabel());
        address.setAddressLine(request.getAddressLine());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());
        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {

        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found")
                );

        addressRepository.delete(address);
    }

    private User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );
    }

    private AddressResponse mapToResponse(Address address) {

        return AddressResponse.builder()
                .id(address.getId())
                .label(address.getLabel())
                .addressLine(address.getAddressLine())
                .city(address.getCity())
                .state(address.getState())
                .pincode(address.getPincode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }
}
