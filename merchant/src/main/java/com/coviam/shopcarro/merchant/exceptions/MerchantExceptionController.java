package com.coviam.shopcarro.merchant.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.exceptions
 * @project merchant
 */

@RestControllerAdvice
public class MerchantExceptionController {
    private static final Logger LOG = LoggerFactory.getLogger(MerchantExceptionController.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> employeeIdNotFoundException(IdNotFoundException ex) {
        LOG.warn("Id Not Found Exception", ex);
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }

    @ExceptionHandler(IdAlreadyExistsException.class)
    public ResponseEntity<String> idAlreadyExistsException(IdAlreadyExistsException ex) {
        LOG.warn("Id Already Exists Exception", ex);
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException ex) {
        LOG.warn("Wrong parameters sent", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
