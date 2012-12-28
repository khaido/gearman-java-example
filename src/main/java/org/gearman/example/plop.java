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

public class plop implements Runnable {

	public void startProcess() {
		
			Thread t = new Thread(this);
			t.start();
	}	


	public void run() {
		// TODO Auto-generated method stub
		
        String host = Constants.GEARMAN_DEFAULT_TCP_HOST;
        int port = Constants.GEARMAN_DEFAULT_TCP_PORT;
        List<Class<GearmanFunction>> functions =
                new ArrayList<Class<GearmanFunction>>();

        functions.add((Class<GearmanFunction>)(Class)EchoFunction.class);
        functions.add((Class<GearmanFunction>)(Class)ReverseFunction.class);
        functions.add((Class<GearmanFunction>)(Class)JenkinsJobStatus.class);
        
        functions.remove((Class<GearmanFunction>)(Class)ReverseFunction.class);

        WorkerRunner wr = new WorkerRunner(host, port, functions);
        wr.start();
	}

}
