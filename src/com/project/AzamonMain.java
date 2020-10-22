package src.com.project;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import src.com.project.state.AzamonBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

public class AzamonMain {
    public static void main(String[] args) throws IOException {

        //Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Primer parametro es el tipo de algoritmo a usar,
        // Hill Climbing o Simulated Annealing


        //Fill Information
        AzamonInfo aInfo = AzamonInfo.reader();

        AzamonBoard aBoard = new AzamonBoard(aInfo);
        aBoard.generateInicialState();
        boolean correct=true;
        do {
            System.out.println("Selecciona algoritmo: HillC o SimulatedAnn");
            String algorithm = reader.readLine();

            if(algorithm.equals("HillC")) {correct=true; AzamonHillClimbingSearch(aBoard);}
            else if (algorithm.equals("SimulatedAnn")) {correct=true; AzamonSimulatedAnnealingSearch();}
            else {
                correct = false;
                System.out.println("Opcion invalida.");
            }

        } while (! correct);

    }
    private static void AzamonHillClimbingSearch(AzamonBoard aBoard) {
        System.out.println("Has elegido Hill Climbing");
        System.out.println("\nAzamon HillClimbing  -->");
        double heuristicoSinOpti = aBoard.getHeuristicValue(null);
        double costeSinOpti =   aBoard.getCost(null);
        double felicidadSinOpti = aBoard.getFelicidad(null);
        try {
            Problem problem =  new Problem(null,aBoard, null,aBoard);
            Search search =  new HillClimbingSearch();
            Date d1,d2; Calendar c1,c2;
            d1=new Date();
            SearchAgent agent = new SearchAgent(problem,search);
            d2=new Date();
            c1=Calendar.getInstance(); c2=Calendar.getInstance();
            c1.setTime(d1); c2.setTime(d2);
            //System.out.println("Acabao: ");
            //List list = agent.getActions();
            //for(Object str : list) System.out.println(str);
            //System.out.println("H Inicial: " + heuristicoSinOpti);
            //System.out.println("H Final: "+aBoard.getHeuristicValue(null));
            //System.out.println("C Inicial: "+costeSinOpti);
            System.out.println("Coste Final: "+aBoard.getCost(null));
            //System.out.println("F Inicial: "+felicidadSinOpti);
            //System.out.println("F Final: "+aBoard.getFelicidad(null));
            System.out.println(c2.getTimeInMillis()-c1.getTimeInMillis()+" ms");
            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
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