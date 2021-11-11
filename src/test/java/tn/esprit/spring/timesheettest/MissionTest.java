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
import static org.junit.Assert.assertFalse;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
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
			logger.debug("start ajoutMission");
			timeSheetService.ajouterMission(mission);
			logger.debug("end ajoutMission");
		} catch (Exception e) {
			logger.error("Erreur dans AjouterMission() :" + e);
		}

	}
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}
	@Test
	public void TestFindAllMissionByEmployeJPQL() throws ParseException {
		logger.debug("start ajoutEmploye");
		Employe employe = new Employe(1,"gueldich", "wissem", "gueldich.wissem@esprit.tn", true, Role.INGENIEUR);
		employeRepository.save(employe);
		logger.debug("end ajoutEmploye");

		logger.debug("start ajoutMission1");
		Mission mission1 = new Mission("mission1Name","mission1Desc");
		missionRepository.save(mission1);
		logger.debug("end ajoutMission1");

		logger.debug("start ajoutMission2");
		Mission mission2 = new Mission("mission2Name","mission2Desc");
		missionRepository.save(mission2);
		logger.debug("end ajoutMission2");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date dateDebut = dateFormat.parse("2021-05-08");
		 Date dateFin = dateFormat.parse("2021-12-02");

		 TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employe.getId());
		timesheetPK.setIdMission(mission1.getId());
		
		logger.debug("start ajoutTimesheet1");
		Timesheet timesheet1 = new Timesheet();
		timesheet1.setTimesheetPK(timesheetPK);
		timesheetRepository.save(timesheet1);
		logger.debug("end ajoutTimesheet1");
		
		logger.debug("start ajoutTimesheet2");
		Timesheet timesheet2 = new Timesheet();
		timesheetPK.setIdMission(mission2.getId());
		timesheet2.setTimesheetPK(timesheetPK);
		timesheetRepository.save(timesheet2);
		logger.debug("end ajoutTimesheet2");

		logger.info("In findAllMissionByEmployeJPQL");
		logger.debug("start findAllMissionByEmployeJPQL");
		List<Mission> list = timeSheetService.findAllMissionByEmployeJPQL(employe.getId());
		logger.debug("end ajoutfindAllMissionByEmployeJPQL");

		
		assertFalse(list.isEmpty());

	}

}
