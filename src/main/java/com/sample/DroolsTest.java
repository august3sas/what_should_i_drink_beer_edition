package com.sample;

import java.util.ArrayList;
import javax.swing.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger;
import org.kie.api.logger.KieRuntimeLogger;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest{
	
    public static final void main(String[] args) {
        
        KieServices ks = KieServices.Factory.get();
    	KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");
        KieRuntimeLogger kLogger=ks.getLoggers().newFileLogger(kSession, "test");
        
        GUI ui = new GUI("", kSession);
        ui.setVisible(true);
        kSession.insert(ui);
        kSession.fireAllRules();

    }
    
    public static class Message {


        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    
    
}
