package com.example.addressBookApp.service;

import com.example.addressBookApp.dto.AddressDTO;
import com.example.addressBookApp.model.Address;
import com.example.addressBookApp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final List<Address> addressList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // Simulating auto-increment ID

    // Convert Address to AddressDTO
    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(address.getName(), address.getPhone(), address.getEmail());
    }

    // Convert AddressDTO to Address Entity
    private Address convertToEntity(AddressDTO dto) {
        return new Address(null, dto.getName(), dto.getPhone(), dto.getEmail());
    }

    // Get all addresses
    public List<AddressDTO> getAllAddresses() {
        return addressList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get address by ID
    public Optional<AddressDTO> getAddressById(Long id) {
        return addressList.stream()
                .filter(address -> address.getId().equals(id))
                .findFirst()
                .map(this::convertToDTO);
    }

    // Save new address
    public AddressDTO saveAddress(AddressDTO addressDTO) {
        Address newAddress = convertToEntity(addressDTO);
        addressList.add(newAddress);
        return convertToDTO(newAddress);
    }

    // Update an existing address
    public Optional<AddressDTO> updateAddress(Long id, AddressDTO newAddressDTO) {
        for (Address address : addressList) {
            if (address.getId().equals(id)) {
                address.setName(newAddressDTO.getName());
                address.setPhone(newAddressDTO.getPhone());
                address.setEmail(newAddressDTO.getEmail());
                return Optional.of(convertToDTO(address));
            }
        }
        return Optional.empty();
    }

    // Delete an address by ID
    public boolean deleteAddress(Long id) {
        return addressList.removeIf(address -> address.getId().equals(id));
    }
}
