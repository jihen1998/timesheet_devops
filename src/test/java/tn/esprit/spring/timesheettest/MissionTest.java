package tn.esprit.spring.timesheettest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;


import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.ITimesheetService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MissionTest {
	private static final Logger logger = LogManager.getLogger(TimesheetTest.class);
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	ITimesheetService timeSheetService;
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	IEmployeService employeService;
	@Autowired
	EmployeRepository employeRepository;
	

	@Test
	public void testAjouterMission() throws ParseException {

		Mission mission = new Mission("missionName","missionDesc");
		
		assertNotNull(missionRepository.save(mission).getId());
		try {
			logger.info("In AjouterMission() : ");
			logger.debug("lancer ajoutMission");
			timeSheetService.ajouterMission(mission);
			
		} catch (Exception e) {
			logger.error("Erreur dans AjouterMission() :" + e);
		}

	}
	//public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		//return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	//}
	//@Test
	//public void TestFindAllMissionByEmployeJPQL() {
		//Employe employe = new Employe(1,"gueldich", "wissem", "gueldich.wissem@esprit.tn", true, Role.INGENIEUR);
		//employeRepository.save(employe);
		//Mission mission1 = new Mission("mission1Name","mission1Desc");
		//System.out.println("eeeeeeeeeeeeeeeeeeeeeee"+mission1.getId());
		//missionRepository.save(mission1);

		//Mission mission2 = new Mission("mission2Name","mission2Desc");
		//missionRepository.save(mission2);
		//System.out.println("fffffffffffffffffffffff"+mission2.getId());

		//Timesheet timesheet1 = new Timesheet();
		//timesheet1.setEmploye(employe);
		//timesheet1.setMission(mission1);
		//timesheetRepository.save(timesheet1);
		//Timesheet timesheet2 = new Timesheet();
		//timesheet2.setEmploye(employe);
		//timesheet2.setMission(mission2);
		//timesheetRepository.save(timesheet2);
		//List<Mission> list1 = timeSheetService.findAllMissionByEmployeJPQL(employe.getId());
		//List<Mission> list2 = new ArrayList<Mission>();
		//list2.add(mission1);
		//list2.add(mission2);
		//assertEquals(list2,list1);

	//}

}
