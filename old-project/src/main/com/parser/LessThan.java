package main.com.parser;

public class LessThan extends BoolExpression {
    public final Expression e1;
    public final Expression e2;

    public LessThan(int line, Expression e1, Expression e2) {
	super(line);
	this.e1 = e1;
	this.e2 = e2;
    }

    void pretty(Printer p) {
	p.print("(");
	e1.pretty(p);
	p.print(" < ");
	e2.pretty(p);
	p.print(")");
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof LessThan)
		{
			LessThan o = (LessThan)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.e1.equals(this.e1) && o.e2.equals(this.e2))
				return true;
		}
		return false;
	}
}