package com.digiwes.frameworx.common.api;

/**
 * Created by nisx on 16-3-17.
 */
public interface IResourceTransfer<S> {
    public <T> T transIn(S inParam, Class<T> tClass) throws Exception;
    public <T> S transOut(T resource) throws Exception;
}
