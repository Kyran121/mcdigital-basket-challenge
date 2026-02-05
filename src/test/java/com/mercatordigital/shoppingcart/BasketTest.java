package com.mercatordigital.shoppingcart;

import com.mercatordigital.shoppingcart.discount.BuyOneGetOneDiscount;
import com.mercatordigital.shoppingcart.discount.BuyTwoGetOneDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class BasketTest {

  public static final Item APPLE = Item.builder().name("Apple").price(BigDecimal.valueOf(0.60)).build();
  public static final Item ORANGE = Item.builder().name("Orange").price(BigDecimal.valueOf(0.25)).build();

  private Basket basket;

  @BeforeEach
  void setUp() {
    basket = new Basket(Map.of("apple", APPLE, "orange", ORANGE));
  }

  @Test
  void addItems() {
    assertThatNoException().isThrownBy(() -> basket.addItems("apple", "orange", "Apple"));
    assertThatException().isThrownBy(() -> basket.addItems("cherry"));
  }

  @ParameterizedTest
  @MethodSource
  void getTotalCost(String[] items, BigDecimal expectedCost) {
    basket.addItems(items);
    assertThat(basket.getTotalCost()).isEqualTo(expectedCost);
  }

  private static Stream<Arguments> getTotalCost() {
    return Stream.of(
      Arguments.of(new String[]{}, BigDecimal.ZERO),
      Arguments.of(new String[]{"apple", "orange"}, BigDecimal.valueOf(0.85)),
      Arguments.of(new String[]{"apple", "apple"}, BigDecimal.valueOf(1.20)),
      Arguments.of(new String[]{"apple", "apple", "orange", "apple"}, BigDecimal.valueOf(2.05))
    );
  }

  @Test
  void getItems() {
    basket.addItems("apple", "apple", "orange");
    assertThat(basket.getItems()).containsExactlyInAnyOrderEntriesOf(Map.of(
      "apple", 2,
      "orange", 1
    ));
  }

  @Test
  void getTotalCostWithDiscount() {
    basket.applyOffers(new BuyOneGetOneDiscount(APPLE), new BuyTwoGetOneDiscount(ORANGE));
    basket.addItems("apple", "apple", "apple", "orange", "orange", "orange");
    assertThat(basket.getTotalCost()).isEqualByComparingTo(BigDecimal.valueOf(1.70));
  }
}