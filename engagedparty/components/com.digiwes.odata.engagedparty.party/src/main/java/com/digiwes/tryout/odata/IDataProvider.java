package com.digiwes.tryout.odata;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;

/**
 *
 * Created by nisx on 16-3-10.
 */
public interface IDataProvider {
    public EntityCollection readAll(final EdmEntitySet edmEntitySet) throws DataProviderException;

    public Entity read(final EdmEntitySet edmEntitySet, final List<UriParameter> keys) throws DataProviderException;

    public Entity create(EdmEntitySet edmEntitySet, Entity requestEntity) throws DataProviderException;
}
