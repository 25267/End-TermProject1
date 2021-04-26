package org.example.controller;

import org.example.model.Address;
import org.example.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping()
    public void newAddress(@RequestBody Address address){
        addressService.newAddress(address);
    }

    @PatchMapping("/{addressId}")
    public void setAddressToUser(@PathVariable Long addressId, @RequestParam Long userId){
        addressService.setUserToAddress(addressId, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
    }

}
