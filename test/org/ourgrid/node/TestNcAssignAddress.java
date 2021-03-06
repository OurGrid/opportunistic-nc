package org.ourgrid.node;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.node.idleness.TestIdlenessChecker;
import org.ourgrid.node.model.InstanceRepository;
import org.ourgrid.node.util.OurVirtUtils;
import org.ourgrid.node.util.Sensor;
import org.ourgrid.virt.OurVirt;
import org.ourgrid.virt.model.HypervisorType;
import org.ourgrid.virt.model.VirtualMachineConstants;
import org.ourgrid.virt.model.VirtualMachineStatus;

import edu.ucsb.eucalyptus.InstanceType;
import edu.ucsb.eucalyptus.NcAssignAddress;
import edu.ucsb.eucalyptus.NcAssignAddressResponseType;
import edu.ucsb.eucalyptus.NcAssignAddressType;


public class TestNcAssignAddress {
	
	private static int PUBLIC_IP = 1;
	private NodeFacade facade;
	private OurVirt ourvirtMock = Mockito.mock(OurVirt.class);
	private TestIdlenessChecker testIChecker = new TestIdlenessChecker();
	private InstanceRepository instanceRepository = new InstanceRepository();
	private Properties properties;
	private static final String STATUS_MSG = "0"; 
	
	@Before
	public void init() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream("WebContent/WEB-INF/conf/euca.conf"));
		System.out.println(properties.toString());
		Sensor sensor = new Sensor(0, instanceRepository);
		facade = new NodeFacade(properties, testIChecker, null, instanceRepository, sensor);
		OurVirtUtils.setOurVirt(ourvirtMock);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNCNotAvailable() throws Exception {
		
		testIChecker.setIdle(false);
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade, instanceRepository);
		
		NcAssignAddress assignReq = createAssignAddressRequest(
				instance.getInstanceId(), instance.getUserId(),
				getPublicIp());
		
		facade.assignAddress(assignReq);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInexistentInstance() throws Exception {
		
		NcAssignAddress assignReq = createAssignAddressRequest(
				"", "", getPublicIp());
		
		facade.assignAddress(assignReq);		
	}
	
	@Test
	public void testNotRegisteredInstance() throws Exception {
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade, instanceRepository);
		
		NcAssignAddress assignReq = createAssignAddressRequest(
				instance.getInstanceId(), instance.getUserId(),
				getPublicIp());
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.NOT_REGISTERED);
		
		facade.assignAddress(assignReq);
		
		Mockito.verify(ourvirtMock).register(instance.getInstanceId(), 
				new HashMap<String, String>());
		
		Mockito.verify(ourvirtMock).setProperty(
				Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()),
				Mockito.eq(VirtualMachineConstants.IP),
				Mockito.eq(assignReq.getNcAssignAddress().getPublicIp()));
		
	}
	
	@Test
	public void testMainFlow() throws Exception {
		
		InstanceType instance = TestUtils.addBasicInstanceToRepository(
				facade, instanceRepository);
		
		NcAssignAddress assignReq = createAssignAddressRequest(
				instance.getInstanceId(), 
				instance.getUserId(),
				getPublicIp());
		
		Mockito.when((ourvirtMock.status(Mockito.any(HypervisorType.class), 
				Mockito.eq(instance.getInstanceId())))).thenReturn(
						VirtualMachineStatus.RUNNING);
		
		NcAssignAddressResponseType response = 
				facade.assignAddress(assignReq).getNcAssignAddressResponse();
		
		Mockito.verify(ourvirtMock).setProperty(
				Mockito.any(HypervisorType.class),
				Mockito.eq(instance.getInstanceId()),
				Mockito.eq(VirtualMachineConstants.IP),
				Mockito.eq(assignReq.getNcAssignAddress().getPublicIp()));
		
		Assert.assertTrue(response.get_return());
		Assert.assertEquals(response.getUserId(),instance.getUserId());
		Assert.assertEquals(response.getCorrelationId(),
				assignReq.getNcAssignAddress().getCorrelationId());
		Assert.assertEquals(response.getStatusMessage(),
				assignReq.getNcAssignAddress().getStatusMessage());
		
	}
	
	private static String getPublicIp() {
		return String.valueOf(PUBLIC_IP++);
	}
	
	private NcAssignAddress createAssignAddressRequest(String instanceId, 
			String userId, String publicIp) {
		NcAssignAddressType assignAddressType = new NcAssignAddressType();
		assignAddressType.setInstanceId(instanceId);
		assignAddressType.setUserId(userId);
		assignAddressType.setStatusMessage(STATUS_MSG);
		assignAddressType.setPublicIp(publicIp);
		assignAddressType.setCorrelationId(TestUtils.getNewCorrelationId());

		NcAssignAddress assignReq = new NcAssignAddress();
		assignReq.setNcAssignAddress(assignAddressType);
		
		return assignReq;
	}
	
}
