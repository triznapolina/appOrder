package org.example.apporders.repositories;


import org.example.apporders.models.Order;
import org.example.apporders.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order orderToUpdate);
}
