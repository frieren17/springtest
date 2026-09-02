package jp.co.sss.cytech.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_items")
public class SalesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_item_id")
    private Integer saleItemId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "sale_name")
    private String saleName;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_rate")
    private Integer discountRate;

    @Column(name = "sales_img_path")
    private String salesImgPath;

    @Column(name = "start_month")
    private LocalDateTime startMonth;

    @Column(name = "end_month")
    private LocalDateTime endMonth;


    public Integer getSaleItemId() {
        return saleItemId;
    }

    public void setSaleItemId(Integer saleItemId) {
        this.saleItemId = saleItemId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }

    public String getSalesImgPath() {
        return salesImgPath;
    }

    public void setSalesImgPath(String salesImgPath) {
        this.salesImgPath = salesImgPath;
    }

    public LocalDateTime getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(LocalDateTime startMonth) {
        this.startMonth = startMonth;
    }

    public LocalDateTime getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(LocalDateTime endMonth) {
        this.endMonth = endMonth;
    }
}
