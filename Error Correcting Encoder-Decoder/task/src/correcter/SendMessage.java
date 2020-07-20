package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class SendMessage implements Mode {

    private byte[] networkError(byte[] mas) {
        byte[] masWithErrors = new byte[mas.length];

        Random random = new Random();

        for (int i = 0; i < mas.length; i++) {
            int position = random.nextInt(7);
            masWithErrors[i] = reverseBit(mas[i], position);
        }

        return masWithErrors;
    }

    @Override
    public void execute() {
        try (FileInputStream inputStream = new FileInputStream(encoded);
             FileOutputStream outputStream = new FileOutputStream(received)) {

            byte[] inp = inputStream.readAllBytes();
            byte[] inpWithErrors = networkError(inp);
            outputStream.write(inpWithErrors);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
