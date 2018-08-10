package ai.leverton.demo.exception;

public class NoSuchDriverException extends Exception {
    public NoSuchDriverException(String exception) {super(exception);}
    public NoSuchDriverException() {super();}
    public NoSuchDriverException(String exception, Throwable throwable) {
        super(exception, throwable);
    }
}
