package com.arokia.message.functions;

import com.arokia.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email() {
        /*
          AccountsMsgDto - Input Data
          AccountsMsgDto - Output Data, reason for same data type is when email is called we want to call sms as well.
          Function email has to give input data to Function sms hence we are using same input and output data.
         */
        return accountsMsgDto -> {
            log.info("Sending email with the details : " +  accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto,Long> sms() {
        return accountsMsgDto -> {
            log.info("Sending sms with the details : " +  accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
            /*
             getter method of record class will have getter method as accountNumber instead of getAccountNumber and
             accountNumber data type is long which is same as return type in Function<AccountsMsgDto,Long>.
             */
        };
    }

}
