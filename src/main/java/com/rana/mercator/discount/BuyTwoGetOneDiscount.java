package com.rana.mercator.discount;

import com.rana.mercator.Basket;
import com.rana.mercator.Item;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class BuyTwoGetOneDiscount implements DiscountStrategy {
  private final Item item;

  @Override
  public BigDecimal getDiscountPrice(Basket basket) {
    var quantity = basket.getItems().getOrDefault(item.name().toLowerCase(), 0);
    if (quantity == 0) return BigDecimal.ZERO;
    return item.price().multiply(BigDecimal.valueOf(quantity / 3));
  }
}
