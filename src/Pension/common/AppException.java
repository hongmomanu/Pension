package Pension.common;


public class AppException extends Exception {
    Throwable throwable;

    public AppException(String paramString) {
        super(paramString);
    }

    public AppException(String paramString, Exception paramException) {
        super(paramString);
        this.throwable = paramException;
    }

    public String getDetailMessage() {
        if (this.throwable != null)
            return this.throwable.getMessage();
        return null;
    }

    public String toString() {
        String str = getMessage();
        if (this.throwable != null)
            str = str + "," + this.throwable.getMessage();
        return str;
    }

    public void printStackTrace() {
        if (this.throwable != null)
            this.throwable.printStackTrace();
    }

    public Throwable getMyException() {
        return this.throwable;
    }

    public void setMyException(Throwable paramThrowable) {
        this.throwable = paramThrowable;
    }
}