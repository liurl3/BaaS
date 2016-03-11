package com.digiwes.tryout.odata;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;

public class App implements BundleActivator {
    public void start(BundleContext context) {
        System.out.println("Starting Bundle.");
    }
    public void stop(BundleContext context) {
        System.out.println("Stop Bundle.");
    }
    
}