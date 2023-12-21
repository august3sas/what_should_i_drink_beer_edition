package com.sample;

import java.util.ArrayList;
import javax.swing.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import org.kie.api.logger.KieRuntimeLogger;

public class DroolsTest{
	
    public static final void main(String[] args) {
        
        KieServices ks = KieServices.Factory.get();
    	KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");
        
        GUI ui = new GUI(kSession);
        ui.setVisible(true);
        kSession.insert(ui);
        kSession.fireAllRules();
    }
}
