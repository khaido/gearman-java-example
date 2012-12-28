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

public class MyStopper {


    @SuppressWarnings(value = "unchecked")
    public MyStopper() {
    	
    }

    @SuppressWarnings(value = "unchecked")
    public static void main(String[] args){
        String host = Constants.GEARMAN_DEFAULT_TCP_HOST;
        int port = Constants.GEARMAN_DEFAULT_TCP_PORT;
        List<Class<GearmanFunction>> functions =
                new ArrayList<Class<GearmanFunction>>();

        WorkerRunner wr = new WorkerRunner(host, port, functions);
        wr.stop();
        

        
    }

}
