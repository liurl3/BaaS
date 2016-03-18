package com.digiwes.frameworx.engagedparty.resource.impl;

import com.digiwes.frameworx.common.api.IResourceTransfer;
import com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualUpdateService;
import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.OptionalIndividualName;
import com.digiwes.frameworx.engagedparty.resource.api.IIndividualResource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

import java.beans.IntrospectionException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nisx on 16-3-18.
 */
@Component(name = "IndividualResource", immediate = true)
@Service(value = IIndividualResource.class)
public class IndividualResourceImpl<T> implements IIndividualResource<T> {
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
    @Reference(bind = "setIndividualTransfer",unbind = "unsetIndividualTransfer", referenceInterface = IResourceTransfer.class, name = "IResourceTransfer")
    private IResourceTransfer<T> resourceTransfer;

    protected void setIndividualTransfer(IResourceTransfer resourceTransfer){
        System.out.println("setIndividualTransfer");

        this.resourceTransfer = resourceTransfer;
    }

    protected void unsetIndividualTransfer(IResourceTransfer resourceTransfer){
        System.out.println("unsetIndividualTransfer");

        this.resourceTransfer = null;
    }
    @Override
    public T create(T inData) throws Exception {
        //1. translate entity to individual
        //Individual individual = null;
        Individual individual = resourceTransfer.transIn(inData, Individual.class);
        Set<OptionalIndividualName> optionalNames;
        optionalNames = individual.get_optionalIndividualName();
        individual.set_optionalIndividualName(new HashSet<OptionalIndividualName>());

        //2. create individal
        Individual retIndividual = individualUpdateService.createIndividual(individual);

        //3. add optional name
        for (OptionalIndividualName optName : optionalNames) {
            retIndividual = individualUpdateService.addOptionalIndividualName(retIndividual, optName);
        }

        //4. translate individual to entity
        return resourceTransfer.transOut(retIndividual);
    }
    @Override
    public T update(T inData) throws Exception {
        return null;
    }
    @Override
    public List<T> retrieve() throws Exception {
        return null;
    }

    @Override
    public boolean delelete(Map<String,String>  keyParam) throws Exception {
        if (keyParam == null) {
            return  true;
        }
        String partyId = keyParam.get("partyId");
        return individualUpdateService.delIndividual(partyId);
    }

    @Override
    public T retrieveByKey(Map<String,String> keyParam) throws Exception {
        if (keyParam == null) {
            return  null;
        }
        String partyId = keyParam.get("partyId");
        Individual individualInfo = individualQueryService.retrieveIndividualById(partyId);
        //translate individualInfo to Entity
        try {
            //return convertBean(individualInfo);
            return resourceTransfer.transOut(individualInfo);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
