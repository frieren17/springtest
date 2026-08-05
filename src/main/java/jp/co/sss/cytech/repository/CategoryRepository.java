package jp.co.sss.cytech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}