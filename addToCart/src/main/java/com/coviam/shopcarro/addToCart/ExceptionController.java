package com.coviam.shopcarro.addToCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    //private static final Logger LOG = (Logger) LoggerFactory.getLogger(ExceptionController.class);

    @Autowired private MessageSource messageSource;
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> myCustomException(CustomException ex){
        //LOG.warning("Get Comand Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
