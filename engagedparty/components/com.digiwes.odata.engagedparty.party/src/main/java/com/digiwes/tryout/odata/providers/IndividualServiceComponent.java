package com.digiwes.tryout.odata.providers;

import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;
import com.digiwes.tryout.odata.resource.IndividualResource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;

/**
 * Created by nisx on 16-3-10.
 */
@Component
public class IndividualServiceComponent {

    @Reference
    private static IndividualQueryService indvQryServiceInstance;
    @Reference
    private static IndividualUpdateService indvUpdServiceInstance;

    private static IndividualResource individualResourceInstance;

    protected void setIndividualQueryService(IndividualQueryService individualQueryService) {
        indvQryServiceInstance = individualQueryService;
    }

    protected void unsetIndividualQueryService(IndividualQueryService individualQueryService) {
        indvQryServiceInstance = null;
    }

    protected void setIndividualUpdateService(IndividualUpdateService individualUpdateService) {
        indvUpdServiceInstance = individualUpdateService;
    }

    protected void unsetIndividualUpdateService(IndividualUpdateService individualUpdateService) {
        indvUpdServiceInstance = null;
    }
    protected void setIndividualResource(IndividualResource individualResource) {
        individualResourceInstance = individualResource;
    }

    protected void unsetIndividualResource(IndividualResource individualResource) {
        individualResourceInstance = null;
    }
    public static IndividualQueryService getIndividualQueryService() {
        return indvQryServiceInstance;
    }

    public static IndividualUpdateService getIndividualUpdateService() {
        return indvUpdServiceInstance;
    }

    public static IndividualResource getIndividualResource() {
        return individualResourceInstance;
    }
}
