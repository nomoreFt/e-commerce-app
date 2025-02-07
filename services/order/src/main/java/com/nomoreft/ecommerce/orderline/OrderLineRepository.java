package com.nomoreft.ecommerce.orderline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {


    Collection<OrderLine> findAllByOrderId(Integer orderId);

}
