package com.example.addressBookApp.service;

import com.example.addressBookApp.model.Address;
import com.example.addressBookApp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public List<Address> getAllAddresses() {
        return repository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return repository.findById(id);
    }

    public Address saveAddress(Address address) {
        return repository.save(address);
    }

    public Address updateAddress(Long id, Address newAddress) {
        return repository.findById(id).map(existing -> {
            existing.setName(newAddress.getName());
            existing.setPhone(newAddress.getPhone());
            existing.setEmail(newAddress.getEmail());
            return repository.save(existing);
        }).orElse(null);
    }

    public void deleteAddress(Long id) {
        repository.deleteById(id);
    }
}
