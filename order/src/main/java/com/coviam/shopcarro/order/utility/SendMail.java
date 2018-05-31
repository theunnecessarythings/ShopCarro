package com.coviam.shopcarro.order.utility;

import com.coviam.shopcarro.order.details.Details;
import com.coviam.shopcarro.order.details.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.coviam.shopcarro.order.utility.Urls.urlgetProductName;

public class SendMail {

    /**
     * @params:
     * To: email
     * Details of the product [merchantId, productId]
     * Subject of the mail
     * */
    @Autowired
    JavaMailSender javaMailSender;
    /**
     * Mail for sending the images with mail using javaMailSender using MimeMessage
     * */

    public void SendingemailD(String email, List<OrderDetails> details, String subject) throws MessagingException {
        System.out.println("Sending mail ");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        String emailText = "Thanks for ordering from ShopCarro. Your ";
        if(details.size() == 1){
            emailText = emailText+"product is <br> </br>";
        }
        else{
            emailText = emailText+"products are <br> </br>";
        }
        emailText = emailText + "\n";

        Long amountPayable= Long.valueOf(0);
        Integer count = 0;
        for(OrderDetails orderDetails: details){
            emailText = emailText + (count+1) + ". ";
            emailText = emailText + orderDetails.getProductName() + " by Merchant:" + orderDetails.getMerchantName() + " With Quantity " + orderDetails.getQuantity() + " at price "+ orderDetails.getPrice() + " each." + "\n";
            String inlineImage = "<br></br><img src=\""+ orderDetails.getImageUrl()  + "\" width=\"100\" height=\"80\" ></img><br/>";
            emailText += inlineImage;
            count+=1;
            amountPayable += Long.valueOf(orderDetails.getPrice())*Long.valueOf(orderDetails.getQuantity());
        }

        emailText += "<br></br> Total amount payable is : Rs. " + amountPayable + "/- only <br></br>";

        emailText += "Thanks for ordering from ShopCarro. See you again.";

        helper.setText(emailText, true);
        helper.setSubject(subject);
        helper.setTo(email);
        helper.setFrom("shopcarroecommerce@gmail.com");
        javaMailSender.send(message);
        return;
    }
}
