package logica;

public class HechizoDeAgua extends Hechizo{
	private int cantidadHeal, presionAgua;

	public HechizoDeAgua(String nombre, String tipo, int danho, int cantidadHeal, int presionAgua) {
		super(nombre, tipo, danho);
		this.cantidadHeal = cantidadHeal;
		this.presionAgua = presionAgua;
		this.puntuacion = CalcularPuntaje();
	}
	
	@Override 
	public double CalcularPuntaje() {
		return (danho + cantidadHeal + presionAgua)* 2.0;
	}
	
	@Override
	public String toString() {
		return nombre + ";Agua;" + danho + ";" + cantidadHeal
				+ "," + presionAgua;
	}
}
