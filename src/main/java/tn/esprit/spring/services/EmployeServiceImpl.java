package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.controller.RestControlEmploye;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
    private static final Logger logger = LogManager.getLogger(RestControlEmploye.class);
	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}
	}

	public int ajouterContrat(Contrat contrat) {
		   logger.trace("debut ajout");	  
		   try{	 contratRepoistory.save(contrat);
				 logger.info("l'ajout est realise avec succés");
			   		}
			   catch(Exception e){
					logger.error("erreur d'ajout");}
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		   logger.trace("debut affectation");
		   Contrat contratManagedEntity=new Contrat();	
		   Employe employeManagedEntity=new Employe();      
		   try{
			 contratManagedEntity = contratRepoistory.findById(contratId).get();
			 logger.info("reference contrat"+contratManagedEntity.getReference());
		   		}
		   catch(Exception e){
				logger.error("contrat invalide");
		   		}
		   try{		
			   employeManagedEntity = employeRepository.findById(employeId).get();
			   logger.info("nom de l employe a affecter"+employeManagedEntity.getNom());	
		   		}
		   catch(Exception e){			
			   logger.error("employe invalide");
			   }
		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
			try{		
				contratRepoistory.save(contratManagedEntity);
			  	logger.info("l'affectation est realise avec succés");
				}
			catch(Exception e){			
				logger.error("erreur lors de l affectation");
			   }
	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}
	public void deleteEmployeById(int employeId)
	{
		Employe employe = employeRepository.findById(employeId).get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}

	public void deleteContratById(int contratId) {
		   logger.trace("debut suppression contrat");

		Contrat contratManagedEntity=new Contrat();
		  try{
				 contratManagedEntity = contratRepoistory.findById(contratId).get();
				 logger.info("reference contrat"+contratManagedEntity.getReference());
			  }
		  catch(Exception e){
					logger.error("contrat invalide");
			  }
		  try{	
			  contratRepoistory.delete(contratManagedEntity);
			  logger.info("la surpression est realise avec succés");
				}
			catch(Exception e){			
				logger.error("erreur lors de la suppression");
			   }
	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
		   logger.trace("debut suppression ");

		try{ employeRepository.deleteAllContratJPQL();
			 logger.info("suppression avec succées");
		  }
	  catch(Exception e){
				logger.error("error lors de la supression");
		  }
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
