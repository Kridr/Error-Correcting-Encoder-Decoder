package correcter;

import java.io.*;

public class DecodeMessage implements Mode {

    //fourth is always equal to 6
    private int correctSingleHelper(byte elem, int first, int second, int third) {
        return (getBit(elem, first) + getBit(elem, second) + getBit(elem,third) + getBit(elem, 6)) % 2 == 0
                ? 0 : first + 1;
    }

    private byte correctSingle(byte elem) {
        byte answer = elem;

        int summary = correctSingleHelper(elem, 0, 2, 4) +
                correctSingleHelper(elem, 1, 2, 5) +
                correctSingleHelper(elem, 3, 4, 5);

        if (summary == 0) {
            answer = setBit(answer, 0, 7);
        } else {
            answer = reverseBit(answer, summary - 1);
        }

        return answer;
    }

    public byte[] decode(byte[] masWithError) {
        byte[] answer = new byte[masWithError.length];

        for (int i = 0; i < answer.length; i++) {
            answer[i] = correctSingle(masWithError[i]);
        }

        return answer;
    }

    private byte collectToSingle(byte firstPart, byte secondPart) {
        byte answer = 0;

        answer = setBit(answer, getBit(firstPart, 2), 0);
        answer = setBit(answer, getBit(firstPart, 4), 1);
        answer = setBit(answer, getBit(firstPart, 5), 2);
        answer = setBit(answer, getBit(firstPart, 6), 3);
        answer = setBit(answer, getBit(secondPart, 2), 4);
        answer = setBit(answer, getBit(secondPart, 4), 5);
        answer = setBit(answer, getBit(secondPart, 5), 6);
        answer = setBit(answer, getBit(secondPart, 6), 7);

        return answer;
    }

    public char[] collectFromDecoded(byte[] mas) {
        char[] answer = new char[mas.length / 2];

        for (int i = 0; i < answer.length; i++) {
            answer[i] = (char) collectToSingle(mas[i * 2], mas[i * 2 + 1]);
        }

        return answer;
    }

    @Override
    public void execute() {
        try (FileInputStream inputStream = new FileInputStream(received);
             FileWriter outputStream = new FileWriter(decoded)) {

            byte[] inp = inputStream.readAllBytes();
            char[] out = collectFromDecoded(decode(inp));
            outputStream.write(out);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
