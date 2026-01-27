package com.rana.mercator.discount;

import com.rana.mercator.Basket;
import com.rana.mercator.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BuyOneGetOneDiscountTest {

  public static final Item APPLE = Item.builder().name("Apple").price(BigDecimal.valueOf(0.60)).build();

  private Basket basket;
  private BuyOneGetOneDiscount strat;

  @BeforeEach
  void setup() {
    basket = new Basket(Map.of("apple", APPLE));
    strat = new BuyOneGetOneDiscount(APPLE);
  }

  @ParameterizedTest
  @MethodSource
  void getDiscountPrice(String[] items, BigDecimal expectedDiscount) {
    basket.addItems(items);
    assertThat(strat.getDiscountPrice(basket)).isEqualTo(expectedDiscount);
  }

  private static Stream<Arguments> getDiscountPrice() {
    return Stream.of(
      Arguments.of(new String[]{}, BigDecimal.ZERO),
      Arguments.of(new String[]{"apple", "apple"}, BigDecimal.valueOf(0.6)),
      Arguments.of(new String[]{"apple", "apple", "apple"}, BigDecimal.valueOf(0.6)),
      Arguments.of(new String[]{"apple", "apple", "apple", "apple"}, BigDecimal.valueOf(1.2))
    );
  }
}
