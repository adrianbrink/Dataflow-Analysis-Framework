package main.com.parser;

public abstract class AST {
	public static boolean LINE_NUMBER_MATTERS_TO_EQUALS = true;
    public final int line;

    public AST(int line) {
	this.line = line;
    }

    abstract void pretty(Printer p);
	
	@Override
	public String toString()
	{
		ToStringPrinter p = new ToStringPrinter();
		pretty(p);
		return p.get();
	}
}