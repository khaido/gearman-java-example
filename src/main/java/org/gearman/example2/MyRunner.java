/*
 * Copyright (C) 2009 by Eric Lambert <Eric.Lambert@sun.com>
 * Use and distribution licensed under the BSD license.  See
 * the COPYING file in the parent directory for full text.
 */
package org.gearman.example2;


public class MyRunner {


    @SuppressWarnings(value = "unchecked")
    public MyRunner() {
    	
    }

    @SuppressWarnings(value = "unchecked")
    public static void main(String[] args) throws Exception {
    	JenkinsWorker jw = new JenkinsWorker();
    	jw.work();
    	Thread.sleep(8000);
    	jw.stop();
    }

}
