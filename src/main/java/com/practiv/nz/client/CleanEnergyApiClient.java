package com.practiv.nz.client;

import com.practiv.nz.model.CleanEnergyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.service.cleanenergy.name}", url = "${feign.service.cleanenergy.url}")
public interface CleanEnergyApiClient {

  @GetMapping(value = "/v1/offeringPrices/monthlyRates/{month}/year/{year}")
  CleanEnergyRate getRate(@PathVariable("month") int month, @RequestParam("year") int year);
}
