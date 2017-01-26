package eu.adrianbrink.parser;

public class Printer {
    private static int INDENT = 2;
    protected int indent;

    public Printer() {
    }

    public void print(String s) {
	System.out.print(s);
    }

    public void newline() {
	System.out.println();
	for (int i=0; i<indent; i++) {
	    System.out.print(" ");
	}
    }

    public void in() {
	indent += INDENT;
    }

    public void out() {
	indent -= INDENT;
    }
}