package org.gearman.example2;

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.gearman.Gearman;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;
import org.gearman.GearmanServer;
import org.gearman.GearmanWorker;
import org.gearman.example.JenkinsData;

import org.dom4j.Element;

import java.net.URL;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
import java.util.List;

import com.google.gson.Gson;


/**
 * The echo worker polls jobs from a job server and execute the echo function.
 * 
 * The echo worker illustrates how to setup a basic worker
 */
public class JenkinsJobStatus implements GearmanFunction {

	/** The echo function name */
	public static final String FUNCTION_NAME = "JenkinsJobStatus";

	/** The host address of the job server */
	public static final String HOST = "localhost";

	/** The port number the job server is listening on */
	public static final int PORT = 4730;

	public static void main(String... args) {

		/*
		 * Create a Gearman instance
		 */
		Gearman gearman = Gearman.createGearman();

		/*
		 * Create the job server object. This call creates an object represents
		 * a remote job server.
		 * 
		 * Parameter 1: the host address of the job server.
		 * Parameter 2: the port number the job server is listening on.
		 * 
		 * A job server receives jobs from clients and distributes them to
		 * registered workers.
		 */
		GearmanServer server = gearman.createGearmanServer(
				JenkinsJobStatus.HOST, JenkinsJobStatus.PORT);

		/*
		 * Create a gearman worker. The worker poll jobs from the server and
		 * executes the corresponding GearmanFunction
		 */
		GearmanWorker worker = gearman.createGearmanWorker();

		/*
		 *  Tell the worker how to perform the echo function
		 */
		worker.addFunction(JenkinsJobStatus.FUNCTION_NAME, new JenkinsJobStatus());

		/*
		 *  Tell the worker that it may communicate with the this job server
		 */
		worker.addServer(server);
	}

	@Override
	public byte[] work(String function, byte[] data,
			GearmanFunctionCallback callback) throws Exception {

		/*
		 * The work method performs the gearman function. In this case, the echo
		 * function simply returns the data it received
		 */
        String decoded = new String(data, "UTF-8"); 

		Gson gson = new Gson();
        
        try {
        	
        	// convert json string to an object 
    		JenkinsData jdata = gson.fromJson(decoded, JenkinsData.class);
    		
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
	    }
        
        return decoded.getBytes();
		
	}

}
