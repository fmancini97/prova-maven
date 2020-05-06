package it.uniroma2.isw2.maven;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONPrinter {

public static void main(String[] args) {
		
		Logger logger = Logger.getLogger(JSONPrinter.class.getSimpleName());
		
		
		JSONParser jsonParser = new JSONParser();
		
		logger.info("Retrieving configuration");

		
		try (FileReader reader = new FileReader("config.json")) {
	            //Read JSON file
			
			JSONArray projectConfs = (JSONArray) jsonParser.parse(reader);
			
			for (Integer i = 0; i < projectConfs.size(); i++) {
				JSONObject projectConf = (JSONObject) projectConfs.get(i);
				String projectName = (String) projectConf.get("project-name");
				if (projectName == null || projectName.equals("")) {
			 		logger.log(Level.WARNING, "Error: Invalid project name or missing tasks"); 
				 	System.exit(1);
			 	}
				
				JSONArray jsonTasks = (JSONArray) projectConf.get("tasks");
				
				if (jsonTasks == null) {
			 		logger.log(Level.WARNING, "Error: Missing tasks"); 
				 	System.exit(1);

				}
			 	
			 	List<String> tasks = new ArrayList<>();
			 	for (Integer j = 0; j < jsonTasks.size(); j++) {
			 		tasks.add((String) jsonTasks.get(j));
			 	}
			 	
			 	JSONArray jsonIssues = (JSONArray) projectConf.get("analysis-types");
			 	List<String> issueTypes = new ArrayList<>();

			 	if (jsonIssues != null) {
			 		for (Integer j = 0; j < jsonIssues.size(); j++) {
						String issueType = (String) jsonIssues.get(j);
				 		issueTypes.add(issueType);
				 		
				 	}
			 	}
			 	
			 	String gitReleaseRegex = (String) projectConf.get("git-release-regex");
			 	
			 	if (gitReleaseRegex == null) {
			 		gitReleaseRegex = "%s";
			 	}
			 	
			 	logger.log(Level.INFO, "'{'\n\"project-name\": \"{0}\"\n\"tasks\": {1}\n\"analysis-types\": {2}\n\"git-release-regex\": \"{3}\"\n'}'", new Object[] {projectName, tasks, issueTypes, gitReleaseRegex});
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	
	}

}
