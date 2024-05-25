package com.vao.agenda;

import java.util.*;
import java.text.SimpleDateFormat;

public class SistemaReservas {

    static class Tratamiento {
        private String nombre;
        private int duracion; // duración en minutos

        public Tratamiento(String nombre, int duracion) {
            this.nombre = nombre;
            this.duracion = duracion;
        }

        public String getNombre() {
            return nombre;
        }

        public int getDuracion() {
            return duracion;
        }
    }

    static class Local {
        private String nombre;
        private List<Integer> diasDisponibles; // Lunes=2, Martes=3, ..., Domingo=1

        public Local(String nombre, List<Integer> diasDisponibles) {
            this.nombre = nombre;
            this.diasDisponibles = diasDisponibles;
        }

        public String getNombre() {
            return nombre;
        }

        public List<Integer> getDiasDisponibles() {
            return diasDisponibles;
        }
    }

    public static void main(String[] args) {
        // Definir tratamientos
        Tratamiento consulta = new Tratamiento("consulta", 15);
        Tratamiento extraccion = new Tratamiento("extraccion", 60);
        Tratamiento ortodoncia = new Tratamiento("ortodoncia", 30);

        // Crear lista de tratamientos
        List<Tratamiento> tratamientos = Arrays.asList(consulta, extraccion, ortodoncia);

        // Definir locales
        Local local1 = new Local("CENTRO", Arrays.asList(Calendar.TUESDAY, Calendar.FRIDAY));
        Local local2 = new Local("BARRIO", Arrays.asList(Calendar.WEDNESDAY, Calendar.FRIDAY));

        // Crear lista de locales
        List<Local> locales = Arrays.asList(local1, local2);

        // Simular elección del usuario
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione un consultorio:");
        for (int i = 0; i < locales.size(); i++) {
            System.out.println((i + 1) + ". " + locales.get(i).getNombre());
        }
        int localElegidoIndex = scanner.nextInt() - 1;
        Local localElegido = locales.get(localElegidoIndex);

        System.out.println("Seleccione un tratamiento:");
        for (int i = 0; i < tratamientos.size(); i++) {
            System.out.println((i + 1) + ". " + tratamientos.get(i).getNombre());
        }
        int tratamientoElegidoIndex = scanner.nextInt() - 1;
        Tratamiento tratamientoElegido = tratamientos.get(tratamientoElegidoIndex);

        // Mostrar horarios disponibles
        mostrarHorariosDisponibles(localElegido, tratamientoElegido);
    }

    public static void mostrarHorariosDisponibles(Local local, Tratamiento tratamiento) {
        System.out.println("Horarios disponibles para " + local.getNombre() + " y tratamiento de " + tratamiento.getNombre() + ":");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Empezar desde mañana

        Calendar fin = Calendar.getInstance();
        fin.add(Calendar.MONTH, 3); // Hasta tres meses desde hoy

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");

        while (calendar.before(fin)) {
            if (local.getDiasDisponibles().contains(calendar.get(Calendar.DAY_OF_WEEK))) {
                System.out.println("Fecha: " + sdf.format(calendar.getTime()));
                mostrarFranjaHoraria(tratamiento);
                System.out.println();
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    public static void mostrarFranjaHoraria(Tratamiento tratamiento) {
        Calendar inicioDia = Calendar.getInstance();
        inicioDia.set(Calendar.HOUR_OF_DAY, 9);
        inicioDia.set(Calendar.MINUTE, 0);
        inicioDia.set(Calendar.SECOND, 0);
        inicioDia.set(Calendar.MILLISECOND, 0);

        Calendar finDia = Calendar.getInstance();
        finDia.set(Calendar.HOUR_OF_DAY, 19);
        finDia.set(Calendar.MINUTE, 0);
        finDia.set(Calendar.SECOND, 0);
        finDia.set(Calendar.MILLISECOND, 0);

        while (inicioDia.before(finDia)) {
            String horaInicio = String.format("%02d:%02d", inicioDia.get(Calendar.HOUR_OF_DAY), inicioDia.get(Calendar.MINUTE));
            inicioDia.add(Calendar.MINUTE, tratamiento.getDuracion());
            if (inicioDia.before(finDia)) {
                String horaFin = String.format("%02d:%02d", inicioDia.get(Calendar.HOUR_OF_DAY), inicioDia.get(Calendar.MINUTE));
                System.out.println(horaInicio + " - " + horaFin);
            }
        }
    }
}
