package eu.adrianbrink.dataflowanalysis;

import eu.adrianbrink.parser.Parser;

/**
 * Hello world!
 *
 */
public class Analysis
{
    public static void main( String[] args )
    {
        // TODO: The parser can't parse programs with an ending ; . This should work and should be fixed.
        Parser.parse("x := 1; y := 10; z := 100");
        System.out.println( "Hello World!" );
    }
}
