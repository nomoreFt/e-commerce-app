package com.nomoreft.ecommerce.customer;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerMapper {
    public Optional<Customer> toCustomer(CustomerRequest request) {
        return Optional.ofNullable(request)
                .map(req -> Customer.builder()
                        .id(req.id())
                        .firstName(req.firstName())
                        .lastName(req.lastName())
                        .email(req.email())
                        .address(req.address())
                        .build());
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
