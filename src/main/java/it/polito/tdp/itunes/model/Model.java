package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Album, DefaultEdge> grafo ;
	private List<Album> listaVertici;
	private Set<Album> setAlbum;
	private Double durataDef;
	  
	  
	
	 

	public void creaGrafo(Double minuti) {
	 
      this.dao= new ItunesDAO();
	  this.listaVertici= new ArrayList<Album>();
	  this.listaVertici.addAll(dao.getVertici(minuti));  
	  this.grafo = new SimpleGraph<Album, DefaultEdge>(DefaultEdge.class);
	  Graphs.addAllVertices(this.grafo, listaVertici) ;
	  
	  for (int i =0; i < listaVertici.size(); i++) {
			Album v1 = listaVertici.get(i);
			for (int j =i+1; j < listaVertici.size(); j++) {
				Album v2 = listaVertici.get(j);	
				
				// if there are retailers in common, we count the number of distinct sales of the two products and add the edge
				int count = this.dao.calcoloArchi(v1, v2);
				if (count > 0) {
					Graphs.addEdgeWithVertices(this.grafo,v1, v2);
				}
			}
		}
	  
	  System.out.println("Grafo creato!");
	  System.out.println("# VERTICI: " + this.grafo.vertexSet().size());
	  System.out.println("# ARCHI: " + this.grafo.edgeSet().size());	
			

		}
		
		 public int nVertici(){
			   
				return this.grafo.vertexSet().size();
				   
				   
			   }
		 
		 public int nArchi(){
			   
				return this.grafo.edgeSet().size();
				   
				   
			   }
		 
		 
		 public List<Album> getvertici() {
				
				return this.listaVertici ;
			}
		 
		 public Set<Album> getComponente(Album v1) {
				ConnectivityInspector<Album, DefaultEdge> ci =
				new ConnectivityInspector<>(this.grafo) ;
				return ci.connectedSetOf(v1) ;
			}
	
		 
		 public void creaSet(Album a1, Double dTot ){
			 
			 if(a1.getDurata()>dTot) {
				 setAlbum= null;
			 }
			 
			 else {
				 
				 setAlbum = new HashSet<Album>();	
				 setAlbum.add(a1);
				 durataDef= a1.getDurata();
				 List<Album> parziale = new ArrayList<Album>();	
				 parziale.add(a1);
				 
				Double durataParziale= a1.getDurata();
				
				ricorsione(parziale, durataParziale, dTot);
				 
				 
				 
				 
			 }
			 
		 }
		 
		 public void ricorsione(List<Album> parziale, double durataParziale, double durataMax) {
			 durataDef= durataParziale;
			 List<Album> connessi= new ArrayList<Album>();
			 connessi.addAll(this.getComponente(parziale.get(0)) );
			 if(durataDef<durataMax)
			    
				 for(Album a: connessi  ) {
			         
					 if(durataDef+a.getDurata()<durataMax) {
						 parziale.add(a);
						 durataDef+=a.getDurata();
						 ricorsione(parziale,durataDef, durataMax);
						 parziale.remove(a);
					 }
				 
				 
				 
			 }
			 if (parziale.size()> this.setAlbum.size()) {
				 this.setAlbum= new HashSet<Album>(parziale);
			 }
			 
		 }
		 
		 
 public Set<Album> getBest(){
	 
	 return this.setAlbum;
 }
		 
		 
}
