package tn.esprit.spring.services;


import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.exception.ContratNotFoundException;
import tn.esprit.spring.exception.EmployeNotFoundException;
import tn.esprit.spring.repository.ContratRepository;

import tn.esprit.spring.repository.EmployeRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	
	@Autowired
	ContratRepository contratRepoistory;
	//SIWAR
	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);

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


	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

	
	
    private static final Logger logger = LogManager.getLogger(EmployeServiceImpl.class);

	
	//JIHEN 
		public void deleteAllContratJPQL() {
			 logger.info("debut suppression de tous les contrat");
			 try{ 
				 employeRepository.deleteAllContratJPQL();
				 logger.info("fin ppression de tous les contrat avec succées");
				 }
			 catch(Exception e){
				 logger.error("error lors de la suppression de tous les contrats");
			 	}
		}

		public void deleteContratById(int contratId) {
			   logger.info("debut suppression contrat");
			Contrat contratManagedEntity=new Contrat();
			  try{	contratManagedEntity = contratRepoistory.findById(contratId).orElseThrow(()-> new ContratNotFoundException("contrat inexistable"));
					 logger.trace("reference contrat "+contratManagedEntity.getReference());
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
		public void affecterContratAEmploye(int contratId, int employeId) {
			  	logger.info("debut affectation contrat a employe");   
			  	Contrat contratManagedEntity=new Contrat();	
			  	Employe employeManagedEntity=new Employe(); 
			   try{
				 contratManagedEntity =contratRepoistory.findById(contratId).orElseThrow(()->new ContratNotFoundException("Contrat inexistable"));
				 logger.trace("reference contrat"+contratManagedEntity.getReference());
			   		}
			   
			   catch(Exception e){
					logger.error("contrat invalide");
			   		}
			   
			   try{		
				   employeManagedEntity = employeRepository.findById(employeId).orElseThrow(()->new EmployeNotFoundException("Employe inexistable"));
				   logger.trace("nom de l employe a affecter"+employeManagedEntity.getNom());	
			   		}
			   
			   catch(Exception e){			
				   logger.error("employe invalide");
				   }
			   
				try{		
					contratManagedEntity.setEmploye(employeManagedEntity);
					contratRepoistory.save(contratManagedEntity);
				  	logger.info("l'affectation est realise avec succés");
					}
				catch(Exception e){			
					logger.error("erreur lors de l affectation");
				   }
		}

			public int ajouterContrat(Contrat contrat) {
				   logger.trace("debut ajout");	  
				   logger.info("debut ajout");	  
				   try{	 contratRepoistory.save(contrat);
						 logger.info("l'ajout est realise avec succés");
					   		}
					   catch(Exception e){
							logger.error("erreur d'ajout");}
				return contrat.getReference();
			}
}
