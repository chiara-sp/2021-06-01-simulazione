package it.polito.tdp.genes.model;

public class Evento implements Comparable<Evento> {

	
	private int nIng;
	private int mese;
	private Genes gene;
	public Evento(int nIng, int mese, Genes gene) {
		super();
		this.nIng = nIng;
		this.mese = mese;
		this.gene=gene;
	}
	public Genes getGene() {
		return gene;
	}
	public void setGene(Genes gene) {
		this.gene = gene;
	}
	public int getnIng() {
		return nIng;
	}
	public void setnIng(int nIng) {
		this.nIng = nIng;
	}
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.mese-o.mese;
	}
	
	
	
	
}
