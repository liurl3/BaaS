package com.digiwes.tryout.odata.providers;

import com.digiwes.tryout.odata.DataProviderException;
import com.digiwes.tryout.odata.IDataProvider;
import com.digiwes.tryout.odata.resource.IndividualResource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.server.api.uri.UriParameter;
import org.osgi.service.component.ComponentContext;

import java.util.List;

/**
 * Created by nisx on 16-3-10.
 */
///**
// * @scr.component name="digiwes.individual.provider" immediate="true"
// * @scr.reference name="individual.resource" interface="com.digiwes.tryout.odata.resource.IndividualResource"
// * cardinality="1..1" policy="dynamic"  bind="bindindividualResourceInstance" unbind="unbindindividualResourceInstance"
// * @src.
// */
//@Component(name = "digiwes.individual.provide", immediate = true)
//@Service(IDataProvider.class)
public class IndividualDataProvider implements IDataProvider {
//    protected void activate(ComponentContext ctxt) {
//
//        System.out.println("digiwes.individual.provide is activated ");
//    }
//
//    protected void deactivate(ComponentContext ctxt) {
//        System.out.println("digiwes.individual.provide is deactivated ");
//    }
//    @Reference(bind = "bindindividualResourceInstance",unbind = "unbindindividualResourceInstance")
    private IndividualResource individualResource = IndividualServiceComponent.getIndividualResource();
//    protected void bindindividualResourceInstance(IndividualResource individualResource) {
//        System.out.println("bindindividualResourceInstance");
//
//        this.individualResource = individualResource;
//    }
//
//    protected void unbindindividualResourceInstance(IndividualResource individualResource) {
//        System.out.println("unbindindividualResourceInstance");
//
//        this.individualResource = null;
//    }
    public EntityCollection readAll(final EdmEntitySet edmEntitySet) throws DataProviderException {
//        throw new DataProviderException("Read all Individual is not support current.");
        return new EntityCollection();
    }

    public Entity read(EdmEntitySet edmEntitySet, List<UriParameter> keys) throws DataProviderException {
        final EdmEntityType entityType = edmEntitySet.getEntityType();

        if (null == keys || keys.isEmpty()) {
            return null;
        }
        String partyId = keys.get(0).getText();
        if (null != partyId) {
            partyId = partyId.replaceAll("'", "");
        }
        System.out.println("PartyId=[" + partyId +"]");
        try {
            return  individualResource.retrievePartyById(partyId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProviderException("Retrieve  data error", e);
        }

    }

    public Entity create(EdmEntitySet edmEntitySet, Entity requestEntity) throws DataProviderException {

        try {
            return individualResource.createParty(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProviderException("Create data error", e);
        }
    }


}