package com.digiwes.tryout.odata;

import com.digiwes.frameworx.engagedparty.resource.api.IIndividualResource;
import com.digiwes.frameworx.engagedparty.resource.api.IResource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.olingo.commons.api.data.Entity;

/**
 * Created by nisx on 16-3-18.
 */
@Component(name = "ServiceFactory", immediate = true)
public class ServiceFactory {
    @Reference(bind = "setIndividualResource", unbind = "unsetIndividualResource",referenceInterface = IIndividualResource.class)
    private static IIndividualResource<Entity> individualResource;
    public void setIndividualResource(IIndividualResource individualResourceParam) {
        individualResource = individualResourceParam;
    }
    public void unsetIndividualResource(IIndividualResource individualResourceParam) {
        individualResource = null;
    }
    public static IResource<Entity> getResource(String name) throws DataProviderException {
        if (PartyEdmProvider.ES_INDIVIDUAL_NAME.equals(name)) {
            return individualResource;
        }
        throw new DataProviderException("Not support the resource current. ResourceName=[" + name +"]");
    }
}
