package eu.adrianbrink.parser;

public class Number extends Expression {
    private int n;

    public Number(int line, int n) {
		super(line);
		this.n = n;
    }

    void pretty(Printer p) {
		p.print("" + n);
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Number)
		{
			Number o = (Number)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.n == this.n)
				return true;
		}
		return false;
	}

	public int getN() {
		return this.n;
	}
}