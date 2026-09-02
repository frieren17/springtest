package jp.co.sss.cytech.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.cytech.entity.SalesItem;

public interface SalesItemRepository extends JpaRepository<SalesItem, Integer> {

    List<SalesItem> findByStartMonthLessThanEqualAndEndMonthGreaterThanEqual(
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
