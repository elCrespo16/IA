package src.com.project;

import src.com.project.classes.AzamonReader;
import src.com.project.classes.Experimentos;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AzamonMain {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Seleccione una operacion [option|none]: ");
        selector(reader.readLine());

    }

    private static void selector(String option) throws Exception {
        switch (option) {
            case (String) "primero":
                Experimentos.primero();
                break;
            case (String) "segundo":
                Experimentos.segundo();
                break;
            case (String) "tercero":
                Experimentos.tercero();
                break;
            case (String) "cuarto":
                Experimentos.cuarto_quinto();
                break;
            case (String) "quinto":
                Experimentos.cuarto_quinto();
                break;
            case (String) "sexto":
                Experimentos.sexto();
                break;
            case (String) "septimo":
                Experimentos.septimo();
                break;
            case (String) "octavo":
                Experimentos.octavo();
                break;
            case (String) "noveno":
                Experimentos.noveno();
                break;
            case (String) "comparativa":
                Experimentos.comparativa();
                break;
            default:
                AzamonReader.execute();
                break;
        }
    }

}