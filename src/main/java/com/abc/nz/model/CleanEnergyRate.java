package com.abc.nz.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CleanEnergyRate {

  private String priceType;
  private String version;
  private Price price;
  private UnitOfMeasure unitOfMeasure;
  private ValidFor validFor;
}
