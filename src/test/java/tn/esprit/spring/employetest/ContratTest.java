package tn.esprit.spring.employetest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratTest {
    private static final Logger logger = LogManager.getLogger(ContratTest.class);
    @MockBean
    ContratRepository contratRepository;
      @Autowired
	IEmployeService iemployeService;
    @Test
	public void testAjouterContrat() {
		   logger.info("debut test ajout");	  
		   Contrat contrat=new Contrat();
		   contrat.setDateDebut(new Date());
		   contrat.setSalaire(100);
		   contrat.setReference(12);
		   contrat.setTypeContrat("CDD");
		   logger.trace("affichage id contrat à ajouter: " + contrat.toString());
		  when(contratRepository.save(contrat)).thenReturn(contrat);	
		  logger.trace("affichage id contrat existe dans mock: "+iemployeService.ajouterContrat(contrat));
		  assertEquals(contrat.getReference(), iemployeService.ajouterContrat(contrat));
		   logger.info(" test ajout Contrat terminé");	  
	}
	@Test
	public void testDeleteContratById() {
		   logger.info("debut test suppression");	
		     Contrat contrat=new Contrat();
			   contrat.setDateDebut(new Date());
			   contrat.setSalaire(100);
			   contrat.setReference(1);
			   contrat.setTypeContrat("CDD"); 
			   Optional<Contrat> optionalEntityType = Optional.of(contrat);
		    when(contratRepository.findById(1)).thenReturn(optionalEntityType);
		   iemployeService.deleteContratById(contrat.getReference());
		  verify(contratRepository,times(1)).delete(contrat);
		   logger.info("fin test contrat suppression");	
	}
}
