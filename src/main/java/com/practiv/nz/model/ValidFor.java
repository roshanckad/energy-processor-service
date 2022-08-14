package com.practiv.nz.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValidFor {

  private String endDateTime;
  private String startDateTime;
}