package org.example.services;

import org.example.model.Address;
import org.example.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void newAddress(Address address) {

        Address newAddress = new Address();
        newAddress.setStreet(address.getStreet());
        newAddress.setHouseNo(address.getHouseNo());
        newAddress.setUserId(null);
        addressRepository.saveAndFlush(newAddress);

    }

    public void setUserToAddress(Long id, Long userId){

        Address address = addressRepository.findById(id).get();
        address.setUserId(userId);
        addressRepository.saveAndFlush(address);

    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }




}
