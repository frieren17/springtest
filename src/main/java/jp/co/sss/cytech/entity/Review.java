package jp.co.sss.cytech.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    // 商品との紐付け
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // ユーザーとの紐付け
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String dummyUserName;

    // 評価
    private int rating;
    
    @Column
    private String contactEmail;

    // コメント
    private String comment;

    @Column(name = "review_img_path")
    private String reviewImgPath;
    
    // 投稿日時
    private LocalDateTime createdAt;

    // getter setter
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getDummyUserName() {
    	return dummyUserName;
    }
    
    public void setDummyUserName(String dummyUserName) {
    	this.dummyUserName = dummyUserName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }    

    public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getReviewImgPath() {
    	return reviewImgPath;
    }
    
    public void setReviewImgPath(String reviewImgPath) {
    	this.reviewImgPath = reviewImgPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}