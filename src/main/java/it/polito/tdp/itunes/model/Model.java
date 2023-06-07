package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private Graph<Album, DefaultEdge> grafo ;
	
	public void creaGrafo(Double duration) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class) ;
		
		ItunesDAO dao = new ItunesDAO() ;
		Graphs.addAllVertices(this.grafo, dao.getAlbumsWithDuration(duration)) ;
		Map<Integer, Album> albumIdMap = new HashMap<>();
		for(Album a: this.grafo.vertexSet()) {
			albumIdMap.put(a.getAlbumId(), a) ;
		}
		
		List<Pair<Integer,Integer>> archi = dao.getCompatibleAlbums() ;
		for(Pair<Integer, Integer>arco: archi) {
			if(albumIdMap.containsKey(arco.getFirst()) && 
					albumIdMap.containsKey(arco.getSecond()) &&
					!arco.getFirst().equals(arco.getSecond())) {
//				System.out.println(arco.getFirst(), arco.getSecond()) ;
				this.grafo.addEdge(albumIdMap.get(
						arco.getFirst()), albumIdMap.get(arco.getSecond()));
			}
		}
		
		System.out.println("Vertici: "+this.grafo.vertexSet().size()) ;
		System.out.println("Archi:   "+this.grafo.edgeSet().size()) ;
	}
	
	public List<Album> getAlbums() {
		List<Album> list = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(list);
		return list ;
	}
	
	public Set<Album> getComponente(Album a1) {
		ConnectivityInspector<Album, DefaultEdge> ci =
				new ConnectivityInspector<>(this.grafo) ;
		return ci.connectedSetOf(a1) ;
	}
	
}
