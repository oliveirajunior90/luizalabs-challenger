package magalu.challenger.challenger.infraestructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import magalu.challenger.challenger.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findOrderById(Long id);

    Page<Order> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

}
