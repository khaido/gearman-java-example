package org.gearman.example2;

import org.gearman.Gearman;
import org.gearman.GearmanServer;
import org.gearman.GearmanWorker;

/**
 * The jenkins worker polls jobs from a job server and execute the jenkins function.
 * 
 */
public class JenkinsWorker {

	/** functions */
	public static final String ECHO_FUNCTION_NAME = "echo";
	public static final String REVERSE_FUNCTION_NAME = "reverse";
	public static final String JENKINS_INVOKE_JOB_FUNCTION_NAME = "JenkinsInvokeJob";
	public static final String JENKINS_JOB_STATUS_FUNCTION_NAME = "JenkinsJobStatus";

	/** The host address of the job server */
	public static final String HOST = "localhost";

	/** The port number the job server is listening on */
	public static final int PORT = 4730;

	
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
			JenkinsWorker.HOST, JenkinsWorker.PORT);

	/*
	 * Create a gearman worker. The worker poll jobs from the server and
	 * executes the corresponding GearmanFunction
	 */
	GearmanWorker worker = gearman.createGearmanWorker();
	
	
//	public static void main(String... args) {
//		
//		//new JenkinsWorker().work();
//		 
//	}

	

	public void stop() {
		
		worker.shutdown();
		
	}

	public void work() {


		/*
		 *  Tell the worker how to perform the functions
		 */
		worker.addFunction(JenkinsWorker.ECHO_FUNCTION_NAME, new EchoWorker());
		worker.addFunction(JenkinsWorker.REVERSE_FUNCTION_NAME, new StringReverseWorker());
		worker.addFunction(JenkinsWorker.JENKINS_INVOKE_JOB_FUNCTION_NAME, new JenkinsInvokeJob());
		worker.addFunction(JenkinsWorker.JENKINS_JOB_STATUS_FUNCTION_NAME, new JenkinsJobStatus());

		/*
		 *  Tell the worker that it may communicate with the this job server
		 */
		worker.addServer(server);
	}
	
	
}
