package main.com.parser;

public class If extends Statement {
    public final BoolExpression b;
    public final Statement s1;
    public final Statement s2;

    public If(int line, BoolExpression b, Statement s1, Statement s2) {
	super(line);
	this.b = b;
	this.s1 = s1;
	this.s2 = s2;
    }

    void pretty(Printer p) {
	p.print("if (");
	b.pretty(p);
	p.print(")");
	p.in();
	p.newline();
	s1.pretty(p);
	p.out();
	p.newline();
	p.print("else");
	p.in();
	p.newline();
	s2.pretty(p);
	p.out();
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof If)
		{
			If o = (If)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.b.equals(this.b)
				&& o.s1.equals(this.s1) && o.s2.equals(this.s2))
				return true;
		}
		return false;
	}
}