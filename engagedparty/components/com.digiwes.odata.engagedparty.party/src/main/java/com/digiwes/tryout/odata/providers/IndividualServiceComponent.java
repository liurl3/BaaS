package com.digiwes.tryout.odata.providers;

import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;
import com.digiwes.tryout.odata.IDataProvider;
import com.digiwes.tryout.odata.PartyServlet;
import com.digiwes.tryout.odata.resource.IndividualResource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;

import java.util.Map;

/**
 * Created by nisx on 16-3-10.
 */
///**
// * @scr.component name="digiwes.servlet.individualcomponent" immediate="true"
// * @scr.reference name="individual.resource" interface="com.digiwes.tryout.odata.resource.IndividualResource"
// * cardinality="1..1" policy="dynamic"  bind="bindindividualResourceInstance" unbind="unbindindividualResourceInstance"
// * @scr.reference name="individual.factory" interface="com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualFactory"
// * cardinality="1..1" policy="dynamic"  bind="setIndividualFactory" unbind="unsetIndividualFactory"
// *
// */
@Component(name = "IndividualServiceComponent", immediate = true)
public class IndividualServiceComponent {

    protected void activate(ComponentContext ctxt) {

        System.out.println("IndividualServiceComponent is activated ");
    }

    protected void deactivate(ComponentContext ctxt) {
        System.out.println("IndividualServiceComponent is deactivated ");
    }

    @Reference(bind = "setIndividualResource", unbind = "unsetIndividualResource")
    private static IndividualResource individualResource;

    protected void setIndividualResource(IndividualResource individualResource) {
        System.out.println("setIndividualResource");

        this.individualResource = individualResource;
    }

    protected void unsetIndividualResource(IndividualResource individualResource) {
        System.out.println("unsetIndividualResource");

        this.individualResource = null;
    }
    public static IndividualResource getIndividualResource() {
        return individualResource;
    }
}
