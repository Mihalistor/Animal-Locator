package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.SMSSendService;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("smsSendService")
public class SMSSendServiceImpl implements SMSSendService {

    @Override
    public void sendSMS(String body, User user) {
        String mobileNumber = "385" + user.getPhone().substring(1);
        AuthMethod auth = new TokenAuthMethod("e1978460", "pBNh8dXfQfY1SKEt");
        NexmoClient client = new NexmoClient(auth);

        String smsBody="ANIMAL LOCATOR" + System.lineSeparator() + body + System.lineSeparator();

        TextMessage message = new TextMessage("Locator", mobileNumber, smsBody);

        try {
            SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(message);
        } catch (IOException | NexmoClientException e) {
            e.printStackTrace();
        }
    }
}
