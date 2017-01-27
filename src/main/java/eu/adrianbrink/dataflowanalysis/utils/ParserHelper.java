package eu.adrianbrink.dataflowanalysis.utils;

import eu.adrianbrink.parser.Parser;
import eu.adrianbrink.parser.Statement;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sly on 26/01/2017.
 */
public class ParserHelper {
    public static List<Statement> parse(File file) throws IOException {
        List<String> lines = ParserHelper.readFile(file);
        List<Statement> statements = ParserHelper.parseLines(lines);
        return statements;
    }

    public static List<Statement> parse(String program) throws IOException {
        List<String> lines = Arrays.asList(program.split(";"));
        List<Statement> statements = ParserHelper.parseLines(lines);
        return statements;
    }

    // TODO: This can't read in actual correct programs, but rather a formatted version. This is because I don't understand exactly how the parser throws errors.
    // It splits the file into each line, parses the line and then adds all the lists together.
    private static List<String> readFile(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream fs = new FileInputStream(file);
        InputStreamReader fsReader = new InputStreamReader(fs, Charset.forName("UTF-8"));
        BufferedReader bReader = new BufferedReader(fsReader);
        String line;
        while ((line = bReader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    private static List<Statement> parseLines(List<String> lines) {
        List<Statement> statements = new ArrayList<>();
        for (String line : lines) {
            Statement statement = Parser.parse(line);
            statements.add(statement);
        }
        return statements;
    }
}
