package eu.adrianbrink.parser;

public class Variable extends Expression {
    public final String id;

    public Variable(int line, String id) {
		super(line);
		this.id = id;
    }

    void pretty(Printer p) {
		p.print(id);
    }
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Variable)
		{
			Variable o = (Variable)other;
			if((o.line == this.line || !LINE_NUMBER_MATTERS_TO_EQUALS) && o.id.equals(o.id))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}