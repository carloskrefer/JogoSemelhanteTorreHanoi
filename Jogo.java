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
	private int qtdJogadas;
	
	enum opcaoMenu {
		SAIR, MOVIMENTAR, SOLUCAO_AUTOMATICA
	}
	
	public Jogo() {
		scanner = new Scanner(System.in);
		torre1 = new PilhaDinamica();
		torre2 = new PilhaDinamica();
		torre3 = new PilhaDinamica();
		qtdJogadas = 0;
	}
	
	public void iniciarNovoJogo() {
		System.out.println("Bem-vindo ao jogo semelhante à Torre de Hanói.\n\n"
				+ "Informe a quantidade de discos: ");
		qtdDiscos = Integer.parseInt(scanner.nextLine());
		inserirDiscosAleatorios(qtdDiscos, torre1);
		System.out.println("\n"
				+ "Qual o seu objetivo?\n"
				+ "0 - Ordenar de maneira crescente\n"
				+ "1 - Ordenar de maneira decrescente");
		isObjetivoOrdemCrescente = (scanner.nextLine().equals("0")) ? true : false;
		iniciarLoopSelecaoOpcoes();
	}
	
	private void inserirDiscosAleatorios(int qtdDiscos, PilhaDinamica torreAlvo) {
		for (int i = 0; i < qtdDiscos; i++) {
			torreAlvo.inserir(gerarNumeroInteiroAleatorioDeZeroA(LIMITE_NUMERO_ALEATORIO));
		}
	}
	
	private int gerarNumeroInteiroAleatorioDeZeroA(int limiteSuperiorInclusivo) {
		return new Random().nextInt(limiteSuperiorInclusivo + 1);
	}
	
	private void iniciarLoopSelecaoOpcoes() {
		opcaoMenu opcaoSelecionada;
		boolean existeAlgumaTorreCheia;
		boolean torreEstaOrdenada = false;
		do {
			System.out.println();
			imprimirTorres();
			imprimirQtdJogadas();
			System.out.println();
			imprimirOpcoesMenu();
			opcaoSelecionada = selecionarOpcao();
			switch (opcaoSelecionada) {
				case SAIR:
					return;
				case MOVIMENTAR:
					qtdJogadas++;
					iniciarMovimento();
					PilhaDinamica torreCheia = buscarTorreCheia();
					existeAlgumaTorreCheia = (torreCheia != null);
					if (existeAlgumaTorreCheia) {
						torreEstaOrdenada = verificarSeTorreEstaOrdenada(torreCheia);
						if (torreEstaOrdenada) {
							System.out.println();
							imprimirTorres();
							imprimirMensagemVitoria();
							return;
						}
					}
					break;
			}
		} while (!opcaoSelecionada.equals(opcaoMenu.SAIR));
	}
	
	private void imprimirQtdJogadas() {
		System.out.println("Quantidade de jogadas: " + qtdJogadas + ".");
	}
	
	private void imprimirMensagemVitoria() {
		System.out.println("\nVocê venceu!");
		System.out.println("Ordenação concluída em " + qtdJogadas + " jogadas.");
	}
	
	private void iniciarMovimento() {
		System.out.println("\nSelecione a torre a desempilhar.");
		imprimirOpcoesTorres();
		No discoSelecionado = selecionarTorre().remover();
		System.out.println("\nSeleciona a torre a empilhar o disco " +
				discoSelecionado.getDado() + ".");
		selecionarTorre().inserir(discoSelecionado);
	}
	
	private boolean verificarSeTorreEstaOrdenada(PilhaDinamica torre) {
		if (isObjetivoOrdemCrescente) {
			return verificarSeTorreEstaCrescente(torre);
		} else {
			return verificarSeTorreEstaDecrescente(torre);
		}
	}
	
	private boolean verificarSeTorreEstaCrescente(PilhaDinamica torre) {
		boolean isOrdemCrescente = true;
		No noAtual = torre.getTopo();
		int valorAtual;
		int valorSeguinte;
		while ((noAtual != null) && (noAtual.getProximoNo() != null)) {
			valorAtual = noAtual.getDado();
			valorSeguinte = noAtual.getProximoNo().getDado();
			if (valorAtual > valorSeguinte) {
				isOrdemCrescente = false;
				break;
			}
			noAtual = noAtual.getProximoNo();
		}
		return isOrdemCrescente;
	}

	private boolean verificarSeTorreEstaDecrescente(PilhaDinamica torre) {
		boolean isOrdemDecrescente = true;
		No noAtual = torre.getTopo();
		int valorAtual;
		int valorSeguinte;
		while ((noAtual != null) && (noAtual.getProximoNo() != null)) {
			valorAtual = noAtual.getDado();
			valorSeguinte = noAtual.getProximoNo().getDado();
			if (valorAtual < valorSeguinte) {
				isOrdemDecrescente = false;
				break;
			}
			noAtual = noAtual.getProximoNo();
		}
		return isOrdemDecrescente;
	}
	
	private PilhaDinamica buscarTorreCheia() {
		if (torre1.getTamanho() == qtdDiscos) {
			return torre1;
		} else if (torre2.getTamanho() == qtdDiscos) {
			return torre2;
		} else if (torre3.getTamanho() == qtdDiscos) {
			return torre3;
		} else {
			return null;
		}
	}
	
	private void imprimirOpcoesTorres() {
		System.out.print((torre1.estaVazia()) ? "" : "1 - Torre 1\n");
		System.out.print((torre2.estaVazia()) ? "" : "2 - Torre 2\n");
		System.out.print((torre3.estaVazia()) ? "" : "3 - Torre 3\n");
		if (torre1.estaVazia() && torre2.estaVazia() && torre3.estaVazia()) {
			System.out.println("Não há opções para seleção de torres, pois estão todas vazias.");
		}
		System.out.println();
	}
	
	private PilhaDinamica selecionarTorre() {
		int numTorreEscolhida = Integer.parseInt(scanner.nextLine());
		
		switch (numTorreEscolhida) {
			case 1:
				return torre1;
			case 2:
				return torre2;
			default:
				return torre3;
		}
	}
	
	private opcaoMenu selecionarOpcao() {
		return opcaoMenu.values()[Integer.parseInt(scanner.nextLine())];
	}
	
	private void imprimirOpcoesMenu() {
		System.out.println("0 - Sair do jogo");
		System.out.println("1 - Movimentar");
		System.out.println("2 - Solução automática");
	}
	
	private void imprimirTorres() {
		No discoTorre1 = torre1.getTopo();
		No discoTorre2 = torre2.getTopo();
		No discoTorre3 = torre3.getTopo();
		
		System.out.println("---------------------------------------");
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
		System.out.println("---------------------------------------");
	}
	
	private int obterTamanhoMaiorTorre() {
		return (torre1.getTamanho() >= torre2.getTamanho()) ?
				((torre1.getTamanho() >= torre3.getTamanho()) ? torre1.getTamanho() : torre3.getTamanho()) :
					((torre2.getTamanho() >= torre3.getTamanho()) ? torre2.getTamanho() : torre3.getTamanho());
	}

}
