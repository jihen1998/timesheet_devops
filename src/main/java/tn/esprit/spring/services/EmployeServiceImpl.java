package tn.esprit.spring.services;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.controller.RestControlEmploye;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;
@Service
public class EmployeServiceImpl implements IEmployeService {

	
	@Autowired
	ContratRepository contratRepoistory;
	
    private static final Logger logger = LogManager.getLogger(RestControlEmploye.class);
	
	public int ajouterContrat(Contrat contrat) {
		   logger.trace("debut ajout");	  
		   try{	 contratRepoistory.save(contrat);
				 logger.info("l'ajout est realise avec succés");
			   		}
			   catch(Exception e){
					logger.error("erreur d'ajout");}
		return contrat.getReference();
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


}
