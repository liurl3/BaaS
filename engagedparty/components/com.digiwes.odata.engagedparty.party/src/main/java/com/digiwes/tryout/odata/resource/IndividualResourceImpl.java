package com.digiwes.tryout.odata.resource;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.OptionalIndividualName;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;
import com.digiwes.tryout.odata.DataProviderException;
import com.digiwes.tryout.odata.providers.IndividualServiceComponent;
import org.apache.olingo.commons.api.data.ComplexValue;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Created by nisx on 16-3-10.
 */
public class IndividualResourceImpl implements IndividualResource {

    public Entity createParty(Entity requestEntity) throws Exception {
        //TODO 1. translate entity to individual
        Individual individual = null;
        Set<OptionalIndividualName> optionalNames;
        optionalNames = individual.get_optionalIndividualName();

        //2. create individal
        IndividualUpdateService individualUpdateService = IndividualServiceComponent.getIndividualUpdateService();
        Individual retIndividual = individualUpdateService.createIndividual(individual);

        //3. add optional name
        for (OptionalIndividualName optName : optionalNames) {
            retIndividual = individualUpdateService.addOptionalIndividualName(retIndividual, optName);
        }

        //TODO 4. translate individual to entity
        return null;
    }

    public Entity updateParty(Entity requestEntity) {
        return null;
    }

    public List<Individual> retrieveParty() {
        return null;
    }

    public Entity retrievePartyById(String partyId) throws Exception {
        IndividualQueryService service = IndividualServiceComponent.getIndividualQueryService();
        Individual individualInfo = service.retrieveIndividualById(partyId);
        //translate individualInfo to Entity
        try {
            return convertBean(individualInfo);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean delParty(String partyId) {
        IndividualUpdateService individualUpdateService = IndividualServiceComponent.getIndividualUpdateService();
        return individualUpdateService.delIndividual(partyId);
    }

    private Entity convertBean(Object bean) throws IntrospectionException {
        if (null == bean) {
            return null;
        }
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
        return  new Property(null,name, ValueType.COMPLEX, complexValue);
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
