package com.abc.nz.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Price {

  private String unit;
  private Float value;
}
