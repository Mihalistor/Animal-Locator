package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.User;
import com.animal.locator.animalLocator.services.EmailSendService;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service("emailSendService")
public class EmailSendServiceImpl implements EmailSendService {

    @Override
    public void sendEmail(String subject, String body, User user) {
        try{
            final String fromEmail = "animal.locator.2018@gmail.com";
            final String password = "Animal2018";
            final String toEmail = user.getEmail();

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session sessionSSL = Session.getInstance(props);
            sessionSSL.setDebug(false);

            Message messageSSL = new MimeMessage(sessionSSL);
            messageSSL.setFrom(new InternetAddress("animal.locator.2018@gmail.com", "Animal locator"));
            messageSSL.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            messageSSL.setSubject(subject);
            messageSSL.setText(body);

            Transport transportSSL = sessionSSL.getTransport();

            transportSSL.connect("smtp.gmail.com", 587, fromEmail, password);
            transportSSL.sendMessage(messageSSL, messageSSL.getAllRecipients());
            transportSSL.close();

        }catch(Exception ex){
            System.out.println("Mail fail");
            System.out.println(ex);
        }
    }
}
