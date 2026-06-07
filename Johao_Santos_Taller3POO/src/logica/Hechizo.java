package logica;

public abstract class Hechizo implements CalcularPuntaje{
	protected String nombre;
	protected String tipo;
	protected int danho;
	protected double puntuacion;

	public Hechizo(String nombre, String tipo, int danho) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.danho = danho;
		this.puntuacion = CalcularPuntaje();
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public int getDanho() {
		return danho;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	@Override
	public String toString() {
		return nombre + ";" + tipo + ";" + danho;
	}
	
	
}
