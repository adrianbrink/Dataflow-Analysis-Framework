package eu.adrianbrink.parser;

public class Output extends Statement {
    public final Expression e;

    public Output(int line, Expression e) {
		super(line);
		this.e = e;
    }

    void pretty(Printer p) {
		p.print("output ");
		e.pretty(p);
    }
	
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Output)
		{
			Output o = (Output)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.e.equals(this.e))
				return true;
		}
		return false;
	}
}