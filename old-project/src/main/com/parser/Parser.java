package main.com.parser;

import java.lang.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.function.BiFunction;

public class Parser
{
	private Parser(){}
	
	private static class Result<E extends AST>
	{
		public E ast;
		public String rest;
		public int lastLine;
		
		public Result(E a, String r)
		{
			ast = a;
			rest = r;
			lastLine = 0;
		}
		
		public Result(E a, String r, int l)
		{
			ast = a;
			rest = r;
			lastLine = l;
		}
	}
	
	@FunctionalInterface
    private interface Function3 <A, B, C, R>
	{ 
		public R apply(A a, B b, C c);
    }
	
	private static int find(String s, List<String> ss)
	{
		for(int i = 0; i<s.length(); i++)
		{
			for(String st : ss)
				if(s.substring(i).startsWith(st))
					return i;
		}
		return -1;
	}
	
	private static int findParen(String s, char start, char end)
	{
		int c = -1;
		for(int i = 0; i<s.length(); i++)
		{
			if(s.charAt(i) == end)
				if(c == 0)
					return i;
				else
					c--;
			if(s.charAt(i) == start)
				c++;
		}
		throw new IllegalArgumentException("Unpared parenthsis at " + s);
	}
	
	private static <T extends AST> T parseCompletely(Result<T> r)
	{
		if(r.rest.length() == 0)
			return r.ast;
		throw new IllegalArgumentException("could not parse " + r.rest);
	}
	
	public static Statement parseFile(String path)
	{
		try
		{	
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return parse(new String(encoded, StandardCharsets.UTF_8));
		}
		catch(IOException e)
		{
			throw new IllegalArgumentException("file not found");
		}
	}
	
	public static Statement parse(String s)
	{
		return parseCompletely(parseAtLine(0, s.replace(" ", "").replace("\n", "").replace("\r", "").replace("\t", "")));
	}
	
	private static Result<Statement> parseAtLine(int line, String s)
	{
		if(s.length() == 0)
			throw new IllegalArgumentException("Cannot parse empty string");
		Statement ret = null;
		while(s.length() > 0)
		{
			if(s.charAt(0) == ';')
				s = s.substring(1);
			Statement second;
			int split;
			if(s.startsWith("skip"))
			{
				second = new Skip(line);
				split = 4;
			}
			else if(s.startsWith("if"))
			{
				int bl = findParen(s, '(', ')');
				BoolExpression b = parseBoolExpression(line, s.substring(3, bl));
				int half = findParen(s, '{', '}');
				Result<Statement> s1 = parseAtLine(line+1, s.substring(bl+2, half));
				split = findParen(s.substring(half+5), '{', '}') + half+5;
				Result<Statement> s2 = parseAtLine(s1.lastLine, s.substring(half+6, split));
				if(s1.rest.length() != 0 || s2.rest.length() != 0)
					throw new IllegalArgumentException("incomplete block in " + s);
				second = new If(line, b, s1.ast, s2.ast);
				split++;
			}
			else if(s.startsWith("while"))
			{
				int bl = findParen(s, '(', ')');
				BoolExpression b = parseBoolExpression(line, s.substring(6, bl));
				split = findParen(s, '{', '}');
				Result<Statement> st = parseAtLine(line+1, s.substring(bl+2, split));
				if(st.rest.length() != 0)
					throw new IllegalArgumentException("incomplete block in " + s);
				second = new While(line, b, st.ast);
				split++;
			}
			else if(s.startsWith("output"))
			{
				split = findParen(s, '(', ')');
				Expression e = parseExpression(line, s.substring(7, split));
				second = new Output(line, e);
				split++;
			}
			else
			{
				int as = find(s, Arrays.asList(":="));
				split = find(s, Arrays.asList(";"));
				if(split == -1)
					split = s.length();
				Expression e = parseExpression(line, s.substring(as+2, split));
				second = new Assignment(line, s.substring(0, as), e);
			}
			s = s.substring(split);
			if(ret == null)
				ret = second;
			else
				ret = new Sequence(line-1, ret, second);
			line++;
		}
		return new Result<Statement>(ret, s, line);
	}
	
	public static BoolExpression parseBoolExpression(int line, String s)
	{
		return parseCompletely(tryParseBoolExpression(line, s));
	}
	
	private static Result<BoolExpression> tryParseBoolExpression(int line, String s)
	{
		Map<String, Function3<Integer, BoolExpression, BoolExpression, BoolExpression>> map = 
			new HashMap<String, Function3<Integer, BoolExpression, BoolExpression, BoolExpression>>();
		map.put("&&", (li, a, b) -> new Conjunction(li, a, b));
		map.put("||", (li, a, b) -> new Disjunction(li, a, b));
		return tryParse(line, (li, st) -> parseSingleBoolExpression(li, st), map, s.replace(" ",""));
	}
	
	private static BoolExpression parseSingleBoolExpression(int line, String s)
	{
		if(s.charAt(0) == '!')
			return new Negation(line, parseSingleBoolExpression(line, s.substring(1)));
		if(s.equals("true") || s.equals("tt"))
			return new BoolConstant(line, true);
		if(s.equals("false") || s.equals("ff"))
			return new BoolConstant(line, false);
		int eq = find(s, Arrays.asList("="));
		if(eq > 0)
		{
			if(find(s.substring(eq+1), Arrays.asList("=", "<")) != -1)
				throw new IllegalArgumentException("multiple operators in " + s);
			return new Equality(line, parseExpression(line, s.substring(0, eq)), parseExpression(line, s.substring(eq+1)));
		}
		int lt = find(s, Arrays.asList("<"));
		if(lt > 0)
		{
			if(find(s.substring(lt+1), Arrays.asList("=", "<")) != -1)
				throw new IllegalArgumentException("multiple operators in " + s);
			return new LessThan(line, parseExpression(line, s.substring(0, lt)), parseExpression(line, s.substring(lt+1)));
		}
		throw new IllegalArgumentException(s + " is not valid boolean");
	}
	
	public static Expression parseExpression(int line, String s)
	{
		return parseCompletely(tryParseExpression(line, s));
	}
	
	private static Result<Expression> tryParseExpression(int line, String s)
	{
		Map<String, Function3<Integer, Expression, Expression, Expression>> map = 
			new HashMap<String, Function3<Integer, Expression, Expression, Expression>>();
		map.put("+", (li, a, b) -> new Addition(li, a, b));
		map.put("*", (li, a, b) -> new Multiplication(li, a, b));
		return tryParse(line, (li, st) -> parseSingleExpression(li, st), map, s.replace(" ",""));
	}
	
	private static Expression parseSingleExpression(int line, String s)
	{
		if(s.matches("(-)?\\d+"))
			return new Number(line, Integer.parseInt(s));
		else if(s.matches("(-)?\\d+.+"))
			throw new IllegalArgumentException(s + " was neither number nor variable");
		else if(s.matches("-.*"))
			return new Multiplication(line, new Number(line, -1), new Variable(line, s.substring(1)));
		else
			return new Variable(line, s);
	}
	
	private static <T extends AST> Result<T> 
		tryParse(int line, BiFunction<Integer, String, T> singleParse, Map<String, Function3<Integer, T, T, T>> treeBuild, String s)
	{
		if(s.length() == 0)
			throw new IllegalArgumentException("Cannot parse empty string");
		int split = 0;
		T ret = null;
		List<String> syms = new ArrayList<String>(treeBuild.keySet());
		if(s.charAt(0) == '(')
		{
			split = findParen(s, '(', ')');
			if(split == -1)
				throw new IllegalArgumentException("Unpared parathesis at " + s);
			ret = parseCompletely(tryParse(line, singleParse, treeBuild, s.substring(1, split)));
			split++;
			s = s.substring(split, s.length());
		}
		else
		{
			split = find(s, syms);
			if(split == -1)
				return new Result<T>(singleParse.apply(line, s), "");
			ret = singleParse.apply(line, s.substring(0,split));
			s = s.substring(split, s.length());
		}
		while(s.length() > 0)
		{
			T second;
			String sym = null;
			for(String k : treeBuild.keySet())
			{
				if(s.startsWith(k))
					sym = k;
			}
			int symSize = sym==null ? 0 : sym.length();
			if(s.length() > 1 && s.charAt(symSize) == '(')
			{
				split = findParen(s.substring(symSize), '(', ')');
				split += symSize;
				second = parseCompletely(tryParse(line, singleParse, treeBuild, s.substring(symSize+1, split)));
				split++;
			}
			else
			{
				split = find(s.substring(symSize), syms);
				if(split == -1)
					split = s.length();
				else
					split += symSize;
				second = singleParse.apply(line, s.substring(symSize, split));
			}
			if(sym == null)
				break;
			ret = treeBuild.get(sym).apply(line, ret, second);
			s = s.substring(split);
		}
		return new Result<T>(ret, s);
	}
}