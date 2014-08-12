package ch.unibe.scg.doodle.database;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.doodle.database.hbase.HBaseIntMap;
import ch.unibe.scg.doodle.util.ApplicationUtil;
import ch.unibe.scg.doodle.util.Pair;

/**
 * Database abstraction to store doodles (js-wrapped html and css strings) and
 * load them.
 * 
 * @author cedric
 * 
 */
public class DoodleDatabase {

	private static final String HTML_TABLE_NAME = "doodles_html";
	private static final String CSS_TABLE_NAME = "doodles_css";
	private static final String PERSISTENCE_TABLE_NAME = "doodles_persistence";
	private static final int NEXT_ID_KEY = 0;

	HBaseIntMap<String> htmlMap = new HBaseIntMap<>(
			ApplicationUtil.getApplicationName(), HTML_TABLE_NAME);
	HBaseIntMap<String> cssMap = new HBaseIntMap<>(
			ApplicationUtil.getApplicationName(), CSS_TABLE_NAME);
	HBaseIntMap<Integer> persistenceMap = new HBaseIntMap<>(
			ApplicationUtil.getApplicationName(), PERSISTENCE_TABLE_NAME);

	// XXX: Problem: Uncoordinated access to DB from multiple applications
	// TODO: Use long to prevent overflow
	private int nextID;
	private int nextLoadID = 0;

	public DoodleDatabase() {
		this.nextID = persistenceMap.containsKey(NEXT_ID_KEY) ? (int) persistenceMap
				.get(NEXT_ID_KEY) : 0;
	}

	/**
	 * Store a doodle, i.e. its two JS scripts inserting data into a running
	 * page.
	 * 
	 * @param htmlAddingScript
	 *            JS script that inserts this doodle's HTML code into the DOM
	 * @param cssAddingScript
	 *            JS script that inserts this doodle's CSS code into HTML head
	 */
	public void store(String htmlAddingScript, String cssAddingScript) {
		htmlMap.put(nextID, htmlAddingScript);
		cssMap.put(nextID, cssAddingScript);
		increaseNextID();
	}

	private void increaseNextID() {
		nextID++;
		persistenceMap.put(NEXT_ID_KEY, (Integer) nextID);
	}

	/**
	 * Load new (not printed yet) doodles from DB.
	 * 
	 * @return Pair Tuple of two JS script strings: One to insert HTML, another
	 *         one to insert CSS into a running page.
	 */
	public List<Pair<String, String>> loadNewDoodles() {
		List<Pair<String, String>> scripts = new ArrayList<Pair<String, String>>();
		while (hasNewDoodles()) {
			scripts.add(new Pair<String, String>((String) htmlMap
					.get(nextLoadID), (String) cssMap.get(nextLoadID)));
			nextLoadID++;
		}
		return scripts;
	}

	public boolean hasNewDoodles() {
		return htmlMap.containsKey(nextLoadID); // XXX Check for css necessary?
	}
}
