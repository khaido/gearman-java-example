package org.gearman.example2;

// a class to facilitate deserializing of json data

public class JenkinsData {
	  private String url;
	  private String jobId;

	  // Getters and setters are not required for this example.
	  // GSON sets the fields directly.

	  @Override
	  public String toString() {
	    return url + " - " + jobId;
	  }
	  
	  public String getUrl() {
		  return url;
	  }
	  
	  public String getJobId() {
		  return jobId;
	  }
}

