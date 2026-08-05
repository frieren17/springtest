package jp.co.sss.cytech.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jp.co.sss.cytech.dto.ReviewRequest;
import jp.co.sss.cytech.entity.Product;
import jp.co.sss.cytech.entity.Review;
import jp.co.sss.cytech.entity.User;
import jp.co.sss.cytech.repository.ProductRepository;
import jp.co.sss.cytech.repository.ReviewRepository;
import jp.co.sss.cytech.repository.UserRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Review createReview(ReviewRequest request) {

        // バリデーション
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("評価は1〜5");
        }

        // 存在チェック
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("ユーザーが存在しない"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("商品が存在しない"));

        // Entity生成
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setDummyUserName(request.getDummyUserName());
        review.setRating(request.getRating());
        review.setContactEmail(request.getContactEmail());
        review.setComment(request.getComment());
        review.setReviewImgPath(request.getReviewImgPath());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }
}