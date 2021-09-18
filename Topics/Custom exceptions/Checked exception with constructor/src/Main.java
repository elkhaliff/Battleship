import java.util.Scanner;

// Do not change the class
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Exception e = new Exception(sc.nextLine());
        Exception customException = new CustomException(e);

        System.out.println(customException.getMessage());
    }
}

class CustomException extends Exception {
    static final long serialVersionUID = Long.MIN_VALUE;
    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(Exception cause) {
        super(cause);
    }
}