package tn.esprit.spring.employetest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.List;
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
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.exception.ContratNotFoundException;
import tn.esprit.spring.exception.EmployeNotFoundException;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratTest {
    private static final Logger logger = LogManager.getLogger(ContratTest.class);
    @MockBean
    ContratRepository contratRepository;
    @MockBean
    EmployeRepository employeRepository;
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
		  logger.trace("affichage id contrat existe dans repo mock: "+iemployeService.ajouterContrat(contrat));
		  assertEquals(contrat.getReference(), iemployeService.ajouterContrat(contrat));
		   logger.info(" test ajout Contrat terminé");	  
	}
	@Test
	public void testDeleteContratById() {
		   logger.info("debut test suppression par id ");	
		     Contrat contrat=new Contrat();
			   contrat.setDateDebut(new Date());
			   contrat.setSalaire(100);
			   contrat.setReference(1);
			   contrat.setTypeContrat("CDD"); 
			   Optional<Contrat> optionalEntityType = Optional.of(contrat);
		    when(contratRepository.findById(1)).thenReturn(optionalEntityType);
		   iemployeService.deleteContratById(contrat.getReference());
		  verify(contratRepository,times(1)).delete(contrat);
		   logger.info("fin test contrat suppression avec succées");	
	}
	@Test
	public void testDeleteAllContratJPQL() {
		   logger.info("debut test suppression de tous les contrats" );	
		   iemployeService.deleteAllContratJPQL();
		  verify(employeRepository,times(1)).deleteAllContratJPQL();
		   logger.info("fin test suppression de tous les contrats");	
	}
	@Test
	public void testAffectEmplToContrat() {
		   logger.info("debut test affectation employe à un contrat");	
		   Contrat c = new Contrat();
		   c.setReference(4);
		   c.setDateDebut(new Date());
		   c.setSalaire(100);
		   c.setTypeContrat("CDD");
		   when(contratRepository.findById(4)).thenReturn(Optional.of(c));
		   Employe e = new Employe();
		   e.setActif(true);
		   e.setId(2);
		   e.setEmail("xx@gmail.com");
		   e.setNom("xx");
		   e.setPrenom("ben xxxx");
		   when(employeRepository.findById(2)).thenReturn(Optional.of(e));
		   logger.trace("contrat avant affectation " + c.toString());
		   logger.trace("employe a affecter a contrat" + e.toString());
		   iemployeService.affecterContratAEmploye(c.getReference(),e.getId());
		   logger.trace("contart apres affectation " + c.toString());
		   assertEquals(c.getEmploye().getId(),contratRepository.findById(c.getReference()).orElseThrow(()->new ContratNotFoundException("contrat inexistable")).getEmploye().getId());
		   logger.info("fin test affectation employe à un contrat avec succées");	
	}
}
