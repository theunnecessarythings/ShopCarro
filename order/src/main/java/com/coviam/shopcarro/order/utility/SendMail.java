package com.coviam.shopcarro.order.utility;

import com.coviam.shopcarro.order.details.Details;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SendMail {

    JavaMailSender javaMailSender;

    public void Sendemail(String email, List<Details> details,String subject){
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("shopcarroecommerce@gmail.com");
            mail.setTo(email);
            mail.setSubject(subject);
            String emailText = "Thanks for ordering from ShhoppCarro .Your product";
            if(details.size() == 1){
                emailText = emailText+" is ";
            }
            else{
                emailText = emailText+" are ";
            }

            emailText = emailText + details.toString();

            for(Details details1:details){
                String getProductName = "http://10.177.1.239:8080/get-product-name/?productId"+details1.getId();
                RestTemplate restTemplate1= new RestTemplate();
                String productName;
                productName = restTemplate1.getForObject(getProductName,String.class);
                emailText = emailText + " " + productName;
            }
            emailText = emailText + "\n Thanks for placing the Order";
            mail.setText(emailText);
            javaMailSender.send(mail);
        }
}
