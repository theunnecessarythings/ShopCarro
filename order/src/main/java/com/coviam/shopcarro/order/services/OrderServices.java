package com.coviam.shopcarro.order.services;

import com.coviam.shopcarro.order.controller.OrderController;
import com.coviam.shopcarro.order.details.MerchantProductDetails;
import com.coviam.shopcarro.order.details.OrderDetails;
import com.coviam.shopcarro.order.dto.OrderDto;
import com.coviam.shopcarro.order.model.Order;
import com.coviam.shopcarro.order.repository.OrderRepository;
import com.coviam.shopcarro.order.utility.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.coviam.shopcarro.order.utility.Urls.*;

/**
 * @author: sandeepgupta
 * */

@Service
public class OrderServices implements IOrderservices {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RestTemplate restTemplate;

    public SendMail sendMail;

    String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

    @Value("${urlGetAvailableMerchant}") private String urlOfGetAvailableMerchant;
    @Value("${urlDecrementStock}") private String urlOfDecrementStock;
    @Value("${urlDelCart}") private String urlOfDelCart;
    @Value("${urlMerchantProductDetails}") private String urlOfMerchantProductDetails;
    @Value("${urlProductImage}") private String urlOfProductImage;

    private static final Logger LOGGER = Logger.getLogger( OrderServices.class.getName() );

    public List<OrderDetails> productBuy(OrderDto orderDto){
        /**
         *  List of orders that are present in the cart are in the OrderDto for that particular user.
         * */
        List<OrderDetails> listOfProductsPurchased = new ArrayList<>();
        for (OrderDetails details:orderDto.getDetails()){

            /**
             *         get the stocks for a particular a particular product and merchant Id.
             *          @params: productId, merchantId and Quantity
             *         Calling the merchant services to get the required availability
             * */
            String urlGetAvailable = urlOfGetAvailableMerchant + details.getMerchantId()+"&productId="+details.getId()+"&quantity="+details.getQuantity();
            LOGGER.info(urlGetAvailable);
            //RestTemplate restTemplate= new RestTemplate();
            Boolean availability;
            availability = restTemplate.getForObject(urlGetAvailable,Boolean.class);

            if(availability){
                /**
                 * Getting date
                 * */
                String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

                OrderDetails orderDetails = new OrderDetails(details.getId(),details.getMerchantId(),details.getMerchantName(),details.getProductName(),details.getImageUrl(),timeStamp,details.getQuantity(),details.getPrice());

                listOfProductsPurchased.add(orderDetails);
                /**
                 *  link to decrement the stock.
                 * */
                String decrementStock = urlOfDecrementStock + details.getMerchantId()+"&productId="+details.getId()+"&quantity="+details.getQuantity();
                //RestTemplate restTemplate3= new RestTemplate();
                Boolean decremented;
                decremented = restTemplate.getForObject(decrementStock,Boolean.class);
            }
        }
        List <OrderDetails> listofProductsAlreadyPurchased = new ArrayList<>();
        if(orderRepository.existsById(orderDto.getEmail())){
            listofProductsAlreadyPurchased = orderRepository.findById(orderDto.getEmail()).get().getDetails();
            listofProductsAlreadyPurchased.addAll(listOfProductsPurchased);
        }
        Order order = new Order(orderDto.getEmail(),listofProductsAlreadyPurchased);
        orderRepository.save(order);
        LOGGER.info(order.getEmail());
        LOGGER.info(listOfProductsPurchased.toString());

        if(listOfProductsPurchased.size() > 0) {
            try {
                LOGGER.info("Sending email");
                SendMail sendMail = new SendMail();
                Sendingemail(order.getEmail(), listOfProductsPurchased, "Order Placed successfully");
            } catch (MessagingException e) {
                LOGGER.info(e.toString());
            }
        }

        String urlDelCart = urlOfDelCart + orderDto.getEmail();
        LOGGER.info("Deleting from cart " + urlDelCart);
        Boolean deletedFromCart = restTemplate.getForObject(urlDelCart,Boolean.class);
        return listOfProductsPurchased;
    }

    /**
     *  Mail for sending the Simple Text messages using JavaMailSender
     *  This function was used previously when we were sending the text messages to the user with only product names
     * */

    public void Sendemail(String email, List<OrderDetails> details,String subject){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("shopcarroecommerce@gmail.com");
        mail.setTo(email);
        mail.setSubject(subject);
       // String emailText = "Thanks for ordering from ShhoppCarro .Your product";
        String emailText = "Thanks for ordering from ShopCarro .Your ";
        if(details.size() == 1){
            emailText = emailText+"product is ";
        }
        else{
            emailText = emailText+"products are ";
        }
        emailText = emailText + "\n";

        Integer count = 0;
        for(OrderDetails orderDetails: details){
            emailText = emailText + (count+1) + " .";
            emailText = emailText + orderDetails.getProductName() + " by " + orderDetails.getMerchantId();
            String inlineImage = "<img src=\""+ orderDetails.getImageUrl()  + "\"></img><br/>";
            emailText += inlineImage;
            count+=1;
        }

        emailText = emailText + "\n Thanks for placing the Order";
        mail.setText(emailText);
        javaMailSender.send(mail);
    }

    /**
     *  Will be returning the history if present otherwise this will be returning the empty list.
     * */

    public List<OrderDetails> getHistoryOfUser(String email) throws ParseException{
        Optional<Order> order = Optional.of(new Order());
        order = orderRepository.findById(email);
        List<OrderDetails> list = new ArrayList<>();
        if(orderRepository.existsById(email)) {
            list = order.get().getDetails();
        }
        return list;
    }

    /**
     *  for single product purchase.
     * */

    public Long purchaseProduct(String email, String productId, String merchantId, String quantity, String productName){
        String urlGetAvailable = urlOfGetAvailableMerchant + merchantId +"&productId="+ productId +"&quantity="+ quantity;
        LOGGER.info(urlGetAvailable);
        Boolean availability;
        availability = restTemplate.getForObject(urlGetAvailable,Boolean.class);

        if(availability) {

            /**
             * fetching the merchantName and price from merchant server
             * */

            String urlMerchantProductDetail = urlOfMerchantProductDetails + merchantId + "&productId=" + productId + "&quantity=" + quantity;
            LOGGER.info(" Getting merchant product Details: " + urlMerchantProductDetail);
            MerchantProductDetails merchantProductDetails = restTemplate.getForObject(urlMerchantProductDetail, MerchantProductDetails.class);

            /**
             * fetching the image url from product server
             * */
            String getImageUrl = urlOfProductImage + productId;
            LOGGER.info("Getting image url " + getImageUrl);
            String imageUrl = restTemplate.getForObject(getImageUrl,String.class);

            /**
             * Getting date
             * */

            OrderDetails orderDetails = new OrderDetails(productId,merchantId,merchantProductDetails.getMerchantName(),productName,imageUrl,timeStamp,quantity,merchantProductDetails.getPrice());

            String decrementStock = urlOfDecrementStock + merchantId +"&productId="+ productId +"&quantity="+ quantity;
            Boolean decremented = restTemplate.getForObject(decrementStock,Boolean.class);

            List<OrderDetails> listOfProductsPurchased = new ArrayList<>();
            listOfProductsPurchased.add(orderDetails);

            List <OrderDetails> listofProductsAlreadyPurchased = new ArrayList<>();
            if(orderRepository.existsById(email)){
                listofProductsAlreadyPurchased = orderRepository.findById(email).get().getDetails();
                listofProductsAlreadyPurchased.addAll(listOfProductsPurchased);
            }
            Order order = new Order(email,listofProductsAlreadyPurchased);
            orderRepository.save(order);
            LOGGER.info(order.getEmail());
            //System.out.println(order.getEmail());
            LOGGER.info(listOfProductsPurchased.toString());
            try {
                LOGGER.info("Sending mail ");
                SendMail sendMail = new SendMail();
                Sendingemail(order.getEmail(),listOfProductsPurchased,"Order Placed successfully");
            } catch (MessagingException e) {
                LOGGER.info(e.toString());
            }
            return Long.valueOf(1);
        }
        return Long.valueOf(0);
    }

    public void Sendingemail(String email, List<OrderDetails> details, String subject) throws MessagingException {
        //System.out.println();
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
            String inlineImage = "<br></br><img src=\""+ orderDetails.getImageUrl()  + "\" width=\"100\" height=\"100\" ></img><br/>";
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