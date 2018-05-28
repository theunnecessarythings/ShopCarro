package com.coviam.shopcarro.order.services;

import com.coviam.shopcarro.order.details.Details;
import com.coviam.shopcarro.order.dto.OrderDto;
import com.coviam.shopcarro.order.model.HistoryProducts;
import com.coviam.shopcarro.order.model.Order;
import com.coviam.shopcarro.order.repository.OrderRepository;
import com.coviam.shopcarro.order.utility.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.coviam.shopcarro.order.utility.Urls.urlDecrementStock;
import static com.coviam.shopcarro.order.utility.Urls.urlGetAvailableMerchant;

/**
 *
 * @author: sandeepgupta
 *
 * */

@Service
public class OrderServices implements IOrderservices {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
     private JavaMailSender javaMailSender;

    public SendMail sendMail;
    public List<Details> productBuy(OrderDto orderDto){
        /**
         *
         *  List of orders that are present in the cart are in the OrderDto for that particular user.
         *
         * */

        List< Details > listOfProductsPurchased = new ArrayList<>();

        for (Details details:orderDto.getDetails()){

            /**
             *
             * get the stocks for a particular a particular product and merchant Id.
             *
             * @params: productId and merchantId,
             *
             *         Calling the merchant services to get the required availability
             *
             * */
            String urlGetAvailable = urlGetAvailableMerchant + details.getMerchantId()+"&productId="+details.getId();
            RestTemplate restTemplate= new RestTemplate();
            Boolean availability;
            availability = restTemplate.getForObject(urlGetAvailable,Boolean.class);

            if(availability){
                listOfProductsPurchased.add(details);
                /**
                 *  link to decrement the stock.
                 * */
                String decrementStock = urlDecrementStock+details.getMerchantId()+"&productId="+details.getId();
                RestTemplate restTemplate1= new RestTemplate();
                Boolean decremented;
                decremented = restTemplate1.getForObject(decrementStock,Boolean.class);
            }
        }
        Order order = new Order(orderDto.getEmail(),listOfProductsPurchased);
        orderRepository.save(order);
        System.out.println(order.getEmail());
        System.out.println(listOfProductsPurchased);
        Sendemail(order.getEmail(),listOfProductsPurchased,"Order Placed");
        return listOfProductsPurchased;
    }

    /**
     *
     *  Will be returning the history if present otherwise this will be returning the null value.
     *
     * */
    public OrderDto getHistory(String email) throws ParseException {
        if(!orderRepository.existsById(email)){
            //sendMessage(email);
            return null;
        }

        Optional<Order> order = Optional.of(new Order());
        order = orderRepository.findById(email);

        OrderDto orderDto = new OrderDto();
        orderDto.setEmail(order.get().getEmail());
        orderDto.setDetails(order.get().getDetails());
        return orderDto;
    }

    public void Sendemail(String email, List<Details> details,String subject){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("shopcarroecommerce@gmail.com");
        mail.setTo(email);
        mail.setSubject(subject);
       // String emailText = "Thanks for ordering from ShhoppCarro .Your product";
        String emailText = "Thanks for ordering from ShopCarro .Your";
        if(details.size() == 1){
            emailText = emailText+"product is ";
        }
        else{
            emailText = emailText+"products are ";
        }
        emailText = emailText + "\n";

        for(Details details1:details){
            String getProductName = "http://localhost:8080/get-product-name/?productId="+details1.getId();
            RestTemplate restTemplate1= new RestTemplate();
            String productName;
            productName = restTemplate1.getForObject(getProductName,String.class);
            emailText = emailText + " " + productName + "\n";
        }
        emailText = emailText + "\n Thanks for placing the Order";
        mail.setText(emailText);
        javaMailSender.send(mail);
    }

    public List<HistoryProducts> getHistoryOfUser(String email) throws ParseException{
        if(!orderRepository.existsById(email)){
            //sendMessage(email);
            return null;
        }
        Optional<Order> order = Optional.of(new Order());
        order = orderRepository.findById(email);
        List<Details> list = new ArrayList<>();
        list = order.get().getDetails();

        List<HistoryProducts> listHistory = new ArrayList<>();

        for(Details details:list){
            String getProductName = "http://localhost:8080/get-product-name/?productId="+details.getId();
            RestTemplate restTemplate1= new RestTemplate();
            String productName;
            productName = restTemplate1.getForObject(getProductName,String.class);
            HistoryProducts historyProducts = new HistoryProducts(details.getId(),details.getMerchantId(),productName);
            listHistory.add(historyProducts);
        }
        return listHistory;
    }
}