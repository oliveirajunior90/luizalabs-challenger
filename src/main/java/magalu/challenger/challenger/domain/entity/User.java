package magalu.challenger.challenger.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;

    private String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {

    }
}

