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
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) { // If its more than 1 argument then throw error and display instructions
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) { // If 1 argument is passed then consider it as a file and process it
            runfile(args[0]);
        } else { // If no arguments are passed, then start the prompt
            runPrompt();
        }
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in); // Bridge between byte streams and char streams
        BufferedReader reader = new BufferedReader(input);

        while (true) {
            System.out.println("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        Stream<String> tokens = scanner.tokens();

        tokens.forEach(System.out::println);

    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line "
                + line
                + "] Error"
                + where
                + ": "
                + message);

        hadError = true;

    }

    private static void runfile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
    }
}
