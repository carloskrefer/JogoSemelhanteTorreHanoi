
public class PilhaDinamica {
	private No topo;
	private int tamanho;
	
	public int getTamanho() {
		return tamanho;
	}
	
	public No getTopo() {
		return topo;
	}
	
	public PilhaDinamica() {
		topo = null;
		tamanho = 0;
	}
	
	private boolean pilhaEstaVazia() {
		return (topo == null);
	}
	
	public void inserir(int dado) {
		No antigoTopo = topo;
		topo = new No(dado);
		topo.setProximoNo(antigoTopo);
		tamanho++;
	}
	
	public No remover() {
		if (pilhaEstaVazia()) {
			return null;
		} else {
			tamanho--;
			No antigoTopo = topo;
			topo = topo.getProximoNo();
			return antigoTopo;
		}
	}
	
	public void imprimir() {
		No no = topo;
		while (no != null) {
			System.out.print(no.getDado() + " -> ");
			no = no.getProximoNo();
		}
		System.out.println("null");
	}
	
}
