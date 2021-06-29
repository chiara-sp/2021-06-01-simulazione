package it.polito.tdp.genes.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	GenesDao dao;
	Map<String, Genes> geni;
	List<Genes> vertici;
	Graph<Genes,DefaultWeightedEdge> grafo;
	Simulatore sim;
	
	public Model() {
		dao= new GenesDao();
		geni= new HashMap<>();
		vertici= new LinkedList<>();
		dao.getAllGenes(geni);
	}
	
	public void creaGrafo() {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		vertici= dao.getVertici(geni);
		Graphs.addAllVertices(grafo, vertici);
		
		for(Adiacenza a: dao.getArchi(geni)) {
			if(vertici.contains(a.getG1())&& vertici.contains(a.getG2())) {
				if(a.getG1().getChromosome()==a.getG2().getChromosome())
					Graphs.addEdge(grafo, a.getG1(), a.getG2(), 2*Math.abs(a.getPeso()));
				else
					Graphs.addEdge(grafo, a.getG1(), a.getG2(), Math.abs(a.getPeso()));
			}
		}
	}
	public int getNumVertici() {
		return grafo.vertexSet().size();
	}
	public int getNumArchi() {
		return grafo.edgeSet().size();
	}

	public List<Genes> getVertici() {
		return vertici;
	}
	public List<Vicino> getVicini(Genes g){
		List<Vicino> result= new LinkedList<>();
		for(Genes gg: Graphs.neighborListOf(grafo, g)) {
			result.add(new Vicino(gg, grafo.getEdgeWeight(grafo.getEdge(gg, g))));
		}
		Collections.sort(result);
		return result;
	} 
	public boolean grafoCreato() {
		if(grafo==null)
			return false;
		return true;
	}
	public Map<Genes, Integer> simula(int n, Genes partenza) {
		Map<Genes,Integer> result= new HashMap<>();
		sim= new Simulatore(grafo,n, partenza, this);
		sim.run();
		for(Genes g: sim.inCorsodiStudi()) {
			result.put(g, sim.ingegneriSuGene(g));
		}
		return result;
	}
}
