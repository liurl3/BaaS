/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.digiwes.tryout.odata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlAction;
import org.apache.olingo.commons.api.edm.provider.CsdlActionImport;
import org.apache.olingo.commons.api.edm.provider.CsdlComplexType;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlFunction;
import org.apache.olingo.commons.api.edm.provider.CsdlFunctionImport;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.provider.CsdlParameter;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlReturnType;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

public class PartyEdmProvider extends CsdlAbstractEdmProvider {
  // Service Namespace
  public static final String NAMESPACE = "com.digiwes.odata.sample";

  // EDM Container
  public static final String CONTAINER_NAME = "PartyCoreService";
  public static final FullQualifiedName CONTAINER_FQN = new FullQualifiedName(
      NAMESPACE, CONTAINER_NAME);

  // Entity Set Names
  public static final String ES_INDIVIDUAL_NAME = "Individuals";
  public static final String ES_PARTY_NAME = "Partys";

  // Entity Types Names
  public static final FullQualifiedName ET_INDIVIDUAL = new FullQualifiedName(
      NAMESPACE, "Individual");
  public static final FullQualifiedName ET_PARTY = new FullQualifiedName(
      NAMESPACE, "Party");
  public static final FullQualifiedName ET_INDIVIDUALNAME = new FullQualifiedName(
      NAMESPACE, "IndividualName");

  // Complex Type Names
  public static final FullQualifiedName CT_TIMEPERIOD = new FullQualifiedName(
      NAMESPACE, "TimePeriod");

  public static final String ACTION_RESET = "Reset";
  public static final FullQualifiedName ACTION_RESET_FQN = new FullQualifiedName(
      NAMESPACE, ACTION_RESET);

  // Function
  public static final String FUNCTION_GETCHINESENAME = "GetChineseName";
  public static final FullQualifiedName FUNCTION_GETCHINESENAME_FQN = new FullQualifiedName(
      NAMESPACE, FUNCTION_GETCHINESENAME);

  // Function/Action Parameters
  public static final String PARAMETER_AMOUNT = "Amount";

  @Override
  public CsdlEntityType getEntityType(final FullQualifiedName entityTypeName)
      throws ODataException {
    if (ET_INDIVIDUAL.equals(entityTypeName)) {
      return new CsdlEntityType()
          .setBaseType(ET_PARTY)
          .setName(ET_INDIVIDUAL.getName())
          .setKey(Arrays.asList(new
                  CsdlPropertyRef().setName("partyId")))
          .setProperties(
                  Arrays.asList(
//                   new
//                   CsdlProperty().setName("partyId").setType(
//                           EdmPrimitiveTypeKind.String
//                                   .getFullQualifiedName()).setNullable(true),
                          new CsdlProperty().setName("nationality").setType(
                                  EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true),
                          new CsdlProperty()
                                  .setName("gender")
                                  .setType(
                                          EdmPrimitiveTypeKind.String.getFullQualifiedName())
                                  .setNullable(true),
                          new CsdlProperty().setName("_defaultIndividualName").setType(ET_INDIVIDUALNAME)
                                  .setNullable(true),
                          new CsdlProperty().setName("_optionalIndividualName")
                                  .setType(ET_INDIVIDUALNAME).setCollection(true)
                  )
          )
          .setNavigationProperties(
                  Arrays.asList(
                          new CsdlNavigationProperty().setName("_defaultIndividualName")
                                  .setType(ET_INDIVIDUALNAME).setNullable(true), //TODO
                          new CsdlNavigationProperty().setName("_optionalIndividualName")
                                  .setType(ET_INDIVIDUALNAME).setCollection(true)
                  ));

    } else if (ET_INDIVIDUALNAME.equals(entityTypeName)) {
      return new CsdlEntityType()
          .setName(ET_INDIVIDUALNAME.getName())
          .setKey(Arrays.asList(new CsdlPropertyRef().setName("formattedName")))
          .setProperties(
              Arrays.asList(
                      new CsdlProperty()
                              .setName("formattedName")
                              .setType(
                                      EdmPrimitiveTypeKind.String.getFullQualifiedName())
                              .setNullable(true),
                      new CsdlProperty().setName("formOfAddress").setType(
                              EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true),
                      new CsdlProperty().setName("familyNames").setType(
                              EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true),
                      new CsdlProperty().setName("givenNames").setType(
                              EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true),
                      new CsdlProperty().setName("middleNames").setType(
                              EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true)));
//          .setNavigationProperties(
//              Arrays.asList(new CsdlNavigationProperty().setName("Person")
//                  .setType(ET_INDIVIDUAL).setPartner("OptName")));
    } else if (ET_PARTY.equals(entityTypeName)) {
      return new CsdlEntityType()
          .setName(ET_PARTY.getName())
          .setKey(Arrays.asList(new CsdlPropertyRef().setName("partyId")))
          .setProperties(
              Arrays.asList(
                  new CsdlProperty()
                      .setName("partyId")
                      .setType(
                          EdmPrimitiveTypeKind.String.getFullQualifiedName())
                      .setNullable(false),
                  new CsdlProperty().setName("validFor").setType(CT_TIMEPERIOD)
                      .setNullable(true)));
    }

    return null;
  }

  @Override
  public List<CsdlFunction> getFunctions(final FullQualifiedName functionName) {
    if (functionName.equals(FUNCTION_GETCHINESENAME_FQN)) {
      // It is allowed to overload functions, so we have to provide a list
      // of functions for each function name
      final List<CsdlFunction> functions = new ArrayList<CsdlFunction>();

      // Create the parameter for the function
      final CsdlParameter parameterPartyId = new CsdlParameter();
      parameterPartyId.setName("partyID");
      parameterPartyId.setNullable(false);
      parameterPartyId
          .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

      // Create the return type of the function
      final CsdlReturnType returnType = new CsdlReturnType();
      returnType.setCollection(false);
      returnType.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

      // Create the function
      final CsdlFunction function = new CsdlFunction();
      function.setName(FUNCTION_GETCHINESENAME_FQN.getName())
          .setParameters(Arrays.asList(parameterPartyId))
          .setReturnType(returnType);
      functions.add(function);

      return functions;
    }

    return null;
  }

  public CsdlComplexType getComplexType(final FullQualifiedName complexTypeName)
      throws ODataException {
    if (CT_TIMEPERIOD.equals(complexTypeName)) {
      return new CsdlComplexType().setName(CT_TIMEPERIOD.getName())
          .setProperties(
              Arrays.asList(
                  new CsdlProperty().setName("startDateTime").setType(
                          EdmPrimitiveTypeKind.Date
                          .getFullQualifiedName()),
                  new CsdlProperty().setName("endDateTime").setType(
                      EdmPrimitiveTypeKind.Date
                          .getFullQualifiedName())));
    } else if (ET_INDIVIDUALNAME.equals(complexTypeName)) {
        return new CsdlComplexType().setName(CT_TIMEPERIOD.getName())
                .setProperties(
                        Arrays.asList(
                                new CsdlProperty()
                                        .setName("formattedName")
                                        .setType(
                                                EdmPrimitiveTypeKind.String.getFullQualifiedName())
                                        .setNullable(true),
                                new CsdlProperty().setName("formOfAddress").setType(
                                        EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true),
                                new CsdlProperty().setName("familyNames").setType(
                                        EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true),
                                new CsdlProperty().setName("givenNames").setType(
                                        EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true),
                                new CsdlProperty().setName("middleNames").setType(
                                        EdmPrimitiveTypeKind.String.getFullQualifiedName()).setNullable(true)));

    }
    return null;
  }

  @Override
  public CsdlFunctionImport getFunctionImport(
      FullQualifiedName entityContainer, String functionImportName) {
    if (CONTAINER_FQN.equals(entityContainer)) {
      if (functionImportName.equals(FUNCTION_GETCHINESENAME_FQN.getName())) {
        return new CsdlFunctionImport().setName(functionImportName)
            .setFunction(FUNCTION_GETCHINESENAME_FQN)
//            .setEntitySet(ES_INDIVIDUAL_NAME) // TODO:
            // ES_CATEGORIES_NAME)
//            .setIncludeInServiceDocument(true)
            ;
      }
    }
    return null;
  }

  @Override
  public CsdlEntitySet getEntitySet(final FullQualifiedName entityContainer,
      final String entitySetName) throws ODataException {
    if (CONTAINER_FQN.equals(entityContainer)) {
      if (ES_INDIVIDUAL_NAME.equals(entitySetName)) {
        return new CsdlEntitySet()
            .setName(ES_INDIVIDUAL_NAME)
            .setType(ET_INDIVIDUAL)
            .setNavigationPropertyBindings(
                Arrays.asList(new CsdlNavigationPropertyBinding().setPath(
                    "IndividualName").setTarget(
                    CONTAINER_FQN.getFullQualifiedNameAsString() + "."
                        + ES_PARTY_NAME)));
      } else if (ES_PARTY_NAME.equals(entitySetName)) {
        return new CsdlEntitySet()
            .setName(ES_PARTY_NAME)
            .setType(ET_PARTY);
      }
    }

    return null;
  }

  @Override
  public List<CsdlAction> getActions(final FullQualifiedName actionName) {
    if (actionName.equals(ACTION_RESET_FQN)) {
      // It is allowed to overload actions, so we have to provide a list
      // of Actions for each action name
      final List<CsdlAction> actions = new ArrayList<CsdlAction>();

      // Create parameters
      final List<CsdlParameter> parameters = new ArrayList<CsdlParameter>();
      final CsdlParameter parameter = new CsdlParameter();
      parameter.setName(PARAMETER_AMOUNT);
      parameter.setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
      parameters.add(parameter);

      // Create the Csdl Action
      final CsdlAction action = new CsdlAction();
      action.setName(ACTION_RESET_FQN.getName());
      action.setParameters(parameters);
      actions.add(action);

      return actions;
    }

    return null;
  }

  @Override
  public CsdlActionImport getActionImport(
      final FullQualifiedName entityContainer, final String actionImportName) {
    if (CONTAINER_FQN.equals(entityContainer)) {
      if (actionImportName.equals(ACTION_RESET_FQN.getName())) {
        return new CsdlActionImport().setName(actionImportName).setAction(
            ACTION_RESET_FQN);
      }
    }

    return null;
  }

  @Override
  public List<CsdlSchema> getSchemas() throws ODataException {
    List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
    CsdlSchema schema = new CsdlSchema();
    schema.setNamespace(NAMESPACE);
    // EntityTypes
    List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
    entityTypes.add(getEntityType(ET_INDIVIDUAL));
    entityTypes.add(getEntityType(ET_INDIVIDUALNAME));
    entityTypes.add(getEntityType(ET_PARTY));
    schema.setEntityTypes(entityTypes);

    // ComplexTypes
    List<CsdlComplexType> complexTypes = new ArrayList<CsdlComplexType>();
    complexTypes.add(getComplexType(CT_TIMEPERIOD));
    schema.setComplexTypes(complexTypes);

    // add actions
    List<CsdlAction> actions = new ArrayList<CsdlAction>();
    actions.addAll(getActions(ACTION_RESET_FQN));
    schema.setActions(actions);

    // add functions
    List<CsdlFunction> functions = new ArrayList<CsdlFunction>();
    functions.addAll(getFunctions(FUNCTION_GETCHINESENAME_FQN));
    schema.setFunctions(functions);

    // EntityContainer
    schema.setEntityContainer(getEntityContainer());
    schemas.add(schema);

    return schemas;
  }

  @Override
  public CsdlEntityContainer getEntityContainer() throws ODataException {
    CsdlEntityContainer container = new CsdlEntityContainer();
    container.setName(CONTAINER_FQN.getName());

    // EntitySets
    List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
    container.setEntitySets(entitySets);
    entitySets.add(getEntitySet(CONTAINER_FQN, ES_INDIVIDUAL_NAME));
    entitySets.add(getEntitySet(CONTAINER_FQN, ES_PARTY_NAME));

    // Create function imports
    List<CsdlFunctionImport> functionImports = new ArrayList<CsdlFunctionImport>();
    functionImports.add(getFunctionImport(CONTAINER_FQN,
        FUNCTION_GETCHINESENAME));
    container.setFunctionImports(functionImports);

    // Create action imports
    List<CsdlActionImport> actionImports = new ArrayList<CsdlActionImport>();
    actionImports.add(getActionImport(CONTAINER_FQN, ACTION_RESET));
    container.setActionImports(actionImports);

    return container;
  }

  @Override
  public CsdlEntityContainerInfo getEntityContainerInfo(
      final FullQualifiedName entityContainerName) throws ODataException {
    if (entityContainerName == null
        || CONTAINER_FQN.equals(entityContainerName)) {
      return new CsdlEntityContainerInfo().setContainerName(CONTAINER_FQN);
    }
    return null;
  }
}
