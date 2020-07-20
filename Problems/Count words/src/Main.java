import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int count = 0;

        int value = reader.read();
        boolean wasSpace = true;
        while (value != -1) {
            if (value == ' ') {
                wasSpace = true;
            } else {
                if (wasSpace) {
                    count++;
                }
                wasSpace = false;
            }

            value = reader.read();
        }

        reader.close();

        System.out.println(count);
    }
}