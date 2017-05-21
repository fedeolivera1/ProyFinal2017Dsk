package gpd.dominio.usuario;

public enum TipoUsr {
	A("Admin", 'A'),
	V("Vendedor", 'V');
	
	private final String tipoUsr;
	private final char asChar;
	
	TipoUsr(String tipoUsr, char asChar) {
		this.tipoUsr = tipoUsr;
		this.asChar = asChar;
	}

	public String getTipoUsr() {
		return tipoUsr;
	}
	
	public char getAsChar() {
		return asChar;
	}
}
