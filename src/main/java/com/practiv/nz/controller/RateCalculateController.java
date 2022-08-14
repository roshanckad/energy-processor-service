package com.practiv.nz.controller;

import com.practiv.nz.exception.RateCalculateException;
import com.practiv.nz.model.EstimatedBillResponse;
import com.practiv.nz.service.BillCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class RateCalculateController {

  @Autowired
  private BillCalculatorService billCalculatorService;

  @Operation(summary = "Get estimated bill")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Estimated bill",
      content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = EstimatedBillResponse.class))}),
    @ApiResponse(responseCode = "400", description = "Invalid input",
      content = @Content)})
  @GetMapping("/users/{user_name}/type/{type}/quantity/{quantity}/estamatedbill")
  public ResponseEntity<EstimatedBillResponse> calculateEstimatedBill(@PathVariable(value = "user_name") String userName
    , @PathVariable(value = "type") String type
    , @PathVariable(value = "quantity") String quantity) {

    return ResponseEntity.of(Optional.of(EstimatedBillResponse.builder()
      .price(billCalculatorService.getEstimatedBill(userName, type, quantity)
        .orElseThrow(() -> new RateCalculateException("Unable to calculate rate for " + userName)))
      .build()));
  }
}
