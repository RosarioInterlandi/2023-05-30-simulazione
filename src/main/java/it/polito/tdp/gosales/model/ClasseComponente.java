package it.polito.tdp.gosales.model;

import java.util.Objects;

public class ClasseComponente implements Comparable<ClasseComponente>{
	int dimensione;
	Double sommaPesi;
	public ClasseComponente(int dimensione, Double sommaPesi) {
		super();
		this.dimensione = dimensione;
		this.sommaPesi = sommaPesi;
	}
	public int getDimensione() {
		return dimensione;
	}
	public void setDimensione(int dimensione) {
		this.dimensione = dimensione;
	}
	public Double getSommaPesi() {
		return sommaPesi;
	}
	public void setSommaPesi(Double sommaPesi) {
		this.sommaPesi = sommaPesi;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dimensione, sommaPesi);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClasseComponente other = (ClasseComponente) obj;
		return dimensione == other.dimensione && Objects.equals(sommaPesi, other.sommaPesi);
	}
	@Override
	public int compareTo(ClasseComponente o) {
		// TODO Auto-generated method stub
		return this.sommaPesi.compareTo(o.sommaPesi);
	}
	 
	
}
