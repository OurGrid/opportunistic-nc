package org.ourgrid.node;

import org.ourgrid.node.model.InstanceRepository;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.VirtualMachineType;

public class TestUtils {
	
	private static int instanceId = 0;
	private static int userId = 1;
	
	public static int correlationId = 0;
	public static final String INSTANCE_ID = "001"; 
	public static final String DEFAULT_USER_ID = "user001";
	public static final String VDI_EXT = ".vdi";
	
	public static InstanceType addInstanceToRepository(NodeFacade facade) {
		InstanceType instance = createBasicInstance();
				
		return addInstanceToRepository(instance, facade);
	}

	public static InstanceType addInstanceToRepository(int numCores, 
			int mem, int disk, NodeFacade facade) {
		
		VirtualMachineType vmType = new VirtualMachineType();
		vmType.setCores(numCores);
		vmType.setMemory(mem);
		vmType.setDisk(disk);
		
		InstanceType instance = createBasicInstance();
		instance.setInstanceType(vmType);
		instance.setStateName(InstanceRepository.EXTANT_STATE);
		
		return addInstanceToRepository(instance, facade);
	}
	
	private static InstanceType addInstanceToRepository(InstanceType instance,
			NodeFacade facade) {
		InstanceRepository iRep = facade.getInstanceRepository();
		iRep.addInstance(instance);
		facade.setInstanceRepository(iRep);
		
		return instance;
	}
	
	public static void removeInstanceFromRepository(InstanceType instance, 
			NodeFacade facade) {
		InstanceRepository iRep = facade.getInstanceRepository();
		iRep.removeInstance(instance.getInstanceId());
		facade.setInstanceRepository(iRep);
		
	}

	private static InstanceType createBasicInstance() {
		InstanceType instance = new InstanceType();
		instance.setInstanceId(String.valueOf(++instanceId));
		instance.setUserId(String.valueOf(userId));
		return instance;
	}
}
