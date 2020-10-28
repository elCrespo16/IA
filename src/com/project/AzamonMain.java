package src.com.project;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import src.com.project.state.AzamonBoard;
import src.com.project.state.AzamonBoardAnneling;
import src.com.project.state.AzamonState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AzamonMain {
    public static void main(String[] args) throws Exception {

        //Enter data using BufferReader
        Experimentos.tercero();
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //System.out.println("Selecciona algoritmo [HillC|SimAnn]:");
        //String algorithm = reader.readLine();
        //if(algorithm.equals("HillC")) AzamonHillClimbingSearch();
        //else if (algorithm.equals("SimAnn")) AzamonSimulatedAnnealingSearch();


    }
    private static void AzamonHillClimbingSearch() throws IOException {
        AzamonInfo aInfo = AzamonInfo.reader();
        AzamonBoard aBoard = new AzamonBoard(aInfo);
        aBoard.generateInicialState(0);

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
            List list = agent.getActions();
            for(Object str : list) System.out.println(str);
            System.out.println("Coste Inicial: "+costeSinOpti);
            System.out.println("Felicidad Inicial: "+felicidadSinOpti);
            System.out.println("Coste Final: "+aBoard.getCost(null));
            System.out.println("Felicidad Final: "+aBoard.getFelicidad(null));
            System.out.println(c2.getTimeInMillis()-c1.getTimeInMillis()+" ms");
            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void AzamonSimulatedAnnealingSearch() throws Exception {

        AzamonInfo aInfo = AzamonInfo.reader();
        AzamonBoardAnneling aBoard = new AzamonBoardAnneling(aInfo);
        aBoard.generateInicialState(0);
        double costeSinOpti =   aBoard.getCost(null);
        double felicidadSinOpti = aBoard.getFelicidad(null);

        System.out.println("\nAzamon Simulated Annealing  -->");
        Problem problem =  new Problem(null,aBoard, null,aBoard);
        SimulatedAnnealingSearch search = simulatedReader();
        Date d1,d2; Calendar c1,c2;
        d1=new Date();
        SearchAgent agent = new SearchAgent(problem,search);
        d2=new Date();
        c1=Calendar.getInstance(); c2=Calendar.getInstance();
        c1.setTime(d1); c2.setTime(d2);
        AzamonState finalState = (AzamonState) search.getGoalState();
        List list = agent.getActions();
        for(Object str : list) System.out.println(str);
        System.out.println("Coste Final: "+aBoard.getCost(finalState));
        System.out.println("Felicidad Final: "+aBoard.getFelicidad(finalState));
        System.out.println(c2.getTimeInMillis()-c1.getTimeInMillis()+" ms");
    }

    private static SimulatedAnnealingSearch simulatedReader() throws IOException {
        int steps, stiter, k;
        double lamb;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("4 parametros para el Simulated Annealing");
        steps = reader.read();
        stiter = reader.read();
        k = reader.read();
        lamb = Double.parseDouble(reader.readLine());
        return new SimulatedAnnealingSearch(steps,stiter,k,lamb);
    }
}