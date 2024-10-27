package com.nomoreft.ecommerce.customer;

import com.nomoreft.ecommerce.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = mapper.toCustomer(request);
        return customer
                .map(repository::save)
                .map(Customer::getId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot create customer:: request is null"));
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id());
        customer
                .ifPresentOrElse(
                        c -> {
                            mergeCustomer(c, request);
                            repository.save(c);
                        },
                        () -> {
                            throw new CustomerNotFoundException(
                                    format("Cannot update customer:: No customer found with ID %s", request.id())
                            );
                        }
                );
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        Optional.ofNullable(request.firstName()).filter(StringUtils::isNotBlank).ifPresent(customer::setFirstName);
        Optional.ofNullable(request.lastName()).filter(StringUtils::isNotBlank).ifPresent(customer::setLastName);
        Optional.ofNullable(request.email()).filter(StringUtils::isNotBlank).ifPresent(customer::setEmail);
        Optional.ofNullable(request.address()).ifPresent(customer::setAddress);
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream()
                .map(mapper::fromCustomer)
                .toList();
    }

    public Boolean existsById(String customerId) {
        return repository.existsById(customerId);
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot find customer:: No customer found with ID %s", customerId)
                ));

    }

    public void deleteById(String customerId) {
        repository.deleteById(customerId);
    }
}
