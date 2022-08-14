package com.practiv.nz.service;

import com.practiv.nz.model.Price;

import java.util.Optional;

public interface BillCalculatorService {

  Optional<Price> getEstimatedBill(String userName, String quantity);
}
