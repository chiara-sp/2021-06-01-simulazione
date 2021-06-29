package it.polito.tdp.genes.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Simulatore {

	//coda degli eventi
	PriorityQueue<Evento> queue;
	
	//modello del mondo 
	int numMesi=36;
	int totIng;
	Graph<Genes,DefaultWeightedEdge> grafo;
	Genes partenza;
	Model model;
	
	//output
	Map<Integer, Genes> geneStudiato;
	
	
	public Simulatore(Graph<Genes,DefaultWeightedEdge> grafo, int totIng, Genes partenza, Model model) {
		this.grafo=grafo;
		this.totIng=totIng;
		this.partenza=partenza;
		this.model=model;
		this.geneStudiato= new HashMap<>();
		this.queue= new PriorityQueue<>();
		
		for(int i=0; i<totIng; i++) {
			geneStudiato.put(i, partenza);
			queue.add(new Evento(i,1,partenza));
			
		}
	}
	public void run() {
		
		while( !queue.isEmpty()) {
			Evento ev= queue.poll();
			int mese= ev.getMese();
			int ing= ev.getnIng();
			Genes stud= ev.getGene();
			double num= Math.random();
			if(mese<=36) {
			if(num<0.3) {
				queue.add(new Evento(ing,mese+1,stud));
			}
			else {
				
				double pesoTOT=0;
				for(Vicino v: model.getVicini(stud)) {
					pesoTOT+=v.getPeso();
				}
				double prob= Math.random()*pesoTOT;
				double r=0;
				for(Vicino v: model.getVicini(stud)) {
					r+=v.getPeso();
					if(prob<r) {
						queue.add(new Evento(ing,mese+1,v.getVicino()));
						this.geneStudiato.put(ing, v.getVicino());
						
						break;
					}
				}
			}
			}
		}
	}
	public List<Genes> inCorsodiStudi(){
		List<Genes> result= new LinkedList<>();
		for(Genes g: this.geneStudiato.values()) {
			if(!result.contains(g))
				result.add(g);
		}
		return result;
	}
	public int ingegneriSuGene(Genes g) {
		int res=0;
		for(Genes gg: this.geneStudiato.values()) {
			if(g.equals(gg))
				res+=1;
		}
		return res;
	}
}
