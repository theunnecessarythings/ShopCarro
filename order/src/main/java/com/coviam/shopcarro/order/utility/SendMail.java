package com.coviam.shopcarro.order.utility;

import com.coviam.shopcarro.order.details.Details;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.coviam.shopcarro.order.utility.Urls.urlgetProductName;

public class SendMail {

    /**
     * @params:
     * To: email
     * Details of the product [merchantId, productId]
     * Subject of the mail
     * */

    JavaMailSender javaMailSender;
    public void Sendemailc(String email, List<Details> details,String subject){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("shopcarroecommerce@gmail.com");
        mail.setTo(email);
        mail.setSubject(subject);
        String emailText = "Thanks for ordering from ShopCarro .Your";
        if(details.size() == 1){
            emailText = emailText+"product is ";
        }
        else{
            emailText = emailText+"products are ";
        }
        emailText = emailText + "\n";
        for(Details details1:details){
            String getProductName = urlgetProductName+details1.getId();
            RestTemplate restTemplate1= new RestTemplate();
            String productName;
            productName = restTemplate1.getForObject(getProductName,String.class);
            emailText = emailText + " " + productName + "\n";
        }
        emailText = emailText + "\n Thanks for placing the Order";
        mail.setText(emailText);
        javaMailSender.send(mail);
    }
}
