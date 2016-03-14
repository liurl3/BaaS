package com.digiwes.tryout.odata.providers;

import com.digiwes.tryout.odata.DataProviderException;
import com.digiwes.tryout.odata.IDataProvider;
import com.digiwes.tryout.odata.resource.IndividualResource;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;

/**
 * Created by nisx on 16-3-10.
 */
public class IndividualDataProvider implements IDataProvider {
    private IndividualResource individualResource = IndividualServiceComponent.getIndividualResource();
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