package com.digiwes.tryout.odata.providers;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.tryout.odata.DataProviderException;
import com.digiwes.tryout.odata.IDataProvider;
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
        IndividualQueryService service = IndividualServiceComponent.getIndividualQueryService();

        String partyId = keys.get(0).getText();

        Individual individualInfo = service.retrieveIndividualById(partyId);
        //translate individualInfo to Entity
        try {
            return convertBean(individualInfo);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw new DataProviderException("convert data error", e);
        }

    }

    private Entity convertBean(Object bean) throws IntrospectionException {
        Class type = bean.getClass();
        Entity entity = new Entity();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Object value = getPropertyValue(bean, propertyName);
            Property property = null;
            if (value instanceof TimePeriod) {
                property = createComplexProperty(propertyName, value);
            } else if (value instanceof String) {
                property = createPrimitive(propertyName,value);
            } else if (value instanceof IndividualName) {
                property = createComplexProperty(propertyName, value);
            } else if (value instanceof List) {
                //property =
            }
            entity.addProperty(property);
        }
        return entity;
    }

    private Property createComplexProperty(String name, Object bean) throws IntrospectionException {
        ComplexValue complexValue=new ComplexValue();
        List<Property> addressProperties = complexValue.getValue();
        Class type = bean.getClass();
        Entity entity = new Entity();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Object value = getPropertyValue(bean, propertyName);
            complexValue.getValue().add(createPrimitive(propertyName, value));
        }
        return  new Property(null,name,ValueType.COMPLEX, complexValue);
    }
    private Property createPrimitive(final String name, final Object value) {
        return new Property(null, name, ValueType.PRIMITIVE, value);
    }
    private Object getPropertyValue(Object bean, String propertyName) {
        String firstLetter = propertyName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + propertyName.substring(1);

        try {
            Method method = bean.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(bean, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}