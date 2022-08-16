package com.abc.nz.service;

import com.abc.nz.model.Price;

import java.util.Optional;

public interface BillCalculatorService {

  Optional<Price> getEstimatedBill(String userName, String quantity);
}
