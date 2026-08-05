/**
 * 
 */
//package jp.co.sss.cytech.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import jp.co.sss.cytech.entity.Product;
//
///**
// * 
// */
//public interface ProductRepository extends JpaRepository<Product, Integer> {
//
//}

package jp.co.sss.cytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // 商品名の部分一致検索
    List<Product> findByProductNameContaining(String keyword);
    
    List<Product> findByCategoryId(Integer categoryId);
    
    List<Product> findByProductNameContainingAndCategoryId(String keyword, Integer categoryId);

}