package com.microtech.microtech.dto.response.statistics;

import java.time.LocalDateTime;

public record ClientStatisticResponse(
        Long ordersNumber,
        double totalMoney,
        LocalDateTime firstOrderDate,
        LocalDateTime lastOrderDate
) {
}
