package com.digiwes.tryout.odata;

import org.apache.olingo.commons.api.data.*;
import org.apache.olingo.commons.api.edm.*;
import org.apache.olingo.server.api.uri.UriParameter;

import java.util.List;

/**
 *
 * Created by nisx on 16-3-10.
 */
public interface IDataProvider {
    public EntityCollection readAll(final EdmEntitySet edmEntitySet) throws DataProviderException;

    public Entity read(final EdmEntitySet edmEntitySet, final List<UriParameter> keys) throws DataProviderException;

}
