import java.util.Random;
import java.util.Scanner;

public class Jogo {
	private Scanner scanner;
	private int qtdDiscos;
	private boolean isObjetivoOrdemCrescente;
	private final int LIMITE_NUMERO_ALEATORIO = 100;
	private PilhaDinamica torre1;
	private PilhaDinamica torre2;
	private PilhaDinamica torre3;
	
	enum opcaoMenu {
		SAIR, MOVIMENTAR, SOLUCAO_AUTOMATICA
	}
	
	public Jogo() {
		scanner = new Scanner(System.in);
		torre1 = new PilhaDinamica();
		torre2 = new PilhaDinamica();
		torre3 = new PilhaDinamica();
	}
	
	public void iniciarNovoJogo() {
		System.out.println("Bem-vindo ao jogo Torre de Hanói.\n\n"
				+ "Informe a quantidade de discos: ");
		qtdDiscos = Integer.parseInt(scanner.nextLine());
		inserirDiscosAleatorios(qtdDiscos, torre1);
		System.out.println("\n"
				+ "Qual o seu objetivo?\n"
				+ "0 - Ordenar de maneira crescente\n"
				+ "1 - Ordenar de maneira decrescente");
		isObjetivoOrdemCrescente = (scanner.nextLine().equals("0")) ? true : false;
		iniciarSelecaoOpcoes();
	}
	
	private void inserirDiscosAleatorios(int qtdDiscos, PilhaDinamica torreAlvo) {
		for (int i = 0; i < qtdDiscos; i++) {
			torreAlvo.inserir(gerarNumeroInteiroAleatorioDeZeroA(LIMITE_NUMERO_ALEATORIO));
		}
	}
	
	private int gerarNumeroInteiroAleatorioDeZeroA(int limiteSuperiorInclusivo) {
		return new Random().nextInt(limiteSuperiorInclusivo + 1);
	}
	
	private void iniciarSelecaoOpcoes() {
		opcaoMenu opcaoSelecionada;
		do {
			imprimirOpcoesMenu();
			opcaoSelecionada = selecionarOpcao();
			
		} while (!opcaoSelecionada.equals(opcaoMenu.SAIR));
	}
	
	private opcaoMenu selecionarOpcao() {
		return opcaoMenu.values()[Integer.parseInt(scanner.nextLine())];
	}
	
	private void imprimirOpcoesMenu() {
		System.out.println("0 - Sair do jogo");
		System.out.println("1 - Movimentar");
		System.out.println("2 - Solução automática\n");
		imprimirTorres();
		torre1.imprimir();
		torre2.imprimir();
		torre3.imprimir();
	}
	
	private void imprimirTorres() {
		No discoTorre1 = torre1.getTopo();
		No discoTorre2 = torre2.getTopo();
		No discoTorre3 = torre3.getTopo();
		
		System.out.println("Torre 1\t\tTorre 2\t\tTorre 3\n");
		
		// Considere o exemplo com duas pilhas (4 -> 7) e (2):
		// 4		h=2		<- alturaAtualImpressao
		// 7	2	h=1
		// No início a alturaAtualImpressao tem h = 2,
		// a altura da maior pilha. Essa variável serve para que
		// não sejam percorridas e impressas as pilhas que
		// não alcancem a altura atual da impressão. No caso, a segunda pilha.
		// Mas, quando a altura for h=1, ela deverá iniciar a
		// a percorrer e imprimir a segunda pilha também.
		int alturaAtualImpressao = obterTamanhoMaiorTorre();
		
		for (int i = 0; i < obterTamanhoMaiorTorre(); i++, alturaAtualImpressao--) {
			if (torre1.getTamanho() >= alturaAtualImpressao) { 
				System.out.print(((discoTorre1 == null) ? "" : discoTorre1.getDado()));
				discoTorre1 = (discoTorre1 == null) ? discoTorre1 : discoTorre1.getProximoNo();
			}
			System.out.print("\t\t");
			if (torre2.getTamanho() >= alturaAtualImpressao) { 
				System.out.print(((discoTorre2 == null) ? "" : discoTorre2.getDado()));
				discoTorre2 = (discoTorre2 == null) ? discoTorre2 : discoTorre2.getProximoNo();
			} 
			System.out.print("\t\t");
			if (torre3.getTamanho() >= alturaAtualImpressao) { 
				System.out.print(((discoTorre3 == null) ? "" : discoTorre3.getDado()));
				discoTorre3 = (discoTorre3 == null) ? discoTorre3 : discoTorre3.getProximoNo();
			}
			System.out.println("");
		}
	}
	
	private int obterTamanhoMaiorTorre() {
		return (torre1.getTamanho() >= torre2.getTamanho()) ?
				((torre1.getTamanho() >= torre3.getTamanho()) ? torre1.getTamanho() : torre3.getTamanho()) :
					((torre2.getTamanho() >= torre3.getTamanho()) ? torre2.getTamanho() : torre3.getTamanho());
	}

}
