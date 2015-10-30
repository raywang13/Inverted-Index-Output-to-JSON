import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Stores the inverted index.
 *
 * @author raywang
 *
 */
public class InvertedIndex {

	/* Stores word and relative path and position */
	private final TreeMap<String, TreeMap<String, TreeSet<Integer>>> index;

	/* Initializes TreeMap */
	public InvertedIndex() {
		index = new TreeMap<>();
	}

	/**
	 * Properly adds a word and position to the index. Must initialize inner
	 * data structure if necessary. Make sure you consider how to handle
	 * duplicates (duplicate words, and words with duplicate positions), and how
	 * to handle words with mixed case and extra spaces.
	 *
	 * @param word
	 *            word to add to index
	 * @param position
	 *            position word was found
	 * @return true if this was a unique entry, false if no changes were made to
	 *         the index
	 */
	public boolean add(String word, Path path, int position) {

		String pathString = path.toString();

		if (!index.containsKey(word)) {
			index.put(word, new TreeMap<String, TreeSet<Integer>>());
		}

		if (!index.get(word).containsKey(pathString)) {
			index.get(word).put(pathString, new TreeSet<Integer>());
		}

		return index.get(word).get(pathString).add(position);
	}


	/**
	 * Writes the entire nested data structure.
	 * @param output
	 * 			output path
	 */
	public void write_all(Path output) {

		try (BufferedWriter bw = Files.newBufferedWriter(output, Charset.forName("UTF8"))) {

			bw.write("{");
			if(!index.isEmpty()) {
				Entry<String, TreeMap<String, TreeSet<Integer>>> first
				= index.firstEntry();

				JSONWriter.write_word(first, bw);

				for(Entry<String, TreeMap<String, TreeSet<Integer>>> entry :
					index.tailMap(first.getKey(), false).entrySet()) {
					bw.write(",");

					JSONWriter.write_word(entry, bw);
				}

				bw.newLine();
				bw.write("}");
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Problem outputting index to " +output+ ".");
		}
	}

}
