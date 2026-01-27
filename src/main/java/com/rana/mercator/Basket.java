package com.rana.mercator;

import com.rana.mercator.discount.DiscountStrategy;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Basket {
  private final Map<String, Item> itemLookup;
  private final Map<String, Integer> itemCounts = new HashMap<>();
  private final List<DiscountStrategy> offers = new ArrayList<>();

  private BigDecimal totalCost = BigDecimal.ZERO;

  public Basket(Map<String, Item> itemLookup) {
    this.itemLookup = itemLookup;
  }

  public void addItems(String... items) {
    for (var item : items) {
      String name = item.toLowerCase();
      if (!this.itemLookup.containsKey(name)) {
        throw new IllegalArgumentException("Item not found: " + item);
      }
      itemCounts.put(name, itemCounts.getOrDefault(name, 0) + 1);
      totalCost = totalCost.add(this.itemLookup.get(name).price());
    }
  }

  public Map<String, Integer> getItems() {
    return itemCounts;
  }

  public void applyOffers(DiscountStrategy... strategies) {
    this.offers.addAll(List.of(strategies));
  }

  public BigDecimal getTotalCost() {
    return totalCost.subtract(
      offers.stream().map(offer ->
        offer.getDiscountPrice(this))
          .reduce(BigDecimal.ZERO, BigDecimal::add));
  }
}
