package y2022.d15;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;

import java.io.File;
import java.util.LinkedList;

public class Q12 {
	
	record P(int r, int c) {}
	record R (int mn, int mx) {}
	record S (P s, P b, int md) {
		R r(int r) { var d = abs(abs(s.r-r) - md); return new R(s.c-d, s.c+d); }
		boolean in(int r) { return abs(s.r - r) <= md; }
	}	
	
	public static void main(String[] args) throws Exception {
		var r = 2_000_000;
		var lr = readAllLines(new File("src/y2022/d15/q1.txt").toPath())
			.stream()
			.map(line-> stream(line.substring(1+line.indexOf("=")).split("\\D+=")).mapToInt(Integer::parseInt).toArray())
			.map(a->{ P S=new P(a[1],a[0]), B=new P(a[3],a[2]); return new S(S, B, abs(S.r-B.r)+abs(S.c-B.c)); })
			.filter(s-> s.in(r))
			.map(sb-> sb.r(r))
			.sorted((a,b)-> a.mn-b.mn)
			.reduce(new LinkedList<R>(), Q12::accumulate, (a,b)->{ a.addAll(b); return a; } );
        out.println(lr.stream().mapToInt(rng-> rng.mx-rng.mn).sum());
	}

	private static LinkedList<R> accumulate(LinkedList<R> a, R b) {
		if (a.isEmpty() || b.mn - a.getLast().mx > 1)
			a.add(b);
		else
			a.set(a.size()-1, new R(a.getLast().mn, max(a.getLast().mx, b.mx)));
		return a;
	}
}
