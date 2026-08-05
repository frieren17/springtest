package jp.co.sss.cytech.dto;

public class ReviewRequest {
	private Integer  userId;
    private Integer productId;
    private String dummyUserName;
    private int rating;
    private String contactEmail;
    private String comment;
    private String reviewImgPath;

    // getter/setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
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
}
