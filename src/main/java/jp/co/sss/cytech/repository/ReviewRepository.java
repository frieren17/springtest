package jp.co.sss.cytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
    List<Review> findByProduct_ProductId(Long productId);
    
}