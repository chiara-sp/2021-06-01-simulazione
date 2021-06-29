package it.polito.tdp.genes.model;

public class Vicino implements Comparable<Vicino>{

	Genes vicino;
	Double peso;
	public Vicino(Genes vicino, double peso) {
		super();
		this.vicino = vicino;
		this.peso = peso;
	}
	public Genes getVicino() {
		return vicino;
	}
	public void setVicino(Genes vicino) {
		this.vicino = vicino;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return -this.peso.compareTo(o.peso);
	}
	@Override
	public String toString() {
		return  vicino + " " + peso;
	}
	
	
}
