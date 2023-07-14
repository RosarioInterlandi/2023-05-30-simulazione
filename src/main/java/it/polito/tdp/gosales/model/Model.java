package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	private GOsalesDAO dao;
	private SimpleWeightedGraph<Retailers, DefaultWeightedEdge> grafo;
	private List<Retailers> allNodes;
	public Model() {
		this.dao = new GOsalesDAO();
	}
	
	public void BuildGraph(int anno, String nazione, int nCommonProducts) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.allNodes = new ArrayList<>(this.dao.getVertici(nazione));
		//Cerca vertici
		Graphs.addAllVertices(this.grafo, allNodes);
		
		//Cerco archi
		for (Retailers r1 : this.grafo.vertexSet()) {
			for (Retailers r2: this.grafo.vertexSet()) {
				if (!r1.equals(r2)) {
					Set<Integer> intersezione = new HashSet<>(this.dao.prodottiRivenditoriAnno(anno, r1.getCode()));
					Set<Integer> prodottiRivenditore2 = new HashSet<>(this.dao.prodottiRivenditoriAnno(anno, r2.getCode()));
					intersezione.retainAll(prodottiRivenditore2);
					if (intersezione.size()>= nCommonProducts) {
						Graphs.addEdge(this.grafo, r1, r2, intersezione.size());
					}
				}
			}
		}	
	}
	
	public List<String> getCountry(){
		List<String> result = this.dao.allCountry();
		Collections.sort(result);
		return result;
	}
	
	public List<Retailers> getVertici(){
		List<Retailers> result = new ArrayList<>(this.grafo.vertexSet()) ;
		Collections.sort(result);
		return result;
	}
	
	public Set<DefaultWeightedEdge> getArchi(){
		return this.grafo.edgeSet();
	}
	
	public ClasseComponente analizzaComponente(Retailers r) {
		ConnectivityInspector<Retailers, DefaultWeightedEdge> it = new ConnectivityInspector<>(this.grafo);
		Set<Retailers>componente =it.connectedSetOf(r);
		Double peso = 0.0;
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (componente.contains(this.grafo.getEdgeSource(e))&&
					componente.contains(this.grafo.getEdgeTarget(e))) {
				peso += this.grafo.getEdgeWeight(e);
			}
		}
		ClasseComponente result = new ClasseComponente(componente.size(), peso);
		return result;
	}

	
	
}
