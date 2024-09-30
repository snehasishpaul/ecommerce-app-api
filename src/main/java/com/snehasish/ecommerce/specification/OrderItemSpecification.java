package com.snehasish.ecommerce.specification;

import com.snehasish.ecommerce.entity.OrderItem;
import com.snehasish.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

public class OrderItemSpecification {

    /** Specification to filter orderitems by status */
    public static Specification<OrderItem> hasStatus(OrderStatus status) {
        return (root, query, criteriaBuilder) -> status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
    }

    /** Specification to filter orderitems by date range */
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            } else {
                return null;
            }
        };
    }

    /** Specification to filter orderitems by item id */
    public static Specification<OrderItem> hasItemId(Long itemId) {
        return (root, query, criteriaBuilder) -> itemId != null ? criteriaBuilder.equal(root.get("id"), itemId) : null;
    }

}
