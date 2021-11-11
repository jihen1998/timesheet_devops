package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
private static final Logger logger = LogManager.getLogger(TimesheetServiceImpl.class);	

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	
		
	

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		
	}



public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		logger.info("In valider Timesheet");
		Employe validateur = employeRepository.findById(validateurId).get();
		Mission mission = missionRepository.findById(missionId).orElse(null);
		
		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			logger.info("l'employe doit etre chef de departement pour valider une feuille de temps !");
			return;
		}
		
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			logger.info("l'employe doit etre chef de departement de la mission en question");
			return;
		}

		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		logger.info("dateDebut : " , dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
	}



}
