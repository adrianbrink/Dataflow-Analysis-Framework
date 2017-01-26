package eu.adrianbrink.parser;

public class Assignment extends Statement {
    public final String x;
    public final Expression e;

    public Assignment(int line, String x, Expression e) {
	super(line);
	this.x = x;
	this.e = e;
    }

    void pretty(Printer p) {
	p.print(x);
	p.print(" := ");
	e.pretty(p);
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Assignment)
		{
			Assignment o = (Assignment)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.x.equals(this.x) && o.e.equals(this.e))
				return true;
		}
		return false;
	}
}