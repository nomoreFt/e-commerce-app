package com.nomoreft.ecommerce.order;

import com.nomoreft.ecommerce.customer.CustomerClient;
import com.nomoreft.ecommerce.exception.BusinessException;
import com.nomoreft.ecommerce.kafka.OrderConfirmation;
import com.nomoreft.ecommerce.kafka.OrderProducer;
import com.nomoreft.ecommerce.orderline.OrderLineRequest;
import com.nomoreft.ecommerce.orderline.OrderLineService;
import com.nomoreft.ecommerce.product.ProductClient;
import com.nomoreft.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        //check the customer -- OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: no Customer exists with the provided id : " + request.customerId()));

        //purchase the products --> product-ms
        var purchasedProduct = productClient.purchaseProducts(request.products());

        //persist order
        var order = orderRepository.save(mapper.toOrder(request));

        //persist order lines
        for(PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }
        //todo start payment process

        //send the order confirmation --> notification-ms(kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProduct
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No order found with the provided id : " + orderId));
    }
}
