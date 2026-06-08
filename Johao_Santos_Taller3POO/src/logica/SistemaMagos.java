package logica;
import java.io.*;
import java.util.*;
public class SistemaMagos {
	private List<Mago> listaMagos;
	private List<Hechizo> listaHechizos;
	private Scanner s;
	
	public SistemaMagos() {
		listaMagos = new ArrayList<>();
		listaHechizos = new ArrayList<>();
		s = new Scanner(System.in);
	}
	
	public void cargaArchivo() {
		cargarHechizos();
		cargarMagos();
	}
	private void cargarHechizos() {
		try (Scanner lector = new Scanner(new File("Hechizos.txt"))) {
			while (lector.hasNextLine()) {
				String linea = lector.nextLine().trim();
				if (linea.isEmpty()) continue;
				String[] partes = linea.split(";");
				String nombre = partes[0];
				String tipo = partes[1];
				int danho = Integer.parseInt(partes[2]);
				
				switch (tipo) {
				case "Fuego":
					int duracion = Integer.parseInt(partes[3]);
					listaHechizos.add(new HechizoDeFuego(nombre, danho, duracion));
					break;
				case "Tierra":
					int mejora = Integer.parseInt(partes[3]);
					listaHechizos.add(new HechizoDeTierra(nombre, danho, mejora));
					break;
				case "Planta":
					String[] datosPlanta = partes[3].split(",");
					int stun = Integer.parseInt(datosPlanta[0]);
					int cant = Integer.parseInt(datosPlanta[1]);
					listaHechizos.add(new HechizoDePlanta(nombre, danho, stun, cant));
					break;
				case "Agua":
					String[] datosAgua = partes[3].split(",");
					int heal = Integer.parseInt(datosAgua[0]);
					int presion = Integer.parseInt(datosAgua[0]);
					listaHechizos.add(new HechizoDeAgua(nombre, danho, heal, presion));
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Error en la carga de datos de Hechizos.");
		}
	}
	
	private void cargarMagos() {
		try (Scanner lector = new Scanner(new File("Magos.txt"))) {
			while (lector.hasNextLine()) {
				String linea = lector.nextLine().trim();
				if (linea.isEmpty()) {
					continue;
				}
				String[] partes = linea.split(";", 2);
				String nombre = partes[0];
				Mago mago = new Mago(nombre);
				if (partes.length > 1) {
					String[] hechizos = partes[1].split("|");
					for(String h : hechizos) {
						Hechizo hechizo = buscarHechizoPorNombre(h.trim());
						if (hechizo != null) {
							mago.agregarHechizo(hechizo);
						}
					}
				}
				listaMagos.add(mago);
				
			}
		} catch (Exception e) {
			System.err.println("Error en la carga de Magos.");
		}
	}
	private Hechizo buscarHechizoPorNombre(String nombre) {
		return listaHechizos.stream().filter(h -> h.getNombre().equals(nombre)).findFirst().orElse(null);
	}
	public void guardarMagos() {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Magos.txt"))){
			for (Mago m : listaMagos) {
				escritor.write(m.toString());
				escritor.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error al guardar Magos.txt: " + e.getMessage());
		}
	}
	public void menuAdministrador() {
		while (true) {
			System.out.println("\n=== MENU ADMINISTRADOR ===\n1. Agregar Mago\n2. Modificar Mago\n3.Eliminar Mago\n4. Agregar Hechizo\n5. Modificar Hechizo\n6. Eliminar Hechizo");
			int op = obtenerEntero();
			switch (op) {
			case 1 -> agregarMago();
			case 2 -> modificarMago();
			case 3 -> eliminarMago();
			case 4 -> agregarHechizo();
			case 5 -> modificarHechizo();
			case 6 -> eliminarHechizo();
			case 7 -> {return;}
			default -> System.out.println("Opcion invalida.");
			}
		}
	}
	private void agregarMago() {
		System.out.print("Nombre del Mago: ");
		String nombre = s.nextLine().trim();
		if (buscarMagoPorNombre(nombre) != null) {
			System.out.println("Ya existe un mago con ese nombre.");
			return;
		}
		Mago mago = new Mago(nombre);
		listaMagos.add(mago);
		guardarDatos();
		System.out.println("Mago agregado.");
		
	}
	private void modificarMago() {
		System.out.print("Nombre del mago a modificar: ");
		String nombre = s.nextLine().trim();
		Mago mago = buscarMagoPorNombre(nombre);
		if (mago == null) {
			System.out.println("Mago no encontrado");
			return;
		}
		System.out.println("¿Que desea modificar?\n1. Cambiar nombre\n2. Agregar hechizo\n3. Eliminar hechizo");
		System.out.print("> ");
		int op = obtenerEntero();
		switch(op) {
		case 1:
			System.out.print("Nuevo nombre: ");
			String nuevoNombre = s.nextLine();
			if (buscarMagoPorNombre() != null) {
				System.out.println("Ya existe un mago con ese nombre.");
				return;
			}
			mago = new Mago(nuevoNombre);
			listaMagos.removeIf(m -> m.getNombre().equals(nombre));
			listaMagos.add(mago);
			break;
			
		}
	}
}
