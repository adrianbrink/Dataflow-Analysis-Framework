package main.com.parser;

public class Disjunction extends BoolExpression {
    public final BoolExpression b1;
    public final BoolExpression b2;

    public Disjunction(int line, BoolExpression b1, BoolExpression b2) {
	super(line);
	this.b1 = b1;
	this.b2 = b2;
    }

    void pretty(Printer p) {
	p.print("(");
	b1.pretty(p);
	p.print(" || ");
	b2.pretty(p);
	p.print(")");
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Disjunction)
		{
			Disjunction o = (Disjunction)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.b1.equals(this.b1) && o.b2.equals(this.b2))
				return true;
		}
		return false;
	}
}