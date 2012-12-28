/*
 * Copyright (C) 2009 by Eric Lambert <Eric.Lambert@sun.com>
 * Use and distribution licensed under the BSD license.  See
 * the COPYING file in the parent directory for full text.
 */
package org.gearman.example;

import java.io.IOException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import junit.framework.Assert;

import org.gearman.common.Constants;
import org.gearman.common.GearmanNIOJobServerConnection;
import org.gearman.tests.functions.FailedFunction;
import org.gearman.tests.functions.FailedFunctionFactory;
import org.gearman.tests.functions.IncrementalReverseFunction;
import org.gearman.tests.functions.IncrementalReverseFunctionFactory;
import org.gearman.tests.functions.LongRunningFunction;
import org.gearman.tests.functions.LongRunningFunctionFactory;
import org.gearman.tests.functions.ReverseFunction;
import org.gearman.tests.functions.ReverseFunctionFactory;
import org.gearman.tests.util.IncrementalListener;
import org.gearman.tests.util.WorkerRunnable;
import org.gearman.util.ByteUtils;
import org.gearman.worker.GearmanWorker;
import org.gearman.worker.GearmanWorkerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GearmanWorkerRunner {

    GearmanWorker worker = null;
    Thread workerThread = null;
    WorkerRunnable runner = null;
    Thread wt = null;
    LongRunningFunctionFactory lrff = null;

    public void initTest() throws IOException {
        worker = new GearmanWorkerImpl();

        lrff = new LongRunningFunctionFactory();
        worker.registerFunctionFactory(lrff);
        worker.registerFunctionFactory(new ReverseFunctionFactory());
        worker.registerFunctionFactory(new IncrementalReverseFunctionFactory());
        worker.registerFunctionFactory(new FailedFunctionFactory());

        //create a worker for each of the job servers
        worker.addServer(new GearmanNIOJobServerConnection("localhost"));

        runner = new WorkerRunnable(worker);
        wt = startWorkerThread(runner, "workerThread");
    }

    
    public void exit() throws IOException {
        worker.stop();
        runner.stop();
    }



    private Thread startWorkerThread(Runnable r, String name) {
        Thread t = new Thread(r, name);
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ioe) {
        }
        return t;
    }

}
