package com.practiv.nz.service;

import com.practiv.nz.client.CleanEnergyApiClient;
import com.practiv.nz.client.CoalFiredApiClient;
import com.practiv.nz.model.CleanEnergyRate;
import com.practiv.nz.model.Price;
import com.practiv.nz.model.ProductOfferingPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillCalculatorService {

  @Autowired
  private CoalFiredApiClient coalFiredApiClient;
  @Autowired
  private CleanEnergyApiClient cleanEnergyApiClient;
  private final String BLOCK_265_OFFER_PRICE = "block-265-offer-price";
  private final String COALFIRED_FIELDS = "id,price,unitOfMeasure";

  public Price getEstimatedBill(String userName, String type, String quantity) {

    float calculatedRate = 0;

    ProductOfferingPrice coalFiredEnergyRate = coalFiredApiClient.getRate(BLOCK_265_OFFER_PRICE, COALFIRED_FIELDS);
    CleanEnergyRate cleanEnergyRate = cleanEnergyApiClient.getRate();

    calculatedRate = calculatedRate(coalFiredEnergyRate, cleanEnergyRate, quantity);

    log.info("{} estimated rate for user {} is {}", type, userName, calculatedRate);

    return Price.builder()
      .unit(coalFiredEnergyRate.getPrice().getUnit())
      .value(calculatedRate)
      .build();
  }

  private float calculatedRate(ProductOfferingPrice coalFiredEnergyRate, CleanEnergyRate cleanEnergyRate, String quantity) {
    float coalFiredEnergyRateValuePerUnit = coalFiredEnergyRate.getPrice().getValue() / 265;
    float cleanEnergyRateValuePerUnit = cleanEnergyRate.getPrice().getValue();

    return (float) ((Float.parseFloat(quantity) * 0.81 * cleanEnergyRateValuePerUnit)
      + (Float.parseFloat(quantity) * 0.19 * coalFiredEnergyRateValuePerUnit));
  }
}
