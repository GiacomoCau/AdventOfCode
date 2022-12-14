package y2022.d13;

import static java.lang.Integer.valueOf;
import static java.lang.System.out;
import static java.nio.file.Files.readString;
import static java.util.regex.Pattern.compile;
import static java.util.stream.IntStream.range;
import static y2022.d13.Q1.Cons.cons;
import static y2022.d13.Q1.Parser.parse;

import java.io.File;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Q1 { 
	
	record Cons(Object car, Cons cdr) {
		static Cons cons(Object car) { return cons(car, null); }
		static Cons cons(Object car, Object cdr) { return new Cons(car, (Cons) cdr); }
		public String toString() { return "(" + toString(this) + ")"; }
		private String toString(Cons c) {
			var car = switch(c.car) { case null-> "()"; case Object o-> o.toString(); };
			return c.cdr == null ? car : car + " " + toString(c.cdr);
		}
	}; 
	
	static class Parser {
		static Object parse(String s) { return new Parser(s).parse(); }
		static Pattern p = compile("\\[]|\\[|]|,|\\d+");
		int i=-1; String[] token;
		Parser(String s) {
			token = p.matcher(s).results().map(MatchResult::group).toArray(String[]::new);
		}
		Object parse() {
			return switch (token[i+=1]) {
				case "]", "[]"-> null;
				case "[", ","-> cons(parse(), parse());
				case String s-> valueOf(s);
			};
		}
	}
	
	public static void main(String[] args) throws Exception {
		var pairs = readString(new File("src/y2022/d13/q1.txt").toPath()).split("\n\n");
		out.println(
			range(0, pairs.length)
			//.filter(i-> ((Function<String[],Boolean>) p-> compare(parse(p[0]), parse(p[1])) == -1).apply(pairs[i].split("\n")) )
			.filter(i-> apply(p-> compare(parse(p[0]), parse(p[1])) == -1, pairs[i].split("\n")) )
			.map(i-> i+1)
			.sum()
		);
	}
	

	static <T,R> R apply(Function<T,R> f, T t) { return f.apply(t); }
	
	static int compare(Object a, Object b) {
		//out.println(a + " | " + b);
		if (a == null) return b == null ? 0 : -1;	
		if (a instanceof Cons ca) {
			if (b == null) return 1;
			if (b instanceof Cons cb) {
				int c = compare(ca.car, cb.car);
				if (c != 0) return c;
				return compare(ca.cdr, cb.cdr);
			}
			return compare(ca, cons(b));
		}
		if (b == null) return 1;
		if (b instanceof Cons bc) return compare(cons(a), bc);
		return ((Integer) a).compareTo((Integer) b);
	}
}
