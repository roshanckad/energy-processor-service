package com.practiv.nz.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductOfferingPrice {

  private String id;
  private Price price;
  private UnitOfMeasure unitOfMeasure;
}
