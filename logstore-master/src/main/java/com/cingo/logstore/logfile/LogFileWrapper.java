package com.cingo.logstore.logfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LogFileWrapper implements LogWrapper {

	private static String LAST_LINE;
	private static int LAST_LINE_INDEX = 0;
	
	private File file;
	
	public LogFileWrapper(File file) {
		super();
		this.file = file;
	}

	public List<String> getLineContents() {
		List<String> fileLines = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(this.file.getPath()))) {

            stream.forEach(line -> fileLines.add(line));

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return this.mergeLogLines(fileLines);
	}
	
	private List<String> mergeLogLines(List<String> fileLines) {
		Map<Integer,String> lines = new HashMap<Integer, String>();
		
		fileLines.forEach(line -> {
			if (line.indexOf("| ")>0) {
				LAST_LINE_INDEX++;
				lines.put(LAST_LINE_INDEX, line.substring(line.indexOf("| ")+2));
				LAST_LINE = line.substring(line.indexOf("| ")+2);
			} else {
				lines.put(LAST_LINE_INDEX, LAST_LINE+"\n"+line);
				LAST_LINE = LAST_LINE+"\n"+line;
			}
		});
		
		List<String> logLineContents = new ArrayList<String>();
		lines.forEach((key, value) -> logLineContents.add(value));
		return logLineContents;
	}
	
}
