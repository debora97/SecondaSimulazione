package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Adiacenze;
import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	List<Condiment> listAllCondiment;
	List<Condiment> listCalorieCondiment;
	Map<Integer, Condiment> condimentiMap;
	List<Adiacenze> listAdiacenze;
	FoodDao dao = new FoodDao();
	SimpleWeightedGraph<Condiment, DefaultWeightedEdge> grafo;
	private List<Condiment> best;
	double maxcalorie;

	public Model() {
		
		listAllCondiment = new LinkedList<Condiment>(dao.listAllCondiment());
		grafo = new SimpleWeightedGraph<Condiment, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		listAdiacenze = new LinkedList<Adiacenze>();
	}

	public List<Condiment> getCondimentCalorie(double calorie) {
		condimentiMap = new HashMap<Integer, Condiment>();
		listCalorieCondiment = new LinkedList<Condiment>();
		listCalorieCondiment = dao.listAllCondimentCalories(calorie);
		for (Condiment c : listCalorieCondiment) {
			condimentiMap.put(c.getCondiment_id(), c);
		}

		return listCalorieCondiment;
	}

	public void creaGrafo() {
		
		Graphs.addAllVertices(grafo, this.listCalorieCondiment);

		for (Condiment c1 : grafo.vertexSet()) {
			for (Condiment c2 : grafo.vertexSet()) {
				if (!c1.equals(c2)) {

					Adiacenze a = dao.getAdiacenze(c1, c2, condimentiMap);
					if (a != null) {
						if (a.getCont() > 0)
							listAdiacenze.add(a);
					}

				}

			}
		}
		for (Adiacenze ad : listAdiacenze) {

			if (grafo.getEdge(ad.getC1(), ad.getC2()) == null) {

				Graphs.addEdge(this.grafo, ad.getC1(), ad.getC2(), (double) ad.getCont());

			}

		}
		System.out.println(
				"Grafo creato  con \n vertici " + grafo.vertexSet().size() + " e \n archi " + grafo.edgeSet().size());

	}

	public String getCalorieeTot() {
		String s = "";
		Collections.sort(listCalorieCondiment);
		for (Condiment c : listCalorieCondiment) {
			int contenuto = getTot(c);
			s += "L'ingrediente " + c.getDisplay_name() + " con calorie " + c.getCondiment_calories() + "ha "
					+ c.getCondiment_calories() + " calorie ed e' contenuto in " + contenuto + "piatti \n";
		}
		return s;
	}

	private int getTot(Condiment c) {
		int tot = 0;
		for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(c)) {
			tot += grafo.getEdgeWeight(e);
		}
		return tot;
	}

	public List<Condiment> calcolaInsieme(Condiment c1) {
		best = new LinkedList<Condiment>();
		maxcalorie = 0.0;
		
		List<Condiment> parziale = new LinkedList<Condiment>();
		// aggiungo il primo elemento che devo agg per forza all'insieme
		parziale.add(c1);
		this.cerca(parziale);
		return best;

	}

	public void cerca(List<Condiment> parziale) {
		//prendo la lista di tutti i condimenti che ho 
		//escludo quelli che sono i vicini dell'arco che ho inserito nell'insime
		//listCalorieCondiment.removeAll(Graphs.neighborListOf(grafo, parziale.get(i)));
	double calParziale=calcolaTotCalorie(parziale);
		if(calParziale>maxcalorie) {
			best= new LinkedList<Condiment>(parziale);
			maxcalorie=calParziale;
			
			
		}
		List<Condiment> candidati= new LinkedList<Condiment>();
		candidati= this.creaCandidati(parziale);
	
	
	
		
		
		
	if(candidati.size()==0)return;
	
	
	for(Condiment c : candidati) {
		if(!parziale.contains(c)) {
			parziale.add(c);
			this.cerca(parziale);
			parziale.remove(parziale.size()-1);
		}
	}
	
	
	


}

	private List<Condiment> creaCandidati(List<Condiment> parziale) {
		List<Condiment>candidati=	new LinkedList<Condiment>(this.listCalorieCondiment);
		for(Condiment c: parziale) {
			for(Condiment cr:Graphs.neighborListOf(grafo, c) ){
				if(candidati.contains(cr)) {
					candidati.remove(cr);
				}
			}
			
		}
		return candidati;
	}

	private double calcolaTotCalorie(List<Condiment> parziale) {
		double cal=0;
		for(Condiment c: parziale) {
			cal+=c.getCondiment_calories();
		}
		return cal;
	}
}