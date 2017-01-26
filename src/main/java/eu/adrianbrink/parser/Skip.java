package eu.adrianbrink.parser;

public class Skip extends Statement {
    public Skip(int line) {
	super(line);
    }

    void pretty(Printer p) {
	p.print("skip");
    }
	
	@Override
	public boolean equals(Object other)
	{
		return other instanceof Skip && (((Skip)other).line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS);
	}
}