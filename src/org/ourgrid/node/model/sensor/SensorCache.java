package org.ourgrid.node.model.sensor;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Period;

public class SensorCache {
	
	private long collectionIntervalTimeMs;
	private int historySize;
	private boolean initialized;
	private boolean suspendPolling;
	private int usedResources;
	private Period lastPolled;
	private Period intervalPolled;
	private List<SensorResource> sensorResources;
	
	public SensorCache() {
		this.collectionIntervalTimeMs = 0;
		this.historySize = 0;
		this.lastPolled = Period.ZERO;
		this.intervalPolled = Period.ZERO;
		this.sensorResources = new ArrayList<SensorResource>();
		this.initialized = true;
	}
	
	public long getCollectionIntervalTimeMs() {
		return collectionIntervalTimeMs;
	}
	public void setCollectionIntervalTimeMs(long collectionIntervalTimeMs) {
		this.collectionIntervalTimeMs = collectionIntervalTimeMs;
	}
	public int getHistorySize() {
		return historySize;
	}
	public void setHistorySize(int historySize) {
		this.historySize = historySize;
	}
	public boolean isInitialized() {
		return initialized;
	}
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	public boolean isSuspendPolling() {
		return suspendPolling;
	}
	public void setSuspendPolling(boolean suspendPolling) {
		this.suspendPolling = suspendPolling;
	}
	public int getUsedResources() {
		return usedResources;
	}
	public void setUsedResources(int usedResources) {
		this.usedResources = usedResources;
	}
	public Period getLastPolled() {
		return lastPolled;
	}
	public void setLastPolled(Period lastPolled) {
		this.lastPolled = lastPolled;
	}
	public Period getIntervalPolled() {
		return intervalPolled;
	}
	public void setIntervalPolled(Period intervalPolled) {
		this.intervalPolled = intervalPolled;
	}
	public List<SensorResource> getSensorResources() {
		return sensorResources;
	}
	public void setSensorResources(List<SensorResource> sensorResources) {
		this.sensorResources = sensorResources;
	}

	public void addResource(SensorResource sensorResource) {
		sensorResources.add(sensorResource);
	}
}
