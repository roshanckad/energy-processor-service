package com.practiv.nz.service;

import com.practiv.nz.client.CleanEnergyApiClient;
import com.practiv.nz.client.CoalFiredApiClient;
import com.practiv.nz.config.RateProportionConfig;
import com.practiv.nz.exception.InvalidDataException;
import com.practiv.nz.exception.RateCalculateException;
import com.practiv.nz.model.CleanEnergyRate;
import com.practiv.nz.model.Price;
import com.practiv.nz.model.ProductOfferingPrice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class BillCalculatorServiceImpl implements BillCalculatorService {

  @Autowired
  private CoalFiredApiClient coalFiredApiClient;
  @Autowired
  private CleanEnergyApiClient cleanEnergyApiClient;
  @Autowired
  private RateProportionConfig rateProportionConfig;

  @Override
  public Optional<Price> getEstimatedBill(String userName, String quantity) {

    if (!NumberUtils.isCreatable(quantity) || Float.compare(Float.parseFloat(quantity) - 0, 0.01f) < 0) {
      throw new InvalidDataException("Invalid quantity");
    }

    String BLOCK_265_OFFER_PRICE = "block-265-offer-price";
    String COALFIRED_FIELDS = "id,price,unitOfMeasure";

    ProductOfferingPrice coalFiredEnergyRate = coalFiredApiClient.getRate(BLOCK_265_OFFER_PRICE, COALFIRED_FIELDS);
    CleanEnergyRate cleanEnergyRate = cleanEnergyApiClient.getRate(getNextMonthOrYear("MONTH"), getNextMonthOrYear("YEAR"));

    float calculatedRate = calculatedRate(coalFiredEnergyRate, cleanEnergyRate, quantity);

    log.info("Estimated rate for user {} is {}", userName, calculatedRate);

    return Optional.of(Price.builder()
      .unit(coalFiredEnergyRate.getPrice().getUnit())
      .value(calculatedRate)
      .build());
  }

  private int getNextMonthOrYear(String type) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
      calendar.set(Calendar.MONTH, Calendar.JANUARY);
      calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
    } else {
      calendar.roll(Calendar.MONTH, true);
    }
    if ("MONTH".equals(type)) {
      return calendar.get(Calendar.MONTH) + 1;
    } else {
      return calendar.get(Calendar.YEAR);
    }
  }

  private float calculatedRate(ProductOfferingPrice coalFiredEnergyRate, CleanEnergyRate cleanEnergyRate, String quantity) {
    try {
      float coalFiredEnergyRateValuePerUnit = coalFiredEnergyRate.getPrice().getValue() / 265;
      float cleanEnergyRateValuePerUnit = cleanEnergyRate.getPrice().getValue();

      return Float.parseFloat(
        new DecimalFormat("##.00")
          .format((Float.parseFloat(quantity) * rateProportionConfig.getClean() * cleanEnergyRateValuePerUnit)
            + (Float.parseFloat(quantity) * rateProportionConfig.getColaFired() * coalFiredEnergyRateValuePerUnit))
      );
    } catch (NumberFormatException e) {
      throw new RateCalculateException("Unable to calculate rate");
    }
  }
}
