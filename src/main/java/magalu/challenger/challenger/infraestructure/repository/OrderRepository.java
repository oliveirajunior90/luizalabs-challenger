package magalu.challenger.challenger.infraestructure.repository;

import org.springframework.data.domain.Sort;
import magalu.challenger.challenger.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findOrderById(Long id);

    List<Order> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate, Sort sort);

}
