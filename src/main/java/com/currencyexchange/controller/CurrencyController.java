package com.currencyexchange.controller;

import com.currencyexchange.api.CurrencyApi;
import com.currencyexchange.model.ExchangeRateDTO;
import com.currencyexchange.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency")
public class CurrencyController implements CurrencyApi {

  @Autowired CurrencyService service;

  @Override
  @GetMapping("")
  @Operation(
      summary = "Retrieves all actual currency exchange rates",
      description = "Retrieve a list of all available currency exchange rates.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of currency exchange rates returned successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                """
                                                   [
                                                                 {
                                                                     "currency": "AUD",
                                                                     "rate": 1.56
                                                                 },
                                                                 {
                                                                     "currency": "BGN",
                                                                     "rate": 1.83
                                                                 }
                                                   ]
                                                  """)))
      })
  public ResponseEntity<List<ExchangeRateDTO>> getAllRates() {
    return ResponseEntity.ok(service.getAllRates());
  }

  @Override
  @GetMapping("/{currencyCode}")
  @Operation(
      summary = "Retrieves actual currency exchange rate for a specific currency",
      description =
          "Retrieves actual currency exchange rate for a specific currency. Error thrown when no currency found")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Exchange rate returned successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                """
                                     {
                                      "currency": "HKD",
                                       "rate": 7.83
                                     }
                                          """))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                """
                            {
                            "timestamp": "2024-04-19T11:13:26.266+00:00",
                            "status": 500,
                            "error": "Internal Server Error",
                            "path": "/currency/aus"
                            }
                          """)))
      })
  public ResponseEntity<ExchangeRateDTO> getSingleRate(
      @Parameter(
              description = "Currency code. Examples: GBP, AUD. Case insensitive",
              schema = @Schema(type = "string"))
          @PathVariable("currencyCode")
          String currencyCode) {
    return service
        .getSingleRate(currencyCode)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new RuntimeException(
                    String.format("Provided currency %s not found", currencyCode)));
  }

  @Override
  @PostMapping("")
  @Operation(
      summary = "Allows to add custom exchange rate to database",
      description =
          "Allows to add custom exchange rate to database. Error thrown when provided request is incorrect. All fields are mandatory")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Exchange rate successfully added",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                """
                                             {
                                              "currency": "HKD",
                                               "rate": 7.83
                                             }
                                                  """))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            value =
                                """
                                            {
                                            "timestamp": "2024-04-19T11:13:26.266+00:00",
                                            "status": 500,
                                            "error": "Internal Server Error",
                                            "path": "/currency"
                                            }
                                          """)))
      })
  public ResponseEntity<ExchangeRateDTO> saveRate(@RequestBody ExchangeRateDTO exchangeRateDTO) {
    return new ResponseEntity<>(service.saveRate(exchangeRateDTO), HttpStatus.CREATED);
  }
}
