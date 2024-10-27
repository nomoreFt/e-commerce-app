package com.nomoreft.ecommerce.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid @NotNull CustomerRequest request
    ) {
        String customerId = service.createCustomer(request);
        return ResponseEntity.ok(customerId);  // 성공 시 200 OK와 함께 고객 ID 반환
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid @NotNull CustomerRequest request
    ) {
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();  // 성공 시 202 Accepted 반환
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(service.existsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(service.findById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable("customer-id") String customerId
    ) {
        service.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }


}
