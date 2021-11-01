package tn.esprit.spring.services;


import java.util.List;
import java.util.NoSuchElementException;

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
	
	 private static final Logger logger = LogManager.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	
	@Autowired
	ContratRepository contratRepoistory;
	
	
	
	//SIWAR
	
	public int ajouterEmploye(Employe employe) {
		logger.info("Lancement de l'ajout de l'employé à la base de données");
		try { employeRepository.save(employe);
		     logger.info("Succés de l'ajout de l'employé");
		}
		catch (Exception e){
			logger.error("Un erreur a apparait lors de l'ajout de l'employé");
			}
		return employe.getId();
	}
	
	public Integer addOrUpdateEmploye(Employe employe) {
		logger.debug("Methode ajouterEmployee");
		try {
			employeRepository.save(employe);
			logger.info("Employe ajoutée avec id = "+employe.getId());
		return employe.getId();
		}
		catch (Exception e) {
			logger.error("erreur methode ajouterEntreprise :" +e);	
		       return null;       
				}		
	}
	
	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		logger.debug("Methode mettre à jour l'email de l'employee");
		try {
		Employe employe = employeRepository.findById(employeId).orElse(null);
		if(employe!=null){
		employe.setEmail(email);
		employeRepository.save(employe);
		logger.debug("mettreAjourEmailByEmployeId est finie avec succes ");
		
		}
	}
		catch (Exception e) {
			logger.error("erreur au niveau de la méthode  mettreAjourEmailByEmployeId : " +e);
		}
		
	}
	
	
		

	
	public String getEmployePrenomById(int employeId) {
		logger.debug("lancement de la methode getEmplpyeById ");
		try {
		Employe employeManagedEntity = employeRepository.findById(employeId).orElse(null);
		logger.debug("la méthode getEmployeById est finie avec succés ");
		
		return employeManagedEntity!=null ?employeManagedEntity.getPrenom():null;}
		catch (Exception e) {
			logger.error("erreur methode getEmployeeById : " +e);
			return null;
		}	
		
	}

	public void deleteEmployeById(int employeId)throws NoSuchElementException
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
    
    
    /**	@Transactional
	public int deleteEmployeById(int employeId)
	{
		 logger.info("lancement de la méthode de la suppression de l'id de l'employé de la base de données ");
		 try {
			 if (employeRepository.findById(employeId).orElse(null)!=null){
				 employeRepository.delete(employeRepository).findById(employeId).orElse(null));
				 logger.debug("deleteEntrepriseById fini avec succes ");
				 
				 return 0;}else {
					 logger.error("erreur methode deleteEntrepriseById : " );
						return -1;
					}
				} catch (Exception e) {
					logger.error("erreur methode deleteEntrepriseById : " +e);
					return -1;
					}
				}		
	*/


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
		
		List<Employe> employes = null; 
		try {
	
			
			logger.info("In Method getAllEmployes");
			employes = (List<Employe>) employeRepository.findAll();
				 logger.info("Employes");
					logger.debug("Connexion Bd");
					
					for (Employe employe : employes) {
						logger.info("Employe"+employe.getNom());
					} 
					logger.info("out with succes");

				}catch (Exception e) {
					logger.error("out of method with error"+e);		}

				return employes;
			}

	
	

	
	

	
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
				   logger.info("debut methode ajout");	  
				   try{	 contratRepoistory.save(contrat);
						 logger.info("l'ajout est realise avec succés, fin methode ajouterContarat");
					   		}
					   catch(Exception e){
							logger.error("erreur d'ajout");}
				return contrat.getReference();
			}
			
			
}
