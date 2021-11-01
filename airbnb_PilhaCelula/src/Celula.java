class Celula {

	private Acomodacao item;
	private Celula proximo;
	
	public Celula(Acomodacao novo) {
		item = novo;
		proximo = null;
	}
	
	public Celula() {
		
		item = new Acomodacao();
		proximo = null;
	}
	
	public Acomodacao getItem() {
		return item;
	}
	public void setItem(Acomodacao item) {
		this.item = item;
	}
	
	public Celula getProximo() {
		return proximo;
	}
	public void setProximo(Celula proximo) {
		this.proximo = proximo;
	}
}
