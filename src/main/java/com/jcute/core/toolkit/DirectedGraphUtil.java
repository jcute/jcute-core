package com.jcute.core.toolkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectedGraphUtil<T>{

	private static final String MARKED = "MARKED";
	private static final String COMPLETE = "COMPLETE";

	private DirectedGraph<T> graph;
	private Map<T,String> marks;
	private List<T> verticesInCycles;

	public DirectedGraphUtil(DirectedGraph<T> graph){
		this.graph = graph;
		this.marks = new HashMap<T,String>();
		this.verticesInCycles = new ArrayList<T>();
	}

	public List<T> getVerticesInCycles(){
		return this.verticesInCycles;
	}

	public boolean hasCycle(){
		this.marks.clear();
		this.verticesInCycles.clear();
		for(T v : this.graph){
			if(!marks.containsKey(v)){
				mark(v);
			}
		}
		return !this.verticesInCycles.isEmpty();
	}

	public Set<T> getSort(){
		Set<T> result = new LinkedHashSet<T>();
		Iterator<T> iter = this.graph.iterator();
		while(iter.hasNext()){
			T current = iter.next();
			int count = this.graph.getEdges(current).size();
			if(count == 0){
				result.add(current);
			}
		}

		iter = this.graph.iterator();
		while(iter.hasNext()){
			T current = iter.next();
			int count = this.graph.getEdges(current).size();
			if(count == 0){
				continue;
			}
			result.addAll(this.search(current));
			result.add(current);
		}
		return result;
	}

	private Set<T> search(T head){
		Set<T> result = new LinkedHashSet<T>();
		Set<T> edges = this.graph.getEdges(head);
		if(null == edges || edges.size() == 0){
			return result;
		}
		Iterator<T> iter = edges.iterator();
		while(iter.hasNext()){
			T current = iter.next();
			int count = this.graph.getEdges(current).size();
			if(count == 0){
				result.add(current);
			}else{
				result.addAll(this.search(current));
				result.add(current);
			}
		}
		return result;
	}

	private boolean mark(T vertex){
		List<T> localCycles = new ArrayList<T>();
		marks.put(vertex,MARKED);
		for(T u : this.graph.getEdges(vertex)){
			if(marks.containsKey(u) && marks.get(u).equals(MARKED)){
				localCycles.add(vertex);
			}else if(!marks.containsKey(u)){
				if(mark(u)){
					localCycles.add(vertex);
				}
			}
		}
		marks.put(vertex,COMPLETE);
		verticesInCycles.addAll(localCycles);
		return !localCycles.isEmpty();
	}

}