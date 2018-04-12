package com.jcute.core.toolkit;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DirectedGraph<T> implements Iterable<T>{

	private final Map<T,Set<T>> nodes = new HashMap<T,Set<T>>();

	public boolean addNode(T node){
		if(nodes.containsKey(node)){
			return false;
		}
		nodes.put(node,new HashSet<T>());
		return true;
	}

	public boolean addEdge(T head,T foot){
		if(!nodes.containsKey(head)){
			this.addNode(head);
		}
		if(!nodes.containsKey(foot)){
			this.addNode(foot);
		}
		nodes.get(head).add(foot);
		return true;
	}

	public Set<T> getEdges(T head){
		if(nodes.containsKey(head)){
			return Collections.unmodifiableSet(nodes.get(head));
		}else{
			return Collections.unmodifiableSet(new HashSet<T>());
		}
	}

	@Override
	public Iterator<T> iterator(){
		return this.nodes.keySet().iterator();
	}

}