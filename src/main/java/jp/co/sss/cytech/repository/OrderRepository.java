package jp.co.sss.cytech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}