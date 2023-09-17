

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
