package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncodeMessage implements Mode {

    //fourth is always equal to 6
    private byte hammingHelper(byte elem, int first, int second, int third) {
        byte partAnswer = elem;
        if ((getBit(partAnswer, first) + getBit(partAnswer, second) +
                getBit(partAnswer, third) + getBit(partAnswer, 6)) % 2 != 0) {
            partAnswer = setBit(partAnswer, 1, first);
        }

        return partAnswer;
    }

    private byte hamming(int first, int second, int third, int fourth) {
        byte answer = 0;

        answer = setBit(answer, first, 2);
        answer = setBit(answer, second, 4);
        answer = setBit(answer, third, 5);
        answer = setBit(answer, fourth, 6);

        answer = hammingHelper(answer, 0, 2, 4);
        answer = hammingHelper(answer, 1, 2, 5);
        answer = hammingHelper(answer, 3, 4, 5);

        return answer;
    }

    private byte hammingFirstPart(byte number) {
        return hamming(getBit(number, 0), getBit(number, 1), getBit(number, 2), getBit(number, 3));
    }

    private byte hammingSecondPart(byte number) {
        return hamming(getBit(number, 4), getBit(number, 5), getBit(number, 6), getBit(number, 7));
    }

    public byte[] getParity(byte[] mas) {
        byte[] parity = new byte[mas.length * 2];

        for (int i = 0; i < mas.length; i++) {
            parity[i * 2] = hammingFirstPart(mas[i]);
            parity[i * 2 + 1] = hammingSecondPart(mas[i]);
        }

        return parity;
    }


    @Override
    public void execute() {
        try (FileInputStream inputStream = new FileInputStream(send);
            FileOutputStream outputStream = new FileOutputStream(encoded)) {

            byte[] inp = inputStream.readAllBytes();
            byte[] out = getParity(inp);
            outputStream.write(out);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
