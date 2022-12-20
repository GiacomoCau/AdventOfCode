package y2022.d15;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.System.out;
import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Q21 {
	
	record P(int r, int c) {}
	record R (int r, int mn, int mx) {}
	record S (P s, P b, int md) {
		R r(int r) { var d = abs(abs(s.r-r) - md); return new R(r, s.c-d, s.c+d); }
		boolean in(int r) { return abs(s.r - r) <= md; }
	}	
	
	public static void main(String[] args) throws Exception {
		var ls = readAllLines(new File("src/y2022/d15/q1.txt").toPath())
			.stream()
			.map(line-> stream(line.substring(1+line.indexOf("=")).split("\\D+=")).mapToInt(Integer::parseInt).toArray())
			.map(a->{ P S=new P(a[1],a[0]), B=new P(a[3],a[2]); return new S(S, B, abs(S.r-B.r)+abs(S.c-B.c)); })
			.toList();
		var lr = rowRange(ls, 10);
        out.println(lr.stream().mapToInt(rng-> rng.mx-rng.mn).sum());
        //out.println(range(0, 4_000_001).mapToObj(r-> rowRange(ls,r)).filter(l-> l.size() > 1).mapToLong(l-> (l.getFirst().mx+1) * 4_000_000l + l.getFirst().r).findFirst().getAsLong());
        out.println(range(0, 4_000_001).mapToObj(r-> rowRange(ls,r)).filter(l-> l.size() > 1).map(l-> l.get(0)).map(rng-> (rng.mx+1) * 4_000_000l + rng.r).findFirst().get());
	}

	private static LinkedList<R> rowRange(List<S> ls, int r) {
		var lr = ls.stream()
			.filter(s-> s.in(r))
			.map(sb-> sb.r(r))
			.sorted((a,b)-> a.mn-b.mn)
			.reduce(
				new LinkedList<R>(),
				(a,b)->{ if (a.isEmpty() || b.mn - a.getLast().mx > 1) a.add(b); else a.set(a.size()-1, new R(r, a.getLast().mn, max(a.getLast().mx, b.mx))); return a;},
				(a,b)->{ a.addAll(b); return a; }
			);
		return lr;
	}
}
