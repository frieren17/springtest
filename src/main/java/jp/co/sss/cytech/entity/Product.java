package jp.co.sss.cytech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "tax_price")
    private Integer taxPrice;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "comment")
    private String comment;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // getter / setter

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Integer taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}

//package jp.co.sss.cytech.entity;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "products")
//
//public class Product {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer productId;
//    private String productName;
//    private Integer price;
//    private Integer taxPrice;
//    private Integer stock;
//    private String comment;
//    private String imgPath;
//    private Integer categoryId;
//    
//    @ManyToOne
//    @JoinColumn(name = "company_id")
//    private Company company;
//    
//    // getter / setter
//    public Integer getProductId() { 
//    	return productId; 
//    }
//    public void setProductId(Integer productId) { 
//    	this.productId = productId; 
//    }
//    public String getProductName() { 
//    	return productName; 
//    }
//    public void setProductName(String productName) { 
//    	this.productName = productName; 
//    }
//    public Integer getPrice() { 
//    	return price; 
//    }
//    public void setPrice(Integer price) { 
//    	this.price = price; 
//    }
//    public Integer getTaxPrice() {
//        return taxPrice;
//    }
//
//    public void setTaxPrice(Integer taxPrice) {
//        this.taxPrice = taxPrice;
//    }
//    public Integer getStock() { 
//    	return stock; 
//    }
//    public void setStock(Integer stock) { 
//    	this.stock = stock; 
//    }
//    public String getComment() { 
//    	return comment; 
//    }
//    public void setComment(String comment) { 
//    	this.comment = comment; 
//    }
//    public String getImgPath() { 
//    	return imgPath; 
//    }
//    public void setImgPath(String imgPath) { 
//    	this.imgPath = imgPath; 
//    }
//    public Company getCompany() {
//        return company;
//    }
//
//    public void setCompany(Company company) {
//        this.company = company;
//    }
//    public Integer getCategoryId() { 
//    	return categoryId; 
//    }
//    public void setCategoryId(Integer categoryId) { 
//    	this.categoryId = categoryId; 
//    }
//    
//    
//}
