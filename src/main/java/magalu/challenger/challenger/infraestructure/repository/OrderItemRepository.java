package magalu.challenger.challenger.infraestructure.repository;

import magalu.challenger.challenger.domain.entity.OrderItem;
import magalu.challenger.challenger.domain.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
