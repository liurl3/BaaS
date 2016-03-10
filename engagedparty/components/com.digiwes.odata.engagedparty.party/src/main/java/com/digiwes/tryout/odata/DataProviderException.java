package com.digiwes.tryout.odata;

import org.apache.olingo.commons.api.ex.ODataException;

/**
 *
 * Created by nisx on 16-3-10.
 */
public class DataProviderException  extends ODataException {
    private static final long serialVersionUID = 5098059649321796156L;

    public DataProviderException(String message, Throwable throwable) {

        super(message, throwable);
    }

    public DataProviderException(String message) {
        super(message);
    }
}
