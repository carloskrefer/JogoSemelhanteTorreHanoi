/*
 * Aluno: Carlos Eduardo Krefer
 * Curso: Engenharia de Software
 * Turno: Diurno
 * Matéria: Resolução de Problemas Estruturados em Computação
 * Atividade: TDE 02
 * Data: 17/09/2023
 */

public class No {
	private Integer dado;
	private No proximoNo;

	public No() {
		this.dado = null;
		this.proximoNo = null;
	}
	
	public No(Integer dado) {
		this.dado = dado;
		this.proximoNo = null;
	}

	public Integer getDado() {
		return dado;
	}

	public void setDado(Integer dado) {
		this.dado = dado;
	}

	public No getProximoNo() {
		return proximoNo;
	}

	public void setProximoNo(No proximoNo) {
		this.proximoNo = proximoNo;
	}
}
