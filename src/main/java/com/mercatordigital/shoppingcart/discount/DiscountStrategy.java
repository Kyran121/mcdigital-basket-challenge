package com.mercatordigital.shoppingcart.discount;

import com.mercatordigital.shoppingcart.Basket;

import java.math.BigDecimal;

public interface DiscountStrategy {
  BigDecimal getDiscountPrice(Basket basket);
}