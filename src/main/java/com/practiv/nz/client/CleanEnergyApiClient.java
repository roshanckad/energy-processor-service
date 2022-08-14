package com.practiv.nz.client;

import com.practiv.nz.model.CleanEnergyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${feign.service.cleanenergy.name}", url = "${feign.service.cleanenergy.url}")
public interface CleanEnergyApiClient {

  @GetMapping(value = "/v1/offeringPrices/hourlyRates")
  CleanEnergyRate getRate();
}
