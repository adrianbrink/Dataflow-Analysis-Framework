package eu.adrianbrink.parser;

public class ToStringPrinter extends Printer
{
	private String str;
	
	public ToStringPrinter()
	{
		str = "";
	}
	
	@Override
	public void print(String s)
	{
		str += s;
	}
	
	@Override
	public void newline()
	{
		str += "\n";
		for (int i=0; i<indent; i++)
			str += " ";
    }
	
	public String get()
	{
		return str;
	}
}