package it.polito.tdp.food.db;

public class Adiacenze {
	private Condiment c1;
	private Condiment c2;
	private int cont;
	public Adiacenze(Condiment c1, Condiment c2, int cont) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.cont = cont;
	}
	public Condiment getC1() {
		return c1;
	}
	public void setC1(Condiment c1) {
		this.c1 = c1;
	}
	public Condiment getC2() {
		return c2;
	}
	public void setC2(Condiment c2) {
		this.c2 = c2;
	}
	public int getCont() {
		return cont;
	}
	public void setCont(int cont) {
		this.cont = cont;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenze other = (Adiacenze) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("Adiacenze [c1=%s, c2=%s, cont=%s]", c1, c2, cont);
	}
	
	
	

}