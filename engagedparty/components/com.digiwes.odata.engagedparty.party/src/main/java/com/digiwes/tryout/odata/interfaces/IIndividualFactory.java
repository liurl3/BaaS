package com.digiwes.tryout.odata.interfaces;

import com.digiwes.frameworx.engagedparty.party.api.bean.Individual;
import org.apache.olingo.commons.api.data.Entity;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

public interface IIndividualFactory{
	
	public Individual convertEntity(Entity entity) throws IntrospectionException, InvocationTargetException, IllegalAccessException;
	
	public Entity convertBean(Individual individual) throws IntrospectionException;
	
}