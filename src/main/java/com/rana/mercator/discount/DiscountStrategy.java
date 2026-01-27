package com.rana.mercator.discount;

import com.rana.mercator.Basket;

import java.math.BigDecimal;

public interface DiscountStrategy {
  BigDecimal getDiscountPrice(Basket basket);
}