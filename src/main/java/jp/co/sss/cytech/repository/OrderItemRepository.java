package jp.co.sss.cytech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}