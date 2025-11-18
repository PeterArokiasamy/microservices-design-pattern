package com.arokia.accounts.functions;

import com.arokia.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunctions {

    private static final Logger log = LoggerFactory.getLogger(AccountsFunctions.class);

    /* Here we are going to receive event from Message MS, but no output hence use Consumer*/
    @Bean
    public Consumer<Long> updateCommunication(IAccountsService accountsService) {
        /* No need add @Autowired for IAccountsService, if the method is with @Bean
        then it will automatically inject arguments of the method */
        return accountNumber -> {
            log.info("Updating Communication status for the account number : " + accountNumber.toString());
            accountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
