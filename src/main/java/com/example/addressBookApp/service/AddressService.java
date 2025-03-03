package com.example.addressBookApp.service;

import com.example.addressBookApp.dto.AddressDTO;
import com.example.addressBookApp.model.Address;
import com.example.addressBookApp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    // Convert Address to AddressDTO
    private AddressDTO convertToDTO(Address address) {
        return new AddressDTO(address.getName(), address.getPhone(), address.getEmail());
    }

    // Convert AddressDTO to Address Entity
    private Address convertToEntity(AddressDTO dto) {
        return new Address(null, dto.getName(), dto.getPhone(), dto.getEmail());
    }

    public List<AddressDTO> getAllAddresses() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AddressDTO> getAddressById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public AddressDTO saveAddress(AddressDTO addressDTO) {
        Address saved = repository.save(convertToEntity(addressDTO));
        return convertToDTO(saved);
    }

    public Optional<AddressDTO> updateAddress(Long id, AddressDTO newAddressDTO) {
        return repository.findById(id).map(existing -> {
            existing.setName(newAddressDTO.getName());
            existing.setPhone(newAddressDTO.getPhone());
            existing.setEmail(newAddressDTO.getEmail());
            return convertToDTO(repository.save(existing));
        });
    }

    public void deleteAddress(Long id) {
        repository.deleteById(id);
    }
}
