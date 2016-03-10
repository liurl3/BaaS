package com.digiwes.tryout.odata.providers;

import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;
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
    public static IndividualQueryService getIndividualQueryService() {
        return indvQryServiceInstance;
    }

    public static IndividualUpdateService getIndividualUpdateService() {
        return indvUpdServiceInstance;
    }

}
