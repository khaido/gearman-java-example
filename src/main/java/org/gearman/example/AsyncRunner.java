/*
 * Copyright (C) 2009 by Eric Lambert <Eric.Lambert@sun.com>
 * Use and distribution licensed under the BSD license.  See
 * the COPYING file in the parent directory for full text.
 */
package org.gearman.example;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.gearman.common.Constants;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.worker.GearmanFunction;
import org.gearman.worker.GearmanWorker;
import org.gearman.worker.GearmanWorkerImpl;

public class AsyncRunner implements Runnable {

    GearmanNIOJobServerConnection conn;
    List<Class<GearmanFunction>> functions;
    GearmanWorker worker = new GearmanWorkerImpl();

    
    @SuppressWarnings(value = "unchecked")
    public AsyncRunner() {
    }
    
    @SuppressWarnings(value = "unchecked")
    public AsyncRunner(String host, int port,
            List<Class<GearmanFunction>> funs) {
        conn = new GearmanNIOJobServerConnection(host, port);
        functions = new ArrayList<Class<GearmanFunction>>();
        functions.addAll(funs);
    }

    public void start() {
        worker.addServer(conn);
        for (Class<GearmanFunction> fun : functions) {
            worker.registerFunction(fun);
        }
        
      	worker.work();
        
    }
    
//    public void stop() {
////    	worker.unregisterAll();
//    	worker.stop();
//    	worker.shutdown();
//    }

/*    @SuppressWarnings(value = "unchecked")
    public static void main(String[] args) {
        List<Class<GearmanFunction>> functions =
                new ArrayList<Class<GearmanFunction>>();
        if (args.length == 0) {
            usage(System.out);
            return;
        }
        String host = Constants.GEARMAN_DEFAULT_TCP_HOST;
        int port = Constants.GEARMAN_DEFAULT_TCP_PORT;
        for (String arg : args) {
            if (arg.startsWith("-h")) {
                host = arg.substring(2);
            } else if (arg.startsWith("-p")) {
                port = Integer.parseInt(arg.substring(2));
            } else if (arg.charAt(0) == '-') {
                usage(System.out);
            } else {
                Class c;
                try {
                    c = Class.forName(arg);
                    if (!GearmanFunction.class.isAssignableFrom(c)) {
                        System.out.println(arg + " is not an instance of " +    //NOPMD
                                GearmanFunction.class.getCanonicalName());
                        usage(System.out);
                        return;
                    }
                    functions.add((Class<GearmanFunction>)c);
                } catch (ClassNotFoundException cfne) {
                    System.out.println("Can not find function " + arg +         //NOPMD
                            " on class path");
                    return;
                }
            }
        }

        new AsyncRunner(host, port,functions).start();
    }

    public static void usage(PrintStream out) {
        String[] usage = {
            "usage: org.gearman.example.WorkerRunner [-h<host>] [-p<port>] " +
                    "functionName",
            "\t-h<host> - job server host",
            "\t-p<port> - job server port",
            "\n\tExample: java org.gearman.example.WorkerRunner " +
                    "org.gearman.example.ReverseFunction",
            "\tExample: java org.gearman.example.WorkerRunner " +
            "-h127.0.0.1 -p4730 org.gearman.example.ReverseFunction",
        };

        for (String line : usage) {
            out.println(line);
        }
    }
*/

    public void startProcess() {
        Thread t = new Thread(this);
        t.start();
    }
    
	public void run() {

        String host = Constants.GEARMAN_DEFAULT_TCP_HOST;
        int port = Constants.GEARMAN_DEFAULT_TCP_PORT;
        List<Class<GearmanFunction>> functions =
                new ArrayList<Class<GearmanFunction>>();

        functions.add((Class<GearmanFunction>)(Class)EchoFunction.class);
        functions.add((Class<GearmanFunction>)(Class)ReverseFunction.class);
        functions.add((Class<GearmanFunction>)(Class)JenkinsJobStatus.class);
        
        functions.remove((Class<GearmanFunction>)(Class)ReverseFunction.class);
		
        new AsyncRunner(host, port,functions).start();
		
	}
}
