package cn.itcast.bos.service.impl;

import cn.itcast.bos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class FacadeService {
	@Autowired
	private UserService userService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private SubareaService subareaService;
	@Autowired
	private DecidedZoneService decideZoneService;
	@Autowired
	private CityService cityService;
	@Autowired
	private NoticeBillService noticeBillService;


	public UserService getUserService(){
		return userService;
	}

	public StandardService getStandardService() {
		return standardService;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	public RegionService getRegionService() {
		return regionService;
	}


	public SubareaService getSubareaService() {
		return subareaService;
	}




	public DecidedZoneService getDecidedZoneService() {
		return decideZoneService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public NoticeBillService getNoticeBillService() {
		return noticeBillService;
	}
}
