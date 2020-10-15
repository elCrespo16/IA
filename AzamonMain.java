
import IA.Azamon.Paquetes;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class AzamonMain {
    public static void main(String[] args) throws IOException {
        //Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Primer parametro es el tipo de algoritmo a usar,
        // Hill Climbing o Simulated Annealing
        System.out.println("Selecciona algoritmo: HillC o SimulatedAnn");
        String algorithm = reader.readLine();
        System.out.println("Numero de paquetes:");
        int npaquetes = Integer.parseInt(reader.readLine());
        System.out.println("Numero de semilla o rand:");
        String sd = reader.readLine();
        int seed = 0;
        if(sd.equals("rand")){
            Random myRandom=new Random();
            seed = myRandom.nextInt(101);
        }
        else {
            seed = Integer.parseInt(sd);
        }
        //Datos Basicos
        Paquetes paquetes = new Paquetes(npaquetes,seed);

        if(algorithm.equals("HillC")){
            System.out.println("Has elegido Hill Climbing");
        }
        else {
            float par1, par2, par3, par4;
            System.out.println("Has elegido Simulated Annealing");
            System.out.println("4 parametros para el Simulated Annealing");
            par1 = Float.parseFloat(reader.readLine());
            par2 = Float.parseFloat(reader.readLine());
            par3 = Float.parseFloat(reader.readLine());
            par4 = Float.parseFloat(reader.readLine());
            AzamonSimulatedAnnealingSearch(par1,par2,par3,par4);
        }

    }
    private static void AzamonHillClimbingSearch(/*Clase con la solucion inicial*/) {
        System.out.println("\nAzamon HillClimbing  -->");
        /*try {
            Problem problem =  new Problem(*//*Clase con la solucion inicial*//*,new *//*Clase de las sucesiones*//*, new *//*Clase del goal*//*,new *//*Clase del heuristico*//*);
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private static void AzamonSimulatedAnnealingSearch(/*Clase con la solucion inicial*/float par1,float par2,float par3,float par4) {
        System.out.println("\nAzamon Simulated Annealing  -->");
        /*try {
            Problem problem =  new Problem(*//*Clase con la solucion inicial*//*,new *//*Clase de las sucesiones*//*, new *//*Clase del goal*//*,new *//*Clase del heuristico*//*);
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(par1,par2,par3,par4);
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}