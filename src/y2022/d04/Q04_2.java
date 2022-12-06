package y2022.d04;

import static java.lang.System.out;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class Q04_2 {
	public static void main(String[] args) throws Exception {
		out.println(
			Files.readAllLines(new File("src/y2022/d04/Q1.txt").toPath()).stream()
			.map(line-> Arrays.stream(line.split("[-,]")).map(Integer::parseInt).toArray(Integer[]::new))
			.filter(a-> a[1] >= a[2] && a[0] <= a[3])
			.count()
		);
	}
}
