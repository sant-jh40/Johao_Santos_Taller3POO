package logica;

public class HechizoDeFuego extends Hechizo{
	private int duracionQuemadura;

	public HechizoDeFuego(String nombre, int danho, int duracionQuemadura) {
		super(nombre, "Fuego", danho);
		this.duracionQuemadura = duracionQuemadura;
		this.puntuacion = CalcularPuntaje();
	}
	
	@Override
	public double CalcularPuntaje() {
		return danho * duracionQuemadura;
	}
	
	@Override
	public String toString() {
		return nombre + ";Fuego;" + danho + ";" + duracionQuemadura;
	}
}
