package eu.adrianbrink.parser;

public class Negation extends BoolExpression {
    public final BoolExpression b;

    public Negation(int line, BoolExpression b) {
		super(line);
		this.b = b;
    }

    void pretty(Printer p) {
		p.print("!");
		b.pretty(p);
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Negation)
		{
			Negation o = (Negation)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.b.equals(this.b))
				return true;
		}
		return false;
	}
}