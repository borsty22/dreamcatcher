/* 
 * Copyright 2009 IT Mill Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.htw.magw.dreamcatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.terminal.gwt.server.HttpServletRequestListener;

import edu.htw.magw.dreamcatcher.domain.dao.DeviceDao;
import edu.htw.magw.dreamcatcher.domain.entity.DataValue;
import edu.htw.magw.dreamcatcher.domain.entity.Device;
import edu.htw.magw.dreamcatcher.domain.entity.MeasurementData;
import edu.htw.magw.dreamcatcher.domain.entity.Series;

@SuppressWarnings("serial")
@Configurable
public class DreamcatcherWebApplication extends com.vaadin.Application
		implements HttpServletRequestListener {

	@Autowired
	DeviceDao deviceDao;

	@Override
	public void init() {

		createExampleData() ;

		Device device = deviceDao.findByDeviceId(1l);

		if (device != null) {
			Map<String, List<Value>> valueMap = new HashMap<String, List<Value>>();

			DateTime startDateTime = null;

			for (Series series : device.getSeriesList()) {
				for (MeasurementData data : series.getMeasurementDataList()) {
					if (startDateTime == null
							|| (startDateTime.isBefore(data.getRecordedDateTime())))
						startDateTime = data.getRecordedDateTime();

					for (DataValue dataValue : data.getDataValueList()) {
						if (valueMap.get(dataValue.getMeasurementType()) == null)
							valueMap.put(dataValue.getMeasurementType(), new ArrayList<Value>());

						valueMap.get(dataValue.getMeasurementType())
								.add(new Value(dataValue.getMeasurementValue(), data
										.getRecordedDateTime()));
					}
				}
			}
			setMainWindow(new DreamcatcherChartWindow(valueMap, startDateTime));
		}
		else
			setMainWindow(new DreamcatcherChartWindow(new HashMap<String, List<Value>>(), new DateTime()));
	}

	private Boolean isAppRunningOnGAE;

	public boolean isAppRunningOnGAE() {
		if (isAppRunningOnGAE == null) {
			return false;
		}
		return isAppRunningOnGAE;
	}

	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		if (isAppRunningOnGAE == null) {
			isAppRunningOnGAE = false;
			String serverInfo = request.getSession().getServletContext()
					.getServerInfo();
			if (serverInfo != null && serverInfo.contains("Google")) {
				isAppRunningOnGAE = true;
			}
		}
	}

	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {

	}

	@SuppressWarnings("unchecked")
	private void createExampleData() {
		List<String> keyList = new ArrayList<String>(
				Arrays.asList(new String[] { "low alpha", "theta",
						"heigh gamma", "strength", "heigh alpha", "delta",
						"low beta", "meditation", "heigh beta", "low gamma",
						"attention" }));

		JSONArray list = new JSONArray();

		Map<String, String> obj = null;
		Map<String, String> lastObj = null;
		
		DateTime startDateTime = new DateTime(2012, 1, 1, 22, 0);

		for (int i = 0; i < 1000; i++) {
			obj = new LinkedHashMap<String, String>();
			
			obj.put("timestamp",
					String.valueOf(startDateTime.getMinuteOfDay() + (i * 10)));

			for (String key : keyList) {
				if (lastObj != null) {
					if (lastObj.get(key) == null) 
						obj.put(key, String.valueOf(Math.random() * (15000 - 1) + 1));
					else
						obj.put(key, String.valueOf(Double.parseDouble(lastObj.get(key)) + Math.random() * (200 - 1) - 100));
				}
			}
			
			lastObj = obj ;
			list.add(obj);
		}

		System.out.println(list.toJSONString());
	}
}
