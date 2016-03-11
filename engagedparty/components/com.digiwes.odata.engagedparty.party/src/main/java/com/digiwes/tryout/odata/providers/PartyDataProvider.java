package com.digiwes.tryout.odata.providers;

import com.digiwes.tryout.odata.DataProviderException;
import com.digiwes.tryout.odata.IDataProvider;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;

/**
 * Created by nisx on 16-3-10.
 */
public class PartyDataProvider implements IDataProvider {

    public EntityCollection readAll(final EdmEntitySet edmEntitySet) throws DataProviderException {
        return null;
    }

    public Entity read(EdmEntitySet edmEntitySet, List<UriParameter> keys) throws DataProviderException {
        return null;
    }

    public Entity create(EdmEntitySet edmEntitySet, Entity requestEntity) throws DataProviderException {
        return null;
    }
}
