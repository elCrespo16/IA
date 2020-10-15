import IA.Azamon.AzamonBoard;
import IA.Azamon.AzamonInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AzamonMain {
    public static void main(String[] args) throws IOException {
        //Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Primer parametro es el tipo de algoritmo a usar,
        // Hill Climbing o Simulated Annealing


        //Fill Information
        AzamonInfo aInfo = new AzamonInfo();

        AzamonBoard aBoard = new AzamonBoard(aInfo);
        aBoard.generateInicialState();
        aBoard.getNextStates();
        boolean correct=true;
        do {
            System.out.println("Selecciona algoritmo: HillC o SimulatedAnn");
            String algorithm = reader.readLine();

            if(algorithm.equals("HillC")) {correct=true; AzamonHillClimbingSearch();}
            else if (algorithm.equals("SimulatedAnn")) {correct=true; AzamonSimulatedAnnealingSearch();}
            else {
                correct = false;
                System.out.println("Opcion invalida.");
            }

        } while (! correct);

    }
    private static void AzamonHillClimbingSearch(/*Clase con la solucion inicial*/) {
        System.out.println("Has elegido Hill Climbing");
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

    private static void AzamonSimulatedAnnealingSearch() throws IOException {
        float par1, par2, par3, par4;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Has elegido Simulated Annealing");
        System.out.println("4 parametros para el Simulated Annealing");
        par1 = Float.parseFloat(reader.readLine());
        par2 = Float.parseFloat(reader.readLine());
        par3 = Float.parseFloat(reader.readLine());
        par4 = Float.parseFloat(reader.readLine());
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