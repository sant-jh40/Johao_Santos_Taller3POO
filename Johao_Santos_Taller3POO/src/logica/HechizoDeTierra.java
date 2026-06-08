package logica;

public class HechizoDeTierra extends Hechizo {
	private int mejoraDefensa;

	public HechizoDeTierra(String nombre, int danho, int mejoraDefensa) {
		super(nombre, "Tierra", danho);
		this.mejoraDefensa = mejoraDefensa;
		this.puntuacion = CalcularPuntaje();
		
	}
	
	@Override
	public double CalcularPuntaje() {
		return (danho * mejoraDefensa)/ 2.0;
	}
	
	@Override
	public String toString() {
		return nombre + ";Tierra;" + danho + ";" + mejoraDefensa;
	}
	
	
}
