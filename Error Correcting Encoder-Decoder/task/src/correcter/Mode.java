package correcter;

public interface Mode {
    String encoded = "encoded.txt";
    String received = "received.txt";
    String decoded = "decoded.txt";
    String send = "send.txt";

    void execute();

    default int getBit(byte b, int index) {
        return (b & (1 << (7 - index))) == 0 ? 0 : 1;
    }

    default byte setBit(byte b, int bit, int index) {
        return (byte)(b | (bit << (7 - index)));
    }

    default byte reverseBit(byte b, int index) {
        return (byte) (b ^ 1 << (7 - index));
    }

}
