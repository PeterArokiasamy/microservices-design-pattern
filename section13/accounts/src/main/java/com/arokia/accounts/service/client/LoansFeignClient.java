package com.arokia.accounts.service.client;

import com.arokia.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient("loans") //name should be what is registered inside eureka server
@FeignClient(name="loans",fallback = LoansFallback.class) //Implement circuitbreaker with fallback
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch",consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("arokiabank-correlation-id")
                                                     String correlationId, @RequestParam String mobileNumber);

}
