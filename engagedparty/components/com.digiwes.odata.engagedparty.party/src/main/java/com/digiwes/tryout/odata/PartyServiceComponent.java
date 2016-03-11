package com.digiwes.tryout.odata;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;

/**
 * Created by huangxm on 3/9/2016.
 */

///**
// * @scr.component name="digiwes.servlet.dscomponent" immediate="true"
// * @scr.reference name="http.service" interface="org.osgi.service.http.HttpService"
// * cardinality="1..1" policy="dynamic"  bind="setHttpService" unbind="unsetHttpService"
// */
@Component(name = "digiwes.servlet.dscomponent", immediate = true)
public class PartyServiceComponent {

    @Reference(bind = "setHttpService",unbind = "unsetHttpService")
    private static HttpService httpServiceInstance;

    protected void activate(ComponentContext ctxt) {
        final HttpService httpService = getHttpService();
        PartyServlet digiwesServlet = new PartyServlet();
        try {
            httpService.registerServlet("/party", digiwesServlet, null, null);
        } catch (Throwable e) {
            System.out.println("Failed to activate PartyServiceComponent");
        }

        System.out.println("PartyServiceComponent is activated ");
    }

    protected void deactivate(ComponentContext ctxt) {
        System.out.println("PartyServiceComponent is deactivated ");
    }

    protected void setHttpService(HttpService httpService) {
        httpServiceInstance = httpService;
    }

    protected void unsetHttpService(HttpService httpService) {
        httpServiceInstance = null;
    }

    public static HttpService getHttpService() {
        if (httpServiceInstance == null) {
            String msg = "Before activating Digiwes Servlet bundle, an instance of "
                    + HttpService.class.getName() + " should be in existence";
            throw new RuntimeException(msg);
        }
        return httpServiceInstance;
    }
}