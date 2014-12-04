package ch.unibe.scg.doodle.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.xstream.XStream;

/**
 * Simple implementation of {@link DoodleDatabaseMap} storing files into the OS'
 * temp file directory, i.e. data will probably be vanished on a reboot.
 * 
 * @author Cedric
 *
 * @param <T>
 */
public class TempFileDatabaseMap<T> extends DoodleDatabaseMap<T> {

	private XStream xstream = new XStream();

	public TempFileDatabaseMap(String mapName) {
		super(mapName);
	}

	private File tempFile() {
		String sysTempDirPath = System.getProperty("java.io.tmpdir");
		File tempDir = new File(sysTempDirPath + "/DoodleDebug");
		tempDir.mkdirs();
		File tempFile = new File(tempDir, mapName + ".ddmap");
		return tempFile;
	}

	@SuppressWarnings("unchecked")
	private Map<String, T> loadMap() {
		File file = tempFile();
		if (tempFile().exists())
			return (Map<String, T>) xstream.fromXML(file);
		else
			return new HashMap<>();
	}

	private void saveMap(Map<String, T> map) {
		try {
			xstream.toXML(map, new FileOutputStream(tempFile()));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clear() {
		Map<String, T> map = loadMap();
		map.clear();
		saveMap(map);
	}

	@Override
	public T get(Object key) {
		return loadMap().get(key);
	}

	@Override
	public Set<String> keySet() {
		return loadMap().keySet();
	}

	@Override
	public T put(String key, T value) {
		Map<String, T> map = loadMap();
		T result = map.put(key, value);
		saveMap(map);
		return result;
	}

	@Override
	public T remove(Object key) {
		Map<String, T> map = loadMap();
		T result = map.remove(key);
		saveMap(map);
		return result;
	}

	@Override
	public int size() {
		return loadMap().size();
	}

	@Override
	public Collection<T> values() {
		return loadMap().values();
	}

}
