package eu.adrianbrink.parser;

public class Sequence extends Statement {
    public final Statement s1;
    public final Statement s2;

    public Sequence(int line, Statement s1, Statement s2) {
		super(line);
		this.s1 = s1;
		this.s2 = s2;
    }

    void pretty(Printer p) {
		s1.pretty(p);
		p.print(";");
		p.newline();
		s2.pretty(p);
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Sequence)
		{
			Sequence o = (Sequence)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.s1.equals(this.s1) && o.s2.equals(this.s2))
				return true;
		}
		return false;
	}
}