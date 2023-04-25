import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import java.util.List;
import java.util.stream.Stream;

public class Lox {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runfile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in); // Bridge between byte streams and char streams
        BufferedReader reader = new BufferedReader(input);

        for (; ; ) {
            System.out.println("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        Stream<String> tokens = scanner.tokens();

        tokens.forEach(System.out::println);

    }

    private static void runfile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }
}
