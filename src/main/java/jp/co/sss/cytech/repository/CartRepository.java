package jp.co.sss.cytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUser_UserId(Integer userId);

}