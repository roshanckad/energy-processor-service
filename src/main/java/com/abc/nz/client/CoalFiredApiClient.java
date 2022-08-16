package com.abc.nz.client;

import com.abc.nz.model.ProductOfferingPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.service.coalfired.name}", url = "${feign.service.coalfired.url}")
public interface CoalFiredApiClient {

  @GetMapping(value = "/productOfferingPrice/{id}")
  ProductOfferingPrice getRate(@PathVariable("id") String id, @RequestParam("fields") String fields);
}
