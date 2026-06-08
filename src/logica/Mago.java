package logica;

import java.util.*;

public class Mago {
	private String nombre;
	private List<Hechizo> hechizos;

	public Mago(String nombre) {
		this.nombre = nombre;
		this.hechizos = new ArrayList<>();
	}

	public void agregarHechizo(Hechizo hechizo) {
		if (!hechizos.contains(hechizo)) {
			hechizos.add(hechizo);
		}
	}

	public boolean tieneHechizo(String nombreHechizo) {
		return hechizos.stream().anyMatch(h -> h.getNombre().equals(nombreHechizo));
	}

	public void eliminarHechizo(String nombreHechizo) {
		hechizos.removeIf(h -> h.getNombre().equals(nombreHechizo));
	}

	public double calcularPuntajeTotal() {
		return hechizos.stream().mapToDouble(Hechizo::getPuntuacion).sum();
	}

	public String getNombre() {
		return nombre;
	}

	public List<Hechizo> getHechizos() {
		return new ArrayList<>(hechizos);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nombre).append(";");
		for (int i = 0; i < hechizos.size(); i++) {
			if (i > 0)
				sb.append("|");
			sb.append(hechizos.get(i).getNombre());
		}
		return sb.toString();
	}
}
