package Tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import com.google.gson.Gson;

import Objects.Configuration;

/**
 * This class supports IO fetch and usage. Due to the nature of a Maven project
 * we save all the resources to the resources directory.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class FileResourcesUtils {

	public Configuration getConfig(String filename) {
		InputStream is = this.getFileFromResourceAsStream(filename);
		return new Gson().fromJson(this.getInputStream(is), Configuration.class);
	}

	/**
	 * This method fetches a file from the resources directory (where all files,
	 * images etc should be saved).
	 * 
	 * @param fileName The name of the file
	 * @return InputStream handler of the file
	 */
	public InputStream getFileFromResourceAsStream(String fileName) {

		// The class loader that loaded the class
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		// the stream holding the file content
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}

	}

	/**
	 * This method print the content of a file using its inputStream handler.
	 * 
	 * @param is InputStream handler of the file
	 */
	public static void printInputStream(InputStream is) {

		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method reads and returns ONLY the first line of a file
	 * 
	 * Be careful, in this stage we only use this object and this function to get
	 * the configuration of the clients and the servers which means we only need a
	 * line for host IP and the port in use. If you wish to extend the IO from files
	 * using this function, you could and should create a string with all the lines
	 * of the file in it. In any other case you should create a new function which
	 * fits your purpose better.
	 * 
	 * @param is InputStream the file handler
	 * @return the first line of the file
	 */
	public String getInputStream(InputStream is) {

		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {

			return reader.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * This method prints a files' content.
	 * 
	 * @param file The name of the file we want to print its content
	 */
	public static void printFile(File file) {

		List<String> lines;
		try {
			lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
			lines.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}