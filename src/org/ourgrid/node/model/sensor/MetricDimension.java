package org.ourgrid.node.model.sensor;

import java.util.LinkedList;

import org.ourgrid.node.util.Sensor;

import edu.ucsb.eucalyptus.MetricDimensionsType;
import edu.ucsb.eucalyptus.MetricDimensionsValuesType;

public class MetricDimension extends MetricDimensionsType {
	
	public static enum DimensionName {
		DEFAULT("default"), TOTAL("total");
		
		private String dimensionNameStr;
		
		DimensionName(String dimensionNameStr) {
			this.dimensionNameStr = dimensionNameStr;
		}
		
		public String getDimensionNameStr() {
			return dimensionNameStr;
		}
	}
	
	public void addValue(MetricDimensionsValuesType value) {
		LinkedList<MetricDimensionsValuesType> values = 
				new LinkedList<MetricDimensionsValuesType>();
		for (MetricDimensionsValuesType eachValue : getValues()) {
			values.add(eachValue);
		}
		values.add(value);
		if (values.size() > Sensor.MAX_SENSOR_RESOURCES) {
			values.removeFirst();
		}
		MetricDimensionsValuesType[] valuesArray = values.toArray(
				new MetricDimensionsValuesType[]{});
		setValues(valuesArray);
	}
	
	public MetricDimension(String dimensionName, MetricDimensionsValuesType[] values) {
		setDimensionName(dimensionName);
		setValues(values);
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean equals(Object arg0) {
		
		if (!(arg0 instanceof MetricDimensionsType)) {
			return false;
		}
		
		MetricDimensionsType mDT = (MetricDimensionsType)arg0;

		for (int i = 0; i < mDT.getValues().length; i++) {
			MetricDimensionsValuesType mDTValue = mDT.getValues()[i];
			MetricDimensionsValuesType myValue = getValues()[i];
			
			if (!mDTValue.equals(myValue)) {
				return false;
			}
		}
		
		return mDT.getDimensionName().equals(getDimensionName());
	}
}
