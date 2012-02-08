package edu.htw.magw.dreamcatcher.services.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.htw.magw.dreamcatcher.domain.dao.DataDao;
import edu.htw.magw.dreamcatcher.domain.dao.DataValueDao;
import edu.htw.magw.dreamcatcher.domain.dao.DeviceDao;
import edu.htw.magw.dreamcatcher.domain.dao.SeriesDao;
import edu.htw.magw.dreamcatcher.domain.entity.DataValue;
import edu.htw.magw.dreamcatcher.domain.entity.Device;
import edu.htw.magw.dreamcatcher.domain.entity.MeasurementData;
import edu.htw.magw.dreamcatcher.domain.entity.Series;

@Path("/data")
@Component
public class DataService {

	List<String> keyList = new ArrayList<String>(Arrays.asList(new String[] {
			"low alpha", "theta", "heigh gamma", "strength", "heigh alpha", "delta", "low beta", "meditation", "heigh beta", "low gamma", "attention" }));

	@Autowired
	DataDao dataDao;

	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	SeriesDao seriesDao ;
	
	@Autowired
	DataValueDao dataValueDao ;

	@POST
	@Transactional
	@Consumes("application/json")
	public Response postData(String jsonString) {

		JSONParser parser = new JSONParser();
		
		dataValueDao.deleteAll() ;
		dataDao.deleteAll() ;
		seriesDao.deleteAll() ;
		deviceDao.deleteAll() ;
		
		Device device = new Device();
		device.setDeviceId(1L);
		
		deviceDao.save(device) ;
		
		try {
			Object object = parser.parse(jsonString);
			JSONArray array = (JSONArray) object;

			Series series = new Series();
			series.setDevice(device);
			device.addToSeriesList(series) ;
			
			for (Object obj : array) {
				JSONObject jsonObj = (JSONObject) obj;

				MeasurementData data = new MeasurementData();
				
				DateTime dateTime = new DateTime() ;
				dateTime.withMillis(Long.parseLong(jsonObj.get("timestamp").toString())) ;
				data.setRecordedDateTime(dateTime);
								
				series.addToMeasurementDataList(data) ;
				
				for (String key : keyList)
				{
					DataValue dataValue = new DataValue() ;
					dataValue.setMeasurementType(key) ;
					dataValue.setMeasurementValue(0d) ;
					
					if (jsonObj.get(key) != null)
						if (!((String) jsonObj.get(key)).equals(""))
							dataValue.setMeasurementValue(Double.parseDouble(jsonObj.get(key).toString())) ;
							
					data.addToDataValue(dataValue) ;
				}
			}

			seriesDao.save(device.getSeriesList()) ;
			if (device.getSeriesList() != null) {
				for (Series s : device.getSeriesList()) {
					dataDao.save(s.getMeasurementDataList());
					for (MeasurementData m : s.getMeasurementDataList()) {
						dataValueDao.save(m.getDataValueList()) ;
					}
				}
				deviceDao.save(device) ;
			}
	
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (Response.ok()).build();
	}
}
