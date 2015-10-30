import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
/**
 * Contains all methods necessary to produce JSON output.
 *
 * @author raywang
 *
 */

public class JSONWriter {

	/**
	 * Writes a String and the Map value for that String.
	 * @param elements
	 * 			TreeMap words -> TreeMap path -> TreeSet positions
	 * @param bw
	 * 			writer to use
	 * @throws IOException
	 */
	public static void write_word(Entry<String,
			TreeMap<String, TreeSet<Integer>>> element, BufferedWriter bw) throws IOException {

		TreeMap<String, TreeSet<Integer>> map = element.getValue();

		bw.newLine();
		bw.write(indent(1) + quote(element.getKey()) + ": {");
		if(!map.isEmpty()) {
			Entry<String, TreeSet<Integer>> first = map.firstEntry();
			write_path(first, bw, 1);

			for(Entry<String, TreeSet<Integer>> entry :
				map.tailMap(first.getKey(), false).entrySet()) {
				bw.write(",");
				write_path(entry, bw, 1);
			}
		}

		bw.newLine();
		bw.write(indent(1) + "}");

	}

	/**
	 * Writes out a String and the Set value for that String
	 * @param elements
	 * 			TreeMap path -> TreeSet of positions
	 * @param bw
	 * 			writer to use
	 * @param level
	 * 			indent level
	 * @throws IOException
	 */
	public static void write_path(Entry<String,
			TreeSet<Integer>> elements, BufferedWriter bw, int level) throws IOException {

		if(!elements.getValue().isEmpty()) {
			TreeSet<Integer> set = elements.getValue();
			bw.newLine();
			bw.write(indent(level + 1) + quote(elements.getKey()) + ": [");
			write_position(set, bw, 1);
		}

		bw.newLine();
		bw.write(indent(level + 1) + "]");

	}


	/**
	 * Writes a set of integers in JSON format.
	 * @param elements
	 * 			elements to write to file
	 * @param bw
	 * 			writer to use
	 * @param level
	 * 			level of indent
	 * @throws IOException
	 */
	public static void write_position(TreeSet<Integer> elements,
			BufferedWriter bw, int level) throws IOException {

		if(!elements.isEmpty()) {
			Integer first = elements.first();
			bw.newLine();
			bw.write(indent(level + 2) + first);

			for(Integer entry : elements.tailSet(first, false)) {
				bw.write(",");
				bw.newLine();
				bw.write(indent(level + 2) + entry);
			}
		}

	}

	/**
	 * Helper method to indent several times by 2 spaces each time. For example,
	 * indent(0) will return an empty string, indent(1) will return 2 spaces,
	 * and indent(2) will return 4 spaces.
	 *
	 * <p>
	 * <em>Using this method is optional!</em>
	 * </p>
	 *
	 * @param times
	 * @return
	 * @throws IOException
	 */
	public static String indent(int times) throws IOException {
		return times > 0 ? String.format("%" + times * 2 + "s", " ") : "";
	}

	/**
	 * Helper method to quote text for output. This requires escaping the
	 * quotation mark " as \" for use in Strings. For example:
	 *
	 * <pre>
	 * String text = "hello world";
	 * System.out.println(text); // output: hello world
	 * System.out.println(quote(text)); // output: "hello world"
	 * </pre>
	 *
	 * @param text
	 *            input to surround with quotation marks
	 * @return quoted text
	 */
	public static String quote(String text) {
		return "\"" + text + "\"";
	}


}
