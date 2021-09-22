package it.uniroma3.siw.spring.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.spring.controller.validator.TipologiaEsameValidator;
import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.service.IndicatoreService;
import it.uniroma3.siw.spring.service.PrerequisitoService;
import it.uniroma3.siw.spring.service.TipologiaEsameService;


@Controller
public class TipologiaEsameController {
	
	@Autowired
	private TipologiaEsameService tipologiaesamiService;
	
    @Autowired
    private TipologiaEsameValidator tipologiaesamiValidator;
    
    @Autowired
    private PrerequisitoService prerequisitiService;
    @Autowired
    private IndicatoreService indicatoriService;
    
        
    @RequestMapping(value="/admin/tipologiaesameForm", method = RequestMethod.GET)
    public String addProdotto(Model model) {
    	model.addAttribute("tipologiaesame", new TipologiaEsame());
        return "TipologiaEsame/TipologiaesameForm";
    }

    @RequestMapping(value = "/tipologiaesame/{id}", method = RequestMethod.GET)
    public String getmedico(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("tipologiaesami", this.tipologiaesamiService.tipologiaesamiPerId(id));
    	model.addAttribute("prerequisiti", this.tipologiaesamiService.tipologiaesamiPerId(id).getPrerequisito());
    	return "TipologiaEsame/TipologiaEsame";
    }

    @RequestMapping(value = "/tipologiaesameForm", method = RequestMethod.POST)
    public String addProdotto(@ModelAttribute("tipologiaesami") TipologiaEsame tipologiaesami, 
    									Model model, BindingResult bindingResult) {
    	this.tipologiaesamiValidator.validate(tipologiaesami, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.tipologiaesamiService.inserisci(tipologiaesami);
            model.addAttribute("tipologiaesami", this.tipologiaesamiService.tutti());
            return "admin/home";
        }
        return "TipologiaEsame/TipologiaesameForm";
    }
    
    @RequestMapping(value = "/TipologiaEsame", method = RequestMethod.GET)
    public String getPrerequisiti(Model model) {
    		model.addAttribute("tipologiaesami", this.tipologiaesamiService.tutti());
    		return "TipologiaEsame/Tipologiaesami";
    }
    
   
    
    @RequestMapping(value = "/AggPrerequisito", method = RequestMethod.GET)
    public String Prerequisiti(Model model) {
    		model.addAttribute("tipologiaesami", this.tipologiaesamiService.tutti());
    		return "TipologiaEsame/SelezionaEsame";
    }
    
    
    @RequestMapping(value = "/selezionaprere/{id}", method = RequestMethod.GET)
    public String getPrere(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("tipologiaesame", this.tipologiaesamiService.tipologiaesamiPerId(id));
    	model.addAttribute("prerequisiti", this.prerequisitiService.tutti());
    	return "TipologiaEsame/AggPrerequisito";
    }
   
    @RequestMapping(value = "/admin/tipologiaesameSelezionato/{id}", method = RequestMethod.GET) //l'id è riferito alla tipologia di esame selezionato precedentemente
    public String tipologiaEsameSelezionato(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("indicatori", this.indicatoriService.tutti());
    	model.addAttribute("idEsame",id); //ci portiamo l'id dell'esame
    	return "indicatori/selezionaIndicatore";
    }
    
    
    
    
    @RequestMapping(value = "/conferma/{tid}/{pid}", method = RequestMethod.GET)
    public String getPrere(@PathVariable("pid") Long pid,@PathVariable("tid") Long tid, Model model) {
    	TipologiaEsame tipologiaesame= this.tipologiaesamiService.tipologiaesamiPerId(tid);
    	Prerequisito prerequisito= this.prerequisitiService.prerequisitoPerId(pid);
    	tipologiaesame.getPrerequisito().add(prerequisito);
    	this.tipologiaesamiService.inserisci(tipologiaesame);
    	return "admin/home";
    }
    
}
