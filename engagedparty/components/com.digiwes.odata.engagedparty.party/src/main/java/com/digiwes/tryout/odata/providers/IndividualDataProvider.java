package com.digiwes.tryout.odata.providers;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.tryout.odata.DataProviderException;
import com.digiwes.tryout.odata.IDataProvider;
import com.digiwes.tryout.odata.resource.IndividualResource;
import org.apache.olingo.commons.api.data.*;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.server.api.uri.UriParameter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by nisx on 16-3-10.
 */
public class IndividualDataProvider implements IDataProvider {

    public EntityCollection readAll(final EdmEntitySet edmEntitySet) throws DataProviderException {
        throw new DataProviderException("Read all Individual is not support current.");
    }

    public Entity read(EdmEntitySet edmEntitySet, List<UriParameter> keys) throws DataProviderException {
        final EdmEntityType entityType = edmEntitySet.getEntityType();

        if (null == keys || keys.isEmpty()) {
            return null;
        }

        String partyId = keys.get(0).getText();
        IndividualResource resource = IndividualServiceComponent.getIndividualResource();

        try {
            return  resource.retrievePartyById(partyId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProviderException("Retrieve  data error", e);
        }

    }

    public Entity create(EdmEntitySet edmEntitySet, Entity requestEntity) throws DataProviderException {

        IndividualResource resource = IndividualServiceComponent.getIndividualResource();

        try {
            return resource.createParty(requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProviderException("Create data error", e);
        }
    }


}