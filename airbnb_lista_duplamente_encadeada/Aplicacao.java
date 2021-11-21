import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Aplicacao {
	final static String caminho = "./tmp/dados_airbnb.txt";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Integer quantidadeComandos;
		String roomIdRecebido;
		String comandoRecebido;
		Scanner input = new Scanner(System.in);
		ListaDuplamenteEncadeada listaDuplamenteEncadeada = new ListaDuplamenteEncadeada();
		Acomodacao[] acomodacao = Acomodacao.atribuirAcomodacao(caminho);
		roomIdRecebido = input.nextLine();
		while (!roomIdRecebido.equalsIgnoreCase("FIM")) {
			 listaDuplamenteEncadeada.inserirFinal(Acomodacao.pesquisarPorId(acomodacao, roomIdRecebido));
			roomIdRecebido = input.nextLine();
		}
		quantidadeComandos = Integer.parseInt(input.nextLine());
		for(int i = 0; i < quantidadeComandos; i++) {
			comandoRecebido = input.nextLine();
			if(comandoRecebido.charAt(0) == 'R') {
				
				if(comandoRecebido.charAt(1) == 'I') {
					try {
						System.out.println("(R) " + listaDuplamenteEncadeada.removerInicio().getRoomId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(comandoRecebido.charAt(1) == 'F') {	
					try {
						System.out.println("(R) " + listaDuplamenteEncadeada.removerFinal().getRoomId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					String[] parte;
					parte = comandoRecebido.split(" ");
			
					try {
						System.out.println("(R) " + listaDuplamenteEncadeada.remover(Integer.parseInt(parte[1])).getRoomId());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}else if (comandoRecebido.charAt(0) == 'I') {
				
				String[] parte;
				parte = comandoRecebido.split(" ");
				
				if(comandoRecebido.charAt(1) == 'I') {
					listaDuplamenteEncadeada.inserirInicio(Acomodacao.pesquisarPorId(acomodacao, parte[1]));
				}else if(comandoRecebido.charAt(1) == 'F'){
					listaDuplamenteEncadeada.inserirFinal(Acomodacao.pesquisarPorId(acomodacao, parte[1]));
				}else {
					listaDuplamenteEncadeada.inserir(Acomodacao.pesquisarPorId(acomodacao, parte[2]), Integer.parseInt(parte[1]));
				}
			}
		}
		try {
			listaDuplamenteEncadeada.imprimir();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


class Celula {

	private Acomodacao item;
	private Celula anterior;
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

	public Celula getAnterior() {
		return anterior;
	}
	public void setAnterior(Celula anterior) {
		this.anterior = anterior;
	}
}

class ListaDuplamenteEncadeada {

	private Celula primeiro;
	private Celula ultimo;
	private int tamanho;
	
	public ListaDuplamenteEncadeada() {
		
		Celula sentinela;
		
		sentinela = new Celula();
		
		primeiro = sentinela;
		ultimo = sentinela;
		
		tamanho = 0;
	}
	
	public boolean listaVazia() {
		
		boolean resp;
		
		if (primeiro == ultimo)
			resp = true;
		else
			resp = false;
		
		return resp;
	}
	
	public void inserirFinal(Acomodacao novo) {
		
		Celula novaCelula;
		
		novaCelula = new Celula(novo);
		if(!listaVazia()) {
			ultimo.setProximo(novaCelula);
			novaCelula.setAnterior(ultimo);
			
			ultimo = novaCelula;
		}else {
			primeiro.setProximo(novaCelula);
			novaCelula.setAnterior(primeiro);
			ultimo = novaCelula;
		}
		tamanho++;
		
	}
	
	
	public void inserirInicio(Acomodacao novo) {
		if(listaVazia()) {
			inserirFinal(novo);
		}else {
			Celula novaCelula = new Celula(novo);
			novaCelula.setProximo(primeiro.getProximo());
			primeiro.setProximo(novaCelula);
			novaCelula.setAnterior(primeiro);
			tamanho++;
		}
	}
	
	
	public void inserir(Acomodacao novo, Integer posicaoDeInsercao) {

		if(listaVazia()) {
			inserirFinal(novo);
		}else {
			if(posicaoDeInsercao > 0 || posicaoDeInsercao <= tamanho) {
				Celula novaCelula = new Celula(novo);
				Celula celulaNaPosicao;
				celulaNaPosicao = primeiro;
				for(int i = 0; i <= posicaoDeInsercao; i++) {
					celulaNaPosicao = celulaNaPosicao.getProximo();
				}
				novaCelula.setProximo(celulaNaPosicao.getAnterior().getProximo());
				novaCelula.setAnterior(celulaNaPosicao.getAnterior());
				celulaNaPosicao.getAnterior().setProximo(novaCelula); // Setando o proximo da cel anterior como novaCelula
				celulaNaPosicao.getAnterior().getProximo().setAnterior(novaCelula); // setando o anterior do proximo da celula que esta na posicao como novaCelula
				tamanho++;
			}
		}
	}
	
	public Acomodacao removerFinal() throws Exception {
		
		Celula removida, penultima;
		
		if (! listaVazia()) {
			
			removida = ultimo;
			
			penultima = ultimo.getAnterior();
			penultima.setProximo(null);
			removida.setAnterior(null);
			
			ultimo = penultima;
			
			tamanho--;
			
			return (removida.getItem());
		} else
			throw new Exception("Nao foi possivel remover o ultimo item da lista: a lista esta vazia!");
	}
	
	public Acomodacao remover(Integer posicaoDeRemocao)throws Exception {

		if(listaVazia()) {
			throw  new Exception("lista vazia");
		}else {
			if(posicaoDeRemocao >= 0 || posicaoDeRemocao <= tamanho) {
				Celula removida;
				removida = primeiro;
				
				for(int i = 0; i < posicaoDeRemocao; i++) {
					removida = removida.getProximo();
				}
				removida.getAnterior().setProximo(removida.getProximo());
				removida.getProximo().setAnterior(removida.getAnterior()); 
				
				tamanho--;
				return removida.getItem();
			}else {
				throw new Exception("Posicao fora de alcance");
			}
		}
	}
	
	public Acomodacao removerInicio() throws Exception{
		
		if(!listaVazia()) {
			Celula removida = primeiro.getProximo();
			
			removida.getProximo().setAnterior(primeiro);
			primeiro.setProximo(removida.getProximo());
			
			tamanho--;
			return removida.getItem();
		}else {
			throw new Exception("Lista vazia!");
		}
		
	}
	
	public void imprimir() throws Exception {
		
		Celula aux;
		int i = 0;
		if (! listaVazia()) {
			aux = primeiro.getProximo();
			while (aux != null) {
				System.out.print("["+i+"]"); aux.getItem().imprimir();
				aux = aux.getProximo();
				
				i++;
			}	
		} else
			throw new Exception("Lista vazia!");
	}
}

class LeituraArquivo{
	private BufferedReader input;
	
	LeituraArquivo(String caminho){
		try {
			input = new BufferedReader(new FileReader(caminho));
		}catch(FileNotFoundException excecao){
			System.out.println("Falha na leitura do arquivo,\nArquivo nao encontrado!");
			System.exit(0);
		}
	}
	
	public void fecharArquivo() {
		try {
			input.close();
			
		}catch(IOException excecao) {
			System.out.println("Erro ao fechar arquivo!");
			System.exit(0);
		}
	}
	
	@SuppressWarnings("finally")
	public String lerLinha() {
		String entrada = null;
		try {
			entrada = input.readLine();
		}catch(EOFException excecao) {
			entrada = null;
		}catch(IOException excecao) {
			System.out.println("Erro na leitura do arquivo!\n"+excecao);
			entrada = null;
		}finally{
			return entrada;
		}
		
	}
	public int contarObjetos() {
		int qtd = 0;
		
		while(lerLinha() != null) {
			qtd++;
		};
		return qtd-1;
	}
}

class Acomodacao {
	private int roomId;
	private int hostId;
	private String roomType;
	private String country;
	private String city;
	private String neighbourhood;
	private int reviews;
	private double overallSatisfaction;
	private int accommodates;
	private double bedrooms;
	private double price;
	private String propertyType;
	
	public Acomodacao() {}
	
	public Acomodacao(int roomId, int hostId, String roomType, String country, String city, String neighbourhood,
			int reviews, double overallSatisfaction, int accommodates, double bedrooms, double price,
			String propertyType) {
		this.roomId = roomId;
		this.hostId = hostId;
		this.roomType = roomType;
		this.country = country;
		this.city = city;
		this.neighbourhood = neighbourhood;
		this.reviews = reviews;
		this.overallSatisfaction = overallSatisfaction;
		this.accommodates = accommodates;
		this.bedrooms = bedrooms;
		this.price = price;
		this.propertyType = propertyType;
	}

	public Acomodacao(int roomId) {
		this.roomId = roomId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public int getReviews() {
		return reviews;
	}

	public void setReviews(int reviews) {
		this.reviews = reviews;
	}

	public double getOverallSatisfaction() {
		return overallSatisfaction;
	}

	public void setOverallSatisfaction(double overallSatisfaction) {
		this.overallSatisfaction = overallSatisfaction;
	}

	public int getAccommodates() {
		return accommodates;
	}

	public void setAccommodates(int accommodates) {
		this.accommodates = accommodates;
	}

	public double getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(double bedrooms) {
		this.bedrooms = bedrooms;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	public Acomodacao clone(){return new Acomodacao(this.roomId, this.hostId, this.roomType, this.country, this.city, this.neighbourhood,
			this.reviews, this.overallSatisfaction, this.accommodates, this.bedrooms, this.price,
			this.propertyType);
	}
	
	public void imprimir() {
		System.out.println("["+ this.roomId + " ## "+ this.hostId+ " ## " + this.roomType+ " ## " + this.country+ " ## " + this.city+ " ## " + this.neighbourhood+ " ## " +
				this.reviews+ " ## " + this.overallSatisfaction+ " ## " + this.accommodates+ " ## " + this.bedrooms+ " ## " + this.price+ " ## " +
				this.propertyType + "]");
	}
	
	public static Acomodacao[] atribuirAcomodacao(String caminho) {
		
		int cont;
		
		LeituraArquivo arquivoEntrada = new LeituraArquivo(caminho);
		
		cont = arquivoEntrada.contarObjetos();
		arquivoEntrada.fecharArquivo();
		arquivoEntrada = new LeituraArquivo(caminho);
		String linhaLida = arquivoEntrada.lerLinha();
		
		Acomodacao[] acomodacao = new Acomodacao[cont];
			for(int i = 0; i < cont; i++) {
				linhaLida = arquivoEntrada.lerLinha();
				String[] parte = linhaLida.split("\t");
				acomodacao[i] = new Acomodacao(Integer.parseInt(parte[0]),Integer.parseInt(parte[1]),parte[2],parte[3],parte[4],parte[5],
						Integer.parseInt(parte[6]),Double.parseDouble(parte[7]),Integer.parseInt(parte[8]),
						Double.parseDouble(parte[9]),Double.parseDouble(parte[10]),parte[11]);
			}		
			return acomodacao;
	}

	public static Acomodacao pesquisarPorId(Acomodacao[] acomodacao, String pesquisa) {
		for(int i = 0; i <=acomodacao.length-1;i++) {
			if(Integer.parseInt(pesquisa) == acomodacao[i].getRoomId()){
				return acomodacao[i];
			} 
		}
		return null;
	}
	
}