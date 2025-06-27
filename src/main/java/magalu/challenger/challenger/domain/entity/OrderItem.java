package magalu.challenger.challenger.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.math.BigDecimal;

@Entity
@IdClass(OrderItemId.class)
public class OrderItem {
    @Id
    private Long orderId;

    @Id
    private Long productId;

    private BigDecimal productValue;

    public OrderItem(Long orderId, Long productId, BigDecimal productValue) {
        this.orderId = orderId;
        this.productId = productId;
        this.productValue = productValue;
    }

    public OrderItem() {

    }
}

