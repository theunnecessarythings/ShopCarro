package com.coviam.shopcarro.search.exceptions;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.exceptions
 * @project search
 */
public class NoItemsMatchingDescriptionException extends Exception {
    public NoItemsMatchingDescriptionException(String message) {
        super(message);
    }
}
