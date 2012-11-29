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

import java.net.URL;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;



public class JenkinsInvokeJob extends AbstractGearmanFunction {
    
    public GearmanJobResult executeFunction() {

    	boolean status = true;
        StringBuffer sb = new StringBuffer(ByteUtils.fromUTF8Bytes((byte[]) this.data));        
        
        try {
	        // every Hudson model object exposes the .../api/xml, but in this example
	        // we'll just take the root object as an example
	        URL url = new URL("http://localhost:8080/job/burnit/build");
	
	        // if you are calling security-enabled Hudson and
	        // need to invoke operations and APIs that are protected,
	        // consult the 'SecuredMain" class
	        // in this package for an example using HttpClient.
	
	        // read it into DOM.
	        Document dom = new SAXReader().read(url);


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

