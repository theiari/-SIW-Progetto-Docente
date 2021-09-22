package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.mapping.Array;
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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.validator.IndicatoreValidator;
import it.uniroma3.siw.spring.controller.validator.PrerequisitoValidator;
import it.uniroma3.siw.spring.controller.validator.ProdottoValidator;
import it.uniroma3.siw.spring.model.Esame;
import it.uniroma3.siw.spring.model.Indicatore;
import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.service.EsameService;
import it.uniroma3.siw.spring.service.IndicatoreService;
import it.uniroma3.siw.spring.service.PrerequisitoService;
import it.uniroma3.siw.spring.service.ProdottoService;
import it.uniroma3.siw.spring.service.TipologiaEsameService;

@Controller
public class IndicatoreController {
	
	@Autowired
	private IndicatoreService indicatoreService;
	
    @Autowired
    private IndicatoreValidator indicatoreValidator;
    @Autowired
    private TipologiaEsameService tipologiaEsameService;
    @Autowired
    private EsameService esameService;
        
    @RequestMapping(value="/admin/indicatoreForm", method = RequestMethod.GET)
    public String addProdotto(Model model) {
    	model.addAttribute("indicatore", new Indicatore());
        return "Indicatori/IndicatoreForm";
    }

    @RequestMapping(value = "/indicatore/{id}", method = RequestMethod.GET)
    public String getIndicatore(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("indicatore", this.indicatoreService.indicatorePerId(id));
    	return "Indicatori/Indicatore";
    }

    @RequestMapping(value = "/indicatoreForm", method = RequestMethod.POST)
    public String addProdotto(@ModelAttribute("indicatore") Indicatore indicatore, 
    									Model model, BindingResult bindingResult) {
    	this.indicatoreValidator.validate(indicatore, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.indicatoreService.inserisci(indicatore);
            model.addAttribute("indicatore", this.indicatoreService.tutti());
            return "admin/home";
        }
        return "Indicatori/IndicatoreForm";
    }
    
    @RequestMapping(value = "/indicatori", method = RequestMethod.GET)
    public String getPrerequisiti(Model model) {
    		model.addAttribute("indicatori", this.indicatoreService.tutti());
    		return "Indicatori/Indicatori";
    }
    
    
    @RequestMapping(value = "/admin/associaIndicatori", method = RequestMethod.GET) //seleziona la tipologia d'esame da cui aggiungere gli indicatori
    public String selectTipologiaEsame(Model model) {
    		model.addAttribute("tipologiaEsami", this.tipologiaEsameService.tutti());
    		return "Indicatori/SelezionaTipologiaEsame";
    }
    
    
    //!!!!!!!!TO DO!!!!!!!!!!!!!!!
    @RequestMapping(value = "/indicatoreScelto/{eid}/{iid}", method = RequestMethod.GET)
    public String idEsameidIndicatore(Model model, @ModelAttribute("eid") Long eid, //eid corrisponde all'id della tipologia d'esame, mentre iid a IndicatoreId
    		@ModelAttribute("iid") Long iid) {
    	   if (tipologiaEsameService.tipologiaesamiPerId(eid).getIndicatori()==null) {
    		   List<Indicatore> listaIndicatori = new LinkedList<Indicatore>();
    		   tipologiaEsameService.tipologiaesamiPerId(eid).setIndicatori(listaIndicatori);
    	   }
    		   this.tipologiaEsameService.tipologiaesamiPerId(eid).getIndicatori().add(this.indicatoreService.indicatorePerId(iid));
    		   this.tipologiaEsameService.inserisci(this.tipologiaEsameService.tipologiaesamiPerId(eid));
    		
    		return "admin/home";
    }
    
    
    
    @RequestMapping(value = "/aggIndicatore/{eid}", method = RequestMethod.POST) //seleziona la tipologia d'esame da cui aggiungere gli indicatori
    public String aggIndicatore(@ModelAttribute("eid") Long eid,@ModelAttribute("esame") Esame esame, Model model) {
            Esame esamee = this.esameService.esamePerId(eid);
            int i=0;
            Map<Indicatore, String> risultati = esamee.getRisultati();
            Set<Indicatore> albero = risultati.keySet();
                for(Indicatore a : albero) {

                    risultati.put(a, esame.getRisu().get(i));
                    i++;

            }



            esamee.setRisultati(risultati);
            this.esameService.inserisci(esamee);

            return "admin/home";
    }
}
