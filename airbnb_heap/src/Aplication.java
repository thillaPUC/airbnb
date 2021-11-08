import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Aplication {
	final static String caminho = "/tmp/dados_airbnb.txt";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Integer qtdComandos;
		Integer idRetorno;
		String pesquisa;
		Scanner input = new Scanner(System.in);
		Acomodacao[] acomodacao = Acomodacao.atribuirAcomodacao(caminho);
		qtdComandos = Integer.parseInt(input.nextLine());
		Acomodacao[] acomodacaoOrdenada = new Acomodacao[qtdComandos];
		for(int i = 0; i < qtdComandos; i++) {
			pesquisa = input.nextLine();
			acomodacaoOrdenada[i] = Acomodacao.pesquisarPorId(acomodacao, pesquisa);
		}
		heapSort(acomodacaoOrdenada, qtdComandos);
		input.close();
		for (int i = 0; i < qtdComandos; i++) {
			acomodacaoOrdenada[i].imprimir();
		}
	}
	
	public static void createLog(Long tempoIni, Long tempoFin) throws IOException {
		FileWriter arq = new FileWriter("732434_heapsort.txt");
		BufferedWriter gravar = new BufferedWriter(arq);
		gravar.write("732434\t" + (tempoFin - tempoIni) + "\t" + comparacoes + "\t" + trocas);
		gravar.close();
		arq.close();
	}

	static int comparacoes = 0;
	static int trocas = 0;
	static String item = "";
	
	static void heapSort(Acomodacao[] acomodacaoOrdenada, int n) {	    
		Acomodacao[] tmp = new Acomodacao[n + 1];
		for(int i = 0; i < n; i++) {
			tmp[i+1] = acomodacaoOrdenada[i];
		}
		acomodacaoOrdenada = tmp;

		for(int tamHeap = 2; tamHeap <= n; tamHeap++) {
			constroi(acomodacaoOrdenada, tamHeap);
		}

		int tamHeap = n;
		while(tamHeap > 1) {
			troca(acomodacaoOrdenada, 1, tamHeap--);
			reconstroi(acomodacaoOrdenada, tamHeap);
		}

		tmp = acomodacaoOrdenada;
		acomodacaoOrdenada = new Acomodacao[n];
		for(int i = 0; i < n; i++) {
			acomodacaoOrdenada[i] = tmp[i+1];
		}
	}

	static void constroi(Acomodacao[] acomodacaoOrdenada, int tamHeap) {
		for(int i = tamHeap; i > 1 && 
				(acomodacaoOrdenada[i].getReviews() > acomodacaoOrdenada[i/2].getReviews() || 
						acomodacaoOrdenada[i].getRoomId() > acomodacaoOrdenada[i/2].getRoomId()); i /= 2) {
			troca(acomodacaoOrdenada, i, i/2);
			comparacoes++;
		}
	}

	static void reconstroi(Acomodacao[] acomodacaoOrdenada, int tamHeap) {
		int i = 1;
		while(i <= (tamHeap/2)) {
			int filho = getMaiorFilho(acomodacaoOrdenada, i, tamHeap);
			comparacoes++;	
			if(acomodacaoOrdenada[i].getReviews() < acomodacaoOrdenada[filho].getReviews() ||
					acomodacaoOrdenada[i].getReviews() == acomodacaoOrdenada[filho].getReviews()) {
				troca(acomodacaoOrdenada, i, filho);
				i = filho;
			} else {
				i = tamHeap;
			}
				
		}
	}

	static int getMaiorFilho(Acomodacao[] acomodacaoOrdenada, int i, int tamHeap) {
		int filho;	
		comparacoes++;
		if(2*i == tamHeap || 
				acomodacaoOrdenada[2*i].getRoomId() > acomodacaoOrdenada[2*i+1].getRoomId() || 
				acomodacaoOrdenada[2*i].getReviews() > acomodacaoOrdenada[2*i+1].getReviews()) {
				filho = 2*i;
			} else {
				filho = 2*i + 1;
			}

		return filho;
	}

	static void troca(Acomodacao[] acomodacaoOrdenada, int i, int j) {
		Acomodacao tmp = acomodacaoOrdenada[i];
		acomodacaoOrdenada[i] = acomodacaoOrdenada[j];
		trocas++;
		acomodacaoOrdenada[j] = tmp;
		trocas++;
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