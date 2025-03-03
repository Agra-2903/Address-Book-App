package com.example.addressBookApp.controller;

import com.example.addressBookApp.model.Address;
import com.example.addressBookApp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    // GET all addresses
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(service.getAllAddresses());
    }

    // GET address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> address = service.getAddressById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST new address
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        return ResponseEntity.ok(service.saveAddress(address));
    }

    // PUT update address by ID
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address newAddress) {
        Address updated = service.updateAddress(id, newAddress);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // DELETE address by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        service.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
