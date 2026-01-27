package com.rana.mercator;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Item(String name, BigDecimal price) {
}
