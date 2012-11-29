/*
 * Copyright (C) 2009 by Eric Lambert <Eric.Lambert@sun.com>
 * Use and distribution licensed under the BSD license.  See
 * the COPYING file in the parent directory for full text.
 */
package org.gearman.example;

import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobResultImpl;
import org.gearman.util.ByteUtils;
import org.gearman.worker.AbstractGearmanFunction;

import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;

import java.net.URL;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
import java.util.List;

import com.google.gson.Gson;



public class JenkinsJobStatus extends AbstractGearmanFunction {
    
    public GearmanJobResult executeFunction() {

    	boolean status = true;
        StringBuffer sb = new StringBuffer(ByteUtils.fromUTF8Bytes((byte[]) this.data));        
        String sb_str = sb.toString();
        
		Gson gson = new Gson();
        
        try {
        	
        	// convert json string to an object 
    		JenkinsData jdata = gson.fromJson(sb_str, JenkinsData.class);
    		
    		// create the url of the jenkins job
    		String jenkins_job_xml = jdata.getUrl()+"/job/"+jdata.getJobId()+"/api/xml";
        	
	        // every Hudson model object exposes the .../api/xml, but in this example
	        // we'll just take the root object as an example
	        URL url = new URL(jenkins_job_xml);
	
	        // if you are calling security-enabled Hudson and
	        // need to invoke operations and APIs that are protected,
	        // consult the 'SecuredMain" class
	        // in this package for an example using HttpClient.
	
	        // read it into DOM.
	        Document dom = new SAXReader().read(url);
	        

	        for( Element job : (List<Element>)dom.getRootElement().elements("healthReport")) {
	            System.out.println(String.format("Health Report:%s\tStatus:%s",
	                job.elementText("description"), job.elementText("score")));
	        }
	        

        } catch (Exception e) {
	    	System.out.println("Error!!!!!");
	    	status = false;
	    	
	    	
	    }
	    
        
      GearmanJobResult gjr = new GearmanJobResultImpl(this.jobHandle,
    		  status, sb.toString().getBytes(),
    		  new byte[0], new byte[0], 0, 0);
      
      return gjr;
        
    }
}

