package magalu.challenger.challenger.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private Long id;

    @ManyToOne
    private User user;

    private LocalDate purchaseDate;

    public Order(Long orderId, User user, LocalDate purchaseDate) {
        this.id = orderId;
        this.user = user;
        this.purchaseDate = purchaseDate;
    }

    public Order() {

    }
}

