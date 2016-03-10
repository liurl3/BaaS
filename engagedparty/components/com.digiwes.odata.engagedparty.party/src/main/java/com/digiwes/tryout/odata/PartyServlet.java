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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digiwes.tryout.odata.providers.IndividualDataProvider;
import com.digiwes.tryout.odata.providers.PartyDataProvider;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.edmx.EdmxReference;

public class PartyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		try {
			HttpSession session = req.getSession(true);
//			DataProvider dataProvider = (DataProvider) session
//					.getAttribute(DataProvider.class.getName());
//			if (dataProvider == null) {
//				dataProvider = new DataProvider();
//				session.setAttribute(DataProvider.class.getName(), dataProvider);
//				System.out.println("Created new data provider.");
//			}
			Map<String, IDataProvider> dataProviderMap = (Map) session.getAttribute("DataProviderMap");
			if (null == dataProviderMap) {
				dataProviderMap = new HashMap<String, IDataProvider>();
				dataProviderMap.put(PartyEdmProvider.ES_PARTY_NAME, new PartyDataProvider());
				dataProviderMap.put(PartyEdmProvider.ES_INDIVIDUAL_NAME, new IndividualDataProvider());
				session.setAttribute("DataProviderMap", dataProviderMap);
			}


			OData odata = OData.newInstance();
			ServiceMetadata edm = odata.createServiceMetadata(
					new PartyEdmProvider(), new ArrayList<EdmxReference>());
			ODataHttpHandler handler = odata.createHandler(edm);
			handler.register(new PartyProcessor(dataProviderMap));
			handler.process(req, resp);
		} catch (RuntimeException e) {
			System.out.println("Server Error" + e.getMessage());
			throw new ServletException(e);
		}
	}
}
