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
	
	public void guardarDatos() {
        guardarMagos();
        guardarHechizos();
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
	private void guardarHechizos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/Hechizos.txt"))) {
            for (Hechizo h : listaHechizos) {
                writer.write(h.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar Hechizos.txt: " + e.getMessage());
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
			if (buscarMagoPorNombre(nuevoNombre) != null) {
				System.out.println("Ya existe un mago con ese nombre.");
				return;
			}
			mago = new Mago(nuevoNombre);
			listaMagos.removeIf(m -> m.getNombre().equals(nombre));
			listaMagos.add(mago);
			break;
		case 2:
			System.out.print("Nombre del hechizo: ");
			String nombreH = s.nextLine();
			Hechizo hechizo = buscarHechizoPorNombre(nombreH);
			if (hechizo == null) {
				System.out.println("Hechizo no encontrado.");
				return;
			}
			mago.agregarHechizo(hechizo);
			break;
		case 3:
			System.out.print("Nombre del hechizo a eliminar: ");
			String hAEliminar = s.nextLine().trim();
			mago.eliminarHechizo(hAEliminar);
			break;
		default:
			System.out.println("opcion invalida");
			
		}
		guardarDatos();
		System.out.println("Mago modificado.");
	}
	private void eliminarMago() {
		System.out.print("Nombre del mago a eliminar: ");
		String nombre = s.nextLine().trim();
		if (listaMagos.removeIf(m -> m.getNombre().equals(nombre))) {
			guardarDatos();
			System.out.println("Mago Eliminado");
		} else {
			System.out.println("Mago no encontrado.");
		}
		
	}
	private void agregarHechizo() {
		System.out.print("Nombre: ");
		String nombre = s.nextLine().trim();
		if (buscarHechizoPorNombre(nombre) != null) {
			System.out.println("Ya existe un hechizo con ese nombre.");
			return;
		}
		System.out.print("Tipo (Fuego/Tierra/Agua/Planta): ");
		String tipo = s.nextLine().trim();
		System.out.print("Daño: ");
		int danho = obtenerEntero();
		
		switch (tipo) {
		case "Fuego":
			System.out.print("Duracion quemadura: ");
			int duracion = obtenerEntero();
			listaHechizos.add(new HechizoDeFuego(nombre, danho, duracion));
			break;
		case "Tierra":
			System.out.print("Mejora Defensa: ");
            int mejora = obtenerEntero();
            listaHechizos.add(new HechizoDeTierra(nombre, danho, mejora));
            break;
		case "Agua":
			System.out.print("Cantidad Heal: ");
            int heal = obtenerEntero();
            System.out.print("Presión Agua: ");
            int presion = obtenerEntero();
            listaHechizos.add(new HechizoDeAgua(nombre, danho, heal, presion));
            break;
		case "Planta":
			System.out.print("Duración Stun: ");
            int stun = obtenerEntero();
            System.out.print("Cantidad Plantas: ");
            int cant = obtenerEntero();
            listaHechizos.add(new HechizoDePlanta(nombre, danho, stun, cant));
            break;
        default:
        	System.out.println("Tipo invalido");
        	return;
		}
		guardarDatos();
		System.out.println("Hechizo agregado");
		
	}
	private void modificarHechizo() {
		System.out.print("Nombre del hechizo a modificar: ");
        String nombre = s.nextLine().trim();
        Hechizo hechizo = buscarHechizoPorNombre(nombre);
        if (hechizo == null) {
            System.out.println("Hechizo no encontrado.");
            return;
        }
        System.out.print("Nuevo daño: ");
        int nuevoDanho = obtenerEntero();
        listaMagos.forEach(m -> {
            if (m.tieneHechizo(nombre)) {
                m.eliminarHechizo(nombre);
                Hechizo nuevo = crearHechizoModificado(hechizo, nuevoDanho);
                m.agregarHechizo(nuevo);
            }
        });
        listaHechizos.remove(hechizo);
        Hechizo nuevo = crearHechizoModificado(hechizo, nuevoDanho);
        listaHechizos.add(nuevo);
        guardarDatos();
        System.out.println("Hechizo modificado.");
    }

    private Hechizo crearHechizoModificado(Hechizo original, int nuevoDanho) {
        String tipo = original.getTipo();
        String nombre = original.getNombre();
        switch (tipo) {
            case "Fuego":
                HechizoDeFuego f = (HechizoDeFuego) original;
                return new HechizoDeFuego(nombre, nuevoDanho, f.duracionQuemadura);
            case "Tierra":
                HechizoDeTierra t = (HechizoDeTierra) original;
                return new HechizoDeTierra(nombre, nuevoDanho, t.mejoraDefensa);
            case "Planta":
                HechizoDePlanta p = (HechizoDePlanta) original;
                return new HechizoDePlanta(nombre, nuevoDanho, p.duracionStun, p.cantPlantas);
            case "Agua":
                HechizoDeAgua a = (HechizoDeAgua) original;
                return new HechizoDeAgua(nombre, nuevoDanho, a.cantidadHeal, a.presionAgua);
            default:
                return null;
        }
	}
	private void eliminarHechizo() {
		System.out.print("Nombre del hechizo a eliminar: ");
		String nombre = s.nextLine().trim();
		if (listaHechizos.removeIf(h -> h.getNombre().equals(nombre))) {
			listaMagos.forEach(m -> m.eliminarHechizo(nombre));
			guardarDatos();
			System.out.println("Hechizo eliminado.");
		} else {
			System.out.println("Hechizo no encontrado");
		}
	}
	
	public void menuAnalisis() {
		while (true) {
			System.out.println("\n=== MENU DE ANALISIS ===\n1. Top 10 Mejores Hechizos\n2. Top 3 Mejores Magos\n3. Mostrar todos los Hechizos\n4. Mostrar todos los Magos\n5. Mostrar Hechizos con Puntuación\n6. Mostrar Magos con Puntuación\n7. Volver");
			System.out.print("> ");
			int op = obtenerEntero();
			
			switch (op) {
            case 1 -> top10Hechizos();
            case 2 -> top3Magos();
            case 3 -> mostrarHechizos();
            case 4 -> mostrarMagos();
            case 5 -> mostrarHechizosConPuntaje();
            case 6 -> mostrarMagosConPuntaje();
            case 7 -> { return; }
            default -> System.out.println("Opción inválida.");
			}
		}
	}
	private void top10Hechizos() {
        listaHechizos.stream()
                .sorted((a, b) -> Double.compare(b.getPuntuacion(), a.getPuntuacion()))
                .limit(10)
                .forEach(h -> System.out.printf("%s -> %.2f\n", h.getNombre(), h.getPuntuacion()));
    }
	private void top3Magos() {
        listaMagos.stream()
                .sorted((a, b) -> Double.compare(b.calcularPuntajeTotal(), a.calcularPuntajeTotal()))
                .limit(3)
                .forEach(m -> System.out.printf("%s -> %.2f\n", m.getNombre(), m.calcularPuntajeTotal()));
    }
	private void mostrarHechizos() {
        listaHechizos.forEach(h -> System.out.println(h.getNombre()));
    }
	private void mostrarMagos() {
        listaMagos.forEach(m -> System.out.println(m.getNombre()));
    }
	private void mostrarHechizosConPuntaje() {
        listaHechizos.forEach(h -> System.out.printf("%s (%s): %.2f\n", h.getNombre(), h.getTipo(), h.getPuntuacion()));
    }
	private void mostrarMagosConPuntaje() {
        listaMagos.forEach(m -> System.out.printf("%s: %.2f\n", m.getNombre(), m.calcularPuntajeTotal()));
    }
	
	private Mago buscarMagoPorNombre(String nombre) {
        return listaMagos.stream()
                .filter(m -> m.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }
	private int obtenerEntero() {
        while (!s.hasNextInt()) {
            System.out.println("Ingrese un número válido.");
            s.next();
        }
        int valor = s.nextInt();
        s.nextLine();
        return valor;
    }
	
}
