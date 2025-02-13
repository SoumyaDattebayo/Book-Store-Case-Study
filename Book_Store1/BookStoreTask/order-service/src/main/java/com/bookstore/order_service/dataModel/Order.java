package com.bookstore.order_service.dataModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long customerId;

    @NotNull
    private Long bookId;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
