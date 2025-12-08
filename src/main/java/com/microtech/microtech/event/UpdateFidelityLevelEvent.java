package com.microtech.microtech.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateFidelityLevelEvent {
    private final Long orderId;
}
