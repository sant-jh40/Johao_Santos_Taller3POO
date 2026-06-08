package logica;

public class HechizoDePlanta extends Hechizo {
	private int duracionStun, cantPlantas;

	public HechizoDePlanta(String nombre, int danho, int duracionStun, int cantPlantas) {
		super(nombre, "Planta", danho);
		this.duracionStun = duracionStun;
		this.cantPlantas = cantPlantas;
		this.puntuacion = CalcularPuntaje();

	}
	
	@Override
	public double CalcularPuntaje() {
		return danho + (duracionStun * cantPlantas);
	}
	
	@Override
	public String toString() {
		return nombre + ";Planta;" + danho + ";" + duracionStun + "," + cantPlantas;
	}
	
}
