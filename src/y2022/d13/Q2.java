package y2022.d13;

import static java.lang.Integer.valueOf;
import static java.lang.System.out;
import static java.nio.file.Files.readString;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;
import static y2022.d13.Q2.Cons.cons;
import static y2022.d13.Q2.Parser.parse;

import java.io.File;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Q2 { 
	
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
		var divider = new String[] {"[[2]]", "[[6]]" };
		var packets = asList((readString(new File("src/y2022/d13/q1.txt").toPath()) + "\n" + stream(divider).collect(joining("\n")) ).split("\n\n?"));
		packets.sort((a,b)-> compare(parse(a), parse(b)));
		out.println(stream(divider).mapToInt(packets::indexOf).map(i-> i+1).reduce(1, (a,b)-> a*b));
	}
	
	static int compare(Object a, Object b) {
		//out.println(a + " | " + b);
		return switch (a) {
			case null-> b == null ? 0 : -1;
			case Cons ca-> switch (b) {
				case null-> 1;
				case Cons cb-> {
					int c = compare(ca.car, cb.car);
					yield c != 0 ? c : compare(ca.cdr, cb.cdr);
				}
				default-> compare(ca, cons(b));
			};
			default-> switch (b) {
				case null-> 1;
				case Cons cb-> compare(cons(a), cb);
				default-> ((Integer) a).compareTo((Integer) b);
			};
		};
	}
}
