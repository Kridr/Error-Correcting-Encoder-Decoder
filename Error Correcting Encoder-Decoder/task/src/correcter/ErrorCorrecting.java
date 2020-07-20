package correcter;

import java.util.Scanner;

public class ErrorCorrecting {
    private Mode setMode(String mode) {
        switch (mode) {
            case "encode":
                return new EncodeMessage();
            case "send":
                return new SendMessage();
            case "decode":
                return new DecodeMessage();
            default:
                return null;
        }
    }

    public void process() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Write a mode: ");
        String mode = scanner.nextLine();

        setMode(mode).execute();
    }
}
