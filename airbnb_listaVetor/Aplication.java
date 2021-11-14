import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Aplication {
	final static String caminho = "./tmp/dados_airbnb.txt";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ListaLinear lista = new ListaLinear(5700);
		Acomodacao[] acomodacao = Acomodacao.atribuirAcomodacao(caminho);
		Scanner input = new Scanner(System.in);
		String pesquisa;
		Integer qtdComandos;
		pesquisa = input.nextLine();
		while(!pesquisa.equalsIgnoreCase("FIM")) {
			lista.inserirFinal(Acomodacao.pesquisarPorId(acomodacao, pesquisa));
			pesquisa = input.nextLine();
		}
		qtdComandos = Integer.parseInt(input.nextLine());
		for (int i = 0; i < qtdComandos; i++) {
			String comando[] = input.nextLine().split(" ");
			
			if(comando[0].charAt(0) == 'R') {
				if(comando[0].contains("*")) {
					try {
						System.out.println("(R)" + lista.remover(Integer.parseInt(comando[1])).getRoomId());
					} catch (NumberFormatException e) {

						e.printStackTrace();
					} catch (Exception e) {

						e.printStackTrace();
					}
				}else if(comando[0].contains("I")) {
					System.out.println("(R)" + lista.removerInicio().getRoomId());
				}else if(comando[0].contains("F")) {
					System.out.println("(R)" + lista.removerFinal().getRoomId());
				}
				
			}else if(comando[0].charAt(0) == 'I') {
				if(comando[0].contains("*")) {
					try {
						lista.inserir(Acomodacao.pesquisarPorId(acomodacao, comando[2]), Integer.parseInt(comando[1]));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(comando[0].charAt(1) == 'I') {
					lista.inserirInicio(Acomodacao.pesquisarPorId(acomodacao, comando[1]));
				}else if(comando[0].contains("F")) {
					lista.inserirFinal(Acomodacao.pesquisarPorId(acomodacao, comando[1]));
				}
				
			}
		}
		try {
			lista.imprimir();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
class ListaLinear {

	private Acomodacao lista[];
	private int primeiro;
	private int ultimo;
	private int tamanho;
	
	public ListaLinear(int M) {
		
		lista = new Acomodacao[M];
		tamanho = 0;
		primeiro = 0;
		ultimo = 0;
	}
	
	public boolean listaVazia() {
		
		boolean resp;
		
		if (primeiro == ultimo)
			resp = true;
		else
			resp = false;
		
		return resp;
	}
	
	public boolean listaCheia() {
		
		boolean resp;
		
		if (ultimo == lista.length) 
			resp = true;
		else
			resp = false;
		
		return resp;
	}
	
	public void inserirInicio(Acomodacao novo) {
		if (!listaCheia()) {
			for(int i = ultimo; i >= primeiro; i--) {
				lista[i+1] = lista[i];		
			}
			lista[primeiro] = novo;
			ultimo++;
			tamanho++;
		}
	}

	
// O problema ta aqui nesse inserirFinal
	
	public void inserirFinal(Acomodacao novo) {
		if (!listaCheia()) {
			if(primeiro != tamanho) {
				lista[ultimo+1] = novo;
				ultimo++;
				tamanho = ultimo;
			}else {
				lista[ultimo] = novo;
			}


		}
	}
	
	
	public void inserir(Acomodacao novo, int posicao) throws Exception {
		
		if (! listaCheia()) {
			if ((posicao >= 0) && (posicao <= tamanho)) {
				for (int i = ultimo; i > posicao; i--)
					lista[i] = lista[i-1];
				
				lista[posicao] = novo;
				
				ultimo++;
				tamanho++;
			} else
				throw new Exception("posicao informada esta invalida!");
		} else
			throw new Exception("a lista esta cheia!");
	}
	public Acomodacao removerInicio() {
		Acomodacao removida;
		if(!listaVazia()) {
			removida = lista[0];
			for(int i = primeiro; i <= ultimo; i++) {
				lista[i] = lista[i+1];
			}
			tamanho--;
			ultimo--;
			return removida;
		}
		
		return null;
	}
	
	
	public Acomodacao removerFinal() {
		Acomodacao removida;
		if(!listaVazia()) {
			ultimo--;
			removida = lista[ultimo];
			tamanho--;

			return removida;
		}
		
		return null;
	}
	
	
	
	public Acomodacao remover(int posicao) throws Exception {
		
		Acomodacao removido;
		
		if (! listaVazia()) {
			if ((posicao >= 0) && (posicao < tamanho)) {
				
				removido = lista[posicao];
				tamanho--;
				
				for (int i = posicao; i < tamanho; i++) {
					lista[i] = lista[i+1];
				}
				
				ultimo--;
				
				return removido;
			} else
				throw new Exception("posicao informada esta invalida!");
		} else
			throw new Exception(" a lista esta vazia!");
	}
	
	public void imprimir() throws Exception {
		
		if (! listaVazia()) {
			
			for (int i = primeiro; i < ultimo; i++) {
				lista[i].imprimir();
			}
		} else
			throw new Exception("a lista esta vazia!");
	}
}




// Fim da Classe Lista
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