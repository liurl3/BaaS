package com.digiwes.frameworx.engagedparty.resource.api;

import java.util.List;
import java.util.Map;

/**
 * Created by nisx on 16-3-18.
 */
public interface IResource<T> {
    public T create(T inData) throws Exception;
    public T retrieveByKey(Map<String,String> keyParam) throws Exception;
    public boolean delelete(Map<String,String> keyParam) throws Exception;
    public T update(T inData) throws Exception;
    public List<T> retrieve() throws Exception;

}
