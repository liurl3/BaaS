package com.digiwes.tryout.odata.resource;

import com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualFactory;
import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.OptionalIndividualName;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;
import com.digiwes.tryout.odata.providers.IndividualServiceComponent;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.olingo.commons.api.data.Entity;
import org.osgi.service.component.ComponentContext;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Set;

/**
 * Created by nisx on 16-3-10.
 */
@Component(name = "individual.resource", immediate = true)
@Service(value = IndividualResource.class)
///**
// * @scr.component name="digiwes.servlet.individualresource" immediate="true"
// *
// * @scr.reference name="individual.query" interface="com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService"
// * cardinality="1..1" policy="dynamic"  bind="bindindvQryServiceInstance" unbind="unbindindvQryServiceInstance"
// * @scr.reference name="individual.update" interface="com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService"
// * cardinality="1..1" policy="dynamic"  bind="bindindvUpdServiceInstance" unbind="unbindindvUpdServiceInstance"
// * @scr.reference name="individual.factory" interface="com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualFactory"
// * cardinality="1..1" policy="dynamic"  bind="setIndividualFactory" unbind="unsetIndividualFactory"
// */
public class IndividualResourceImpl implements IndividualResource {
    protected void activate(ComponentContext ctxt) {

        System.out.println("IndividualResourceImpl is activated ");
    }

    protected void deactivate(ComponentContext ctxt) {
        System.out.println("IndividualResourceImpl is deactivated ");
    }
    @Reference(bind = "bindindvQryServiceInstance",unbind = "unbindindvQryServiceInstance",referenceInterface = IndividualQueryService.class)
    private IndividualQueryService individualQueryService;
    @Reference(bind = "bindindvUpdServiceInstance",unbind = "unbindindvUpdServiceInstance", referenceInterface=IndividualUpdateService.class)
    private IndividualUpdateService individualUpdateService;
    protected void bindindvQryServiceInstance(IndividualQueryService individualQueryService) {
        System.out.println("bindindvQryServiceInstance");
        this.individualQueryService = individualQueryService;
    }

    protected void unbindindvQryServiceInstance(IndividualQueryService individualQueryService) {
        System.out.println("unbindindvQryServiceInstance");
        this.individualQueryService = null;
    }

    protected void bindindvUpdServiceInstance(IndividualUpdateService individualUpdateService) {
        System.out.println("bindindvUpdServiceInstance");

        this.individualUpdateService = individualUpdateService;
    }

    protected void unbindindvUpdServiceInstance(IndividualUpdateService individualUpdateService) {
        System.out.println("unbindindvUpdServiceInstance");

        this.individualUpdateService = null;
    }
    @Reference(bind = "setIndividualFactory",unbind = "unsetIndividualFactory", referenceInterface = IndividualFactory.class, name = "IndividualResource")
    private IndividualFactory individualFactory;

    protected void setIndividualFactory(IndividualFactory indivFactory){
        System.out.println("setIndividualFactory");

        individualFactory = indivFactory;
    }

    protected void unsetIndividualFactory(IndividualFactory indivFactory){
        System.out.println("unsetIndividualFactory");

        individualFactory = null;
    }

    public Entity createParty(Entity requestEntity) throws Exception {
        //1. translate entity to individual
        //Individual individual = null;
        Individual individual = individualFactory.convertEntity(requestEntity);
        Set<OptionalIndividualName> optionalNames;
        optionalNames = individual.get_optionalIndividualName();

        //2. create individal
        Individual retIndividual = individualUpdateService.createIndividual(individual);

        //3. add optional name
        for (OptionalIndividualName optName : optionalNames) {
            retIndividual = individualUpdateService.addOptionalIndividualName(retIndividual, optName);
        }

        //4. translate individual to entity
        Entity rtnEntity = individualFactory.convertBean(retIndividual);
        return rtnEntity;
    }

    public Entity updateParty(Entity requestEntity) {

        return null;
    }

    public List<Individual> retrieveParty() {
        return null;
    }

    public Entity retrievePartyById(String partyId) throws Exception {
        Individual individualInfo = individualQueryService.retrieveIndividualById(partyId);
        //translate individualInfo to Entity
        try {
            //return convertBean(individualInfo);
            return individualFactory.convertBean(individualInfo);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean delParty(String partyId) {
        return individualUpdateService.delIndividual(partyId);
    }

    /*private Entity convertBean(Object bean) throws IntrospectionException {
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
    }*/
}
