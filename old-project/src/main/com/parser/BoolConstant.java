package main.com.parser;

public class BoolConstant extends BoolExpression {
    public final boolean b;

    public BoolConstant(int line, boolean b) {
	super(line);
	this.b = b;
    }

    void pretty(Printer p) {
	p.print(b ? "tt" : "ff");
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof BoolConstant)
		{
			BoolConstant o = (BoolConstant)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.b == this.b)
				return true;
		}
		return false;
	}
}