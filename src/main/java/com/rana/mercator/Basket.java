package com.rana.mercator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
public class Basket {
  private final Map<String, Item> itemLookup;

  @Getter
  private BigDecimal totalCost = BigDecimal.ZERO;

  public void addItems(String... items) {
    for (var item : items) {
      if (!this.itemLookup.containsKey(item.toLowerCase())) {
        throw new IllegalArgumentException("Item not found: " + item);
      }
      totalCost = totalCost.add(this.itemLookup.get(item.toLowerCase()).price());
    }
  }
}
