package loopbag;

import java.util.Iterator;

public class Bag<E> implements LoopBag<E>{
	private int capacity;
	private E[] items;
	private int N;
	
	/**
	 *
	 * @param capacity Fixed size bag capacity
	 */
	public Bag(int capacity){
		this.capacity = capacity;
		this.items = (E[])new Object[capacity];
	}

	@Override
	public String toString() {
		String output = "";
		if(N == 0) {
			return output;
		}
		for(int i = 0; i < N-1; i++) {
			output += items[i] + ",";
		}
		output += items[N-1];
		return output;
	}

	@Override
	public void insert(E item) {
		if(item == null) {
			return;
		}
		if(items.length > N) {
			items[N++] = item;
		}else{
			for(int i = 0; i < N-1; i++) {
				items[i] = items[i +1] ;
		}
			items[N-1] = item;
		}
	}
	@Override
	public int size() {
		return N;
	}
	@Override
	public boolean isEmpty() {
		if(N==0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean contains(E item) {
		if(item == null) {
			return false;
		}
		for(int i = 0; i < N; i ++) {
			if(items[i] != null && items[i].equals(item)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void union(LoopBag<E> lb) {
		Bag result  = (Bag) lb;
		if(lb == null) {
			return;
		}
		for(Object o : result) {
			if(!(this.contains((E)o))) {
				this.insert((E) o); 
			}
		}

	}
	@Override
	public void intersect(LoopBag<E> lb) {
		Bag inter = new Bag(capacity);
			for(Object o : items) {
				if(lb.contains((E)o)) {
					inter.insert((E)o);
				}
			}
			this.items = (E[])inter.items;
			this.N = inter.N;
		

	}
	@Override
	public Iterator<E> iterator() {
		return new bagIterator(); 
		
	}
	private class bagIterator implements Iterator<E>{
		private int count = 0;
		@Override
		public boolean hasNext() {
			if(count < capacity) {
			return true;
		}
			return false;
		}

		@Override
		public E next() {
			if(!hasNext()) {
				return null;
			}
			return items[count++];
			
		}
	}
}
