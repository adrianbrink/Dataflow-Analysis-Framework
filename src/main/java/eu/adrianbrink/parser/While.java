package eu.adrianbrink.parser;

public class While extends Statement {
    public final BoolExpression b;
    public final Statement s;

    public While(int line, BoolExpression b, Statement s) {
		super(line);
		this.b = b;
		this.s = s;
    }

    void pretty(Printer p) {
		p.print("while (");
		b.pretty(p);
		p.print(")");
		p.in();
		p.newline();
		s.pretty(p);
		p.out();
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof While)
		{
			While o = (While)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.b.equals(this.b) && o.s.equals(this.s))
				return true;
		}
		return false;
	}
}