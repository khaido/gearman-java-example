package org.gearman.example2;

import org.gearman.Gearman;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;
import org.gearman.GearmanServer;
import org.gearman.GearmanWorker;

/**
 * The echo worker polls jobs from a job server and execute the echo function.
 * 
 * The echo worker illustrates how to setup a basic worker
 */
public class StringReverseWorker implements GearmanFunction {

	/** The echo function name */
	public static final String FUNCTION_NAME = "reverse";

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
				StringReverseWorker.HOST, StringReverseWorker.PORT);

		/*
		 * Create a gearman worker. The worker poll jobs from the server and
		 * executes the corresponding GearmanFunction
		 */
		GearmanWorker worker = gearman.createGearmanWorker();

		/*
		 *  Tell the worker how to perform the echo function
		 */
		worker.addFunction(StringReverseWorker.FUNCTION_NAME, new StringReverseWorker());

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
        StringBuffer sb = new StringBuffer(decoded);
        String rv = sb.reverse().toString();
		return rv.getBytes();
	}

}
