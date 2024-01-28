package jpabook.jpashop.exception;

public class NotEnoughStockExcption extends RuntimeException{

    public NotEnoughStockExcption() {
        super();
    }

    public NotEnoughStockExcption(String message) {
        super(message);
    }

    public NotEnoughStockExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockExcption(Throwable cause) {
        super(cause);
    }
}
