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
	
	enum OpcaoMenu {
		SAIR_JOGO_ATUAL, MOVIMENTAR, SOLUCAO_AUTOMATICA
	}
	
	enum OpcaoFimJogo {
		JOGAR_NOVAMENTE, SAIR
	}
	
	public Jogo() {
		scanner = new Scanner(System.in);
		torre1 = new PilhaDinamica();
		torre2 = new PilhaDinamica();
		torre3 = new PilhaDinamica();
		qtdJogadas = 0;
	}
	
	public void iniciarJogo() {
		OpcaoFimJogo opcaoFimJogoSelecionada;
		
		do {
			imprimirMensagemBoasVindas();
			
			imprimirSolicitacaoQtdDiscos();
			qtdDiscos = selecionarQtdDiscos();
			inserirDiscosAleatorios(qtdDiscos, torre1);
			
			imprimirOpcoesOrdenacao();
			isObjetivoOrdemCrescente = selecionarOpcaoOrdenacao();
			
			iniciarLoopSelecaoOpcoes();
			
			imprimirOpcoesFimJogo();
			opcaoFimJogoSelecionada = selecionarOpcaoFimJogo();
			if (opcaoFimJogoSelecionada.equals(OpcaoFimJogo.JOGAR_NOVAMENTE)) {
				limparMemoriaJogoAnterior();
			}
		} while (opcaoFimJogoSelecionada.equals(OpcaoFimJogo.JOGAR_NOVAMENTE));

	}
	
	private void imprimirOpcoesOrdenacao() {
		System.out.println("\n"
				+ "Qual o seu objetivo?\n"
				+ "0 - Ordenar de maneira crescente\n"
				+ "1 - Ordenar de maneira decrescente");
	}
	
	private boolean selecionarOpcaoOrdenacao() {
		String opcaoSelecionada;
		boolean isEntradaValida;
		
		do {
			opcaoSelecionada = scanner.nextLine();
			isEntradaValida = validarOpcaoSelecionada(new String[] {"0", "1"}, opcaoSelecionada);
		} while (!isEntradaValida);
		
		return (opcaoSelecionada.equals("0")) ? true : false;
	}
	
	private void imprimirMensagemBoasVindas() {
		System.out.println("\nBem-vindo ao jogo semelhante à Torre de Hanói.\n");
	}
	
	private int selecionarQtdDiscos() {
		String qtdSelecionada;
		boolean isEntradaValida;
		
		do {
			qtdSelecionada = scanner.nextLine();
			isEntradaValida = validarQtdDiscoSelecionada(qtdSelecionada);
		} while (!isEntradaValida);
		
		return Integer.parseInt(qtdSelecionada);
	}
	
	private boolean validarQtdDiscoSelecionada(String qtdSelecionada) {
		try {
			int valorEntrada = Integer.parseInt(qtdSelecionada);
			if (valorEntrada < 2) {
				System.out.println("A quantidade informada não é maior que 1. Tente novamente.");
			}
			return valorEntrada > 1;
		} catch (NumberFormatException e) {
			System.out.println("A quantidade informada não é um número inteiro válido. Tente novamente.");
			return false;
		}
	}
	
	private boolean validarOpcaoSelecionada(String[] opcoesValidas, String opcaoSelecionada) {
		for (int i = 0; i < opcoesValidas.length; i++) {
			if (opcoesValidas[i] == null) {
				continue;
			} else if (opcaoSelecionada.equals(opcoesValidas[i])) {
				return true;
			}
		}
		System.out.println("Opção selecionada não existe! Tente novamente.");
		return false;
	}
	
	private void imprimirSolicitacaoQtdDiscos() {
		System.out.println("Informe uma quantidade de discos maior que 1:");
	}
	
	private OpcaoFimJogo selecionarOpcaoFimJogo() {
		String opcaoSelecionada;
		boolean isEntradaValida;
		
		do {
			opcaoSelecionada = scanner.nextLine();
			isEntradaValida = validarOpcaoSelecionada(new String[] {"0", "1"}, opcaoSelecionada);
		} while (!isEntradaValida);
		
		return OpcaoFimJogo.values()[Integer.parseInt(opcaoSelecionada)];
	}
	
	private void limparMemoriaJogoAnterior() {
		torre1 = new PilhaDinamica();	
		torre2 = new PilhaDinamica();
		torre3 = new PilhaDinamica();
		qtdJogadas = 0;
	}
	
	private void imprimirOpcoesFimJogo() {
		System.out.println("\nGostaria de jogar novamente?");
		System.out.println("0 - Jogar novamente");
		System.out.println("1 - Sair");
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
		OpcaoMenu opcaoSelecionada;
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
				case SAIR_JOGO_ATUAL:
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
				case SOLUCAO_AUTOMATICA:
					realizarSolucaoAutomatica();
			}
		} while (!opcaoSelecionada.equals(OpcaoMenu.SAIR_JOGO_ATUAL));
	}
	
	// Esta solução só funciona no início do jogo por enquanto
	private void realizarSolucaoAutomatica() {
		Empilhar(torre1, torre3, 1);
		
		boolean isMovidoDiscoAlvoTorre1;
		while (!torre1.estaVazia()) {
			if (topoTorre1EhMenorTopoTorre3()) {
				Empilhar(torre1, torre3, 1);
			} else {
				isMovidoDiscoAlvoTorre1 = false;
				for (int i = 1; !isMovidoDiscoAlvoTorre1; i++) {
					Empilhar(torre3, torre2, i);
					if (torre3.estaVazia() || topoTorre1EhMenorTopoTorre3()) {
						Empilhar(torre1, torre3, 1);
						isMovidoDiscoAlvoTorre1 = true;
					}
					Empilhar(torre2, torre3, i);
				}
			}
		}
	}
	
	private boolean topoTorre1EhMenorTopoTorre3() {
		return torre1.getTopo().getDado() < torre3.getTopo().getDado();
	}
	
	// Somente chamar se: ao remover os elementos movimentados
	// os topos das pilhas sejam menores que eles.
	private void Empilhar(PilhaDinamica pilhaOrigem, 
			PilhaDinamica pilhaDestino, int numeroDiscosMovimentarPilhaOrigem) {
		if (numeroDiscosMovimentarPilhaOrigem == 1) {
			No discoMovimentado = pilhaOrigem.getTopo();
			pilhaOrigem.remover();
			pilhaDestino.inserir(discoMovimentado);
			imprimirTorres();
			qtdJogadas++;
			imprimirQtdJogadas();
		} else {
			PilhaDinamica pilhaAuxiliar = buscarPilhaAuxiliar(pilhaOrigem, pilhaDestino);
			if (numeroDiscosMovimentarPilhaOrigem == 2) {
				Empilhar(pilhaOrigem, pilhaAuxiliar, 1);
				Empilhar(pilhaOrigem, pilhaDestino, 1);
				Empilhar(pilhaAuxiliar, pilhaDestino, 1);
			} else {
				Empilhar(pilhaOrigem, pilhaAuxiliar, numeroDiscosMovimentarPilhaOrigem - 1);
				Empilhar(pilhaOrigem, pilhaDestino, 1);
				Empilhar(pilhaAuxiliar, pilhaDestino, numeroDiscosMovimentarPilhaOrigem - 1);
			}
		}
	}
	
	private PilhaDinamica buscarPilhaAuxiliar(PilhaDinamica pilhaOrigem, 
			PilhaDinamica pilhaDestino) {
		if (pilhaOrigem == torre1) {
			if (pilhaDestino == torre2) {
				return torre3;
			} else { // pilhaDestino == 3
				return torre2;
			}
		} else if (pilhaOrigem == torre2) {
			if (pilhaDestino == torre1) {
				return torre3;
			} else { // pilhaDestino == 3
				return torre1;
			}
		} else { // pilhaOrigem == 3
			if (pilhaDestino == torre1) {
				return torre2;
			} else { // pilhaDestino == 2
				return torre1;
			}
		}
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
		imprimirOpcoesTorresDesempilhar();
		PilhaDinamica torreDesejaDesempilhar = selecionarTorreDesempilhar();
		System.out.println("\nSeleciona a torre a empilhar o disco " +
				torreDesejaDesempilhar.getTopo().getDado() + ".");
		imprimirOpcoesTorresEmpilhar(torreDesejaDesempilhar);
		PilhaDinamica torreDesejaEmpilhar = selecionarTorreEmpilhar(torreDesejaDesempilhar);
		torreDesejaEmpilhar.inserir(torreDesejaDesempilhar.remover());
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
	
	private void imprimirOpcoesTorresDesempilhar() {
		boolean isPermitidoDesempilharTorre1 = verificarPermissaoParaDesempilharTorre(torre1);
		boolean isPermitidoDesempilharTorre2 = verificarPermissaoParaDesempilharTorre(torre2);
		boolean isPermitidoDesempilharTorre3 = verificarPermissaoParaDesempilharTorre(torre3);
		
		System.out.print(isPermitidoDesempilharTorre1 ? "1 - Torre 1\n" : "");
		System.out.print(isPermitidoDesempilharTorre2 ? "2 - Torre 2\n" : "");
		System.out.print(isPermitidoDesempilharTorre3 ? "3 - Torre 3\n" : "");
	}
	
	private void imprimirOpcoesTorresEmpilhar(PilhaDinamica torreSelecionadaParaDesempilhar) {
		
		boolean isPermitidoEmpilharTorre1 = verificarPermissaoParaEmpilharEmTorre(
				torreSelecionadaParaDesempilhar, torre1);
		boolean isPermitidoEmpilharTorre2 = verificarPermissaoParaEmpilharEmTorre(
				torreSelecionadaParaDesempilhar, torre2);
		boolean isPermitidoEmpilharTorre3 = verificarPermissaoParaEmpilharEmTorre(
				torreSelecionadaParaDesempilhar, torre3);
		
		System.out.print(isPermitidoEmpilharTorre1 ? "1 - Torre 1\n" : "");
		System.out.print(isPermitidoEmpilharTorre2 ? "2 - Torre 2\n" : "");
		System.out.print(isPermitidoEmpilharTorre3 ? "3 - Torre 3\n" : "");
	}
	
	private boolean verificarPermissaoParaEmpilharEmTorre(PilhaDinamica torreQueDesejaDesempilhar, 
			PilhaDinamica torreQueDesejaEmpilhar) {
		
		if (torreQueDesejaEmpilhar.estaVazia()) {
			return true;
		} else {
			if (isObjetivoOrdemCrescente) {
				return torreQueDesejaDesempilhar.getTopo().getDado() <= 
						torreQueDesejaEmpilhar.getTopo().getDado();
			} else {
				return torreQueDesejaDesempilhar.getTopo().getDado() >= 
						torreQueDesejaEmpilhar.getTopo().getDado();
			}
		}
		
	}
	
	private boolean verificarPermissaoParaDesempilharTorre(PilhaDinamica torreQueDesejaDesempilhar) {
		return torreQueDesejaDesempilhar.getTamanho() != 0;
	}
	
	private PilhaDinamica selecionarTorreEmpilhar(PilhaDinamica torreQueDesejaDesempilhar) {
		String opcaoSelecionada;
		PilhaDinamica torreQueDesejaEmpilhar = torre1;
		boolean isEntradaValida;
		boolean isPermitidoEmpilharNaTorreSelecionada = false;
				
		do {
			opcaoSelecionada = scanner.nextLine();
			
			isEntradaValida = validarOpcaoSelecionada(new String[] {"1", "2", "3"}, opcaoSelecionada);
			
			if (isEntradaValida) {
				if (opcaoSelecionada.equals("1")) {
					torreQueDesejaEmpilhar = torre1;
				} else if (opcaoSelecionada.equals("2")) {
					torreQueDesejaEmpilhar = torre2;
				} else {
					torreQueDesejaEmpilhar = torre3;
				}
				
				isPermitidoEmpilharNaTorreSelecionada = verificarPermissaoParaEmpilharEmTorre(
						torreQueDesejaDesempilhar, torreQueDesejaEmpilhar);
				
				if (!isPermitidoEmpilharNaTorreSelecionada) {
					System.out.println("Opção selecionada não existe! Tente novamente.");
					isEntradaValida = false;
				} else {
					return torreQueDesejaEmpilhar;
				}
			}
			
		} while (!isEntradaValida);

		return torreQueDesejaEmpilhar;
	}
	
	private PilhaDinamica selecionarTorreDesempilhar() {
		String opcaoSelecionada;
		boolean isEntradaValida;
		String[] opcoesValidas = new String[3];
		
		boolean isPermitidoDesempilharTorre1 = verificarPermissaoParaDesempilharTorre(torre1);
		boolean isPermitidoDesempilharTorre2 = verificarPermissaoParaDesempilharTorre(torre2);
		boolean isPermitidoDesempilharTorre3 = verificarPermissaoParaDesempilharTorre(torre3);
		
		if (isPermitidoDesempilharTorre1) {
			opcoesValidas[0] = "1";
		}
		if (isPermitidoDesempilharTorre2) {
			opcoesValidas[1] = "2";
		}
		if (isPermitidoDesempilharTorre3) {
			opcoesValidas[2] = "3";
		}
		
		do {
			opcaoSelecionada = scanner.nextLine();
			isEntradaValida = validarOpcaoSelecionada(opcoesValidas, opcaoSelecionada);
		} while (!isEntradaValida);
		
		if (opcaoSelecionada.equals("1")) {
			return torre1;
		} else if (opcaoSelecionada.equals("2")) {
			return torre2;
		} else {
			return torre3;
		}
	}
	
	private OpcaoMenu selecionarOpcao() {
		String opcaoSelecionada;
		boolean isEntradaValida;
		
		boolean solucaoAutomaticaEstaDisponivel = verificarDisponibilidadeSolucaoAutomatica();
		String[] opcoesValidas = solucaoAutomaticaEstaDisponivel ? 
				new String[] {"0", "1", "2"} : new String[] {"0", "1"};
		
		do {
			opcaoSelecionada = scanner.nextLine();
			
			isEntradaValida = validarOpcaoSelecionada(opcoesValidas, opcaoSelecionada);
		} while (!isEntradaValida);
		
		return OpcaoMenu.values()[Integer.parseInt(opcaoSelecionada)];
	}
	
	private void imprimirOpcoesMenu() {
		System.out.println("0 - Sair do jogo atual");
		System.out.println("1 - Movimentar");
		boolean solucaoAutomaticaEstaDisponivel = verificarDisponibilidadeSolucaoAutomatica();
		if (solucaoAutomaticaEstaDisponivel) {
			System.out.println("2 - Solução automática");
		}
	}
	
	private boolean verificarDisponibilidadeSolucaoAutomatica() {
		return qtdJogadas == 0;
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
