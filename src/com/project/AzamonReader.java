package src.com.project;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import src.com.project.state.AzamonBoard;
import src.com.project.state.AzamonBoardAnneling;
import src.com.project.state.AzamonState;
import src.com.project.state.GenerateEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class AzamonReader {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static void azamonHillClimbing() throws Exception
    {
        //VARIABLES DECLARATION
        String option;
        Date d1,d2; Calendar c1,c2;
        double inicialValues[]={.0D,.0D,.0D,.0D,.0D};
        double finalValues[]={.0D,.0D,.0D,.0D,.0D};

        //VARIABLES STANDARD

        GenerateEnum inicialState = GenerateEnum.BASIC;
        c1=Calendar.getInstance(); c2=Calendar.getInstance();

        //READERS
        AzamonInfo aInfo = AzamonInfo.reader();
        AzamonBoard aBoard = new AzamonBoard(aInfo);
        System.out.print("Estat inicial [basic|priority]: "); option=reader.readLine();
        if (option.equals("basic")) inicialState = GenerateEnum.BASIC;
        else if (option.equals("priority")) inicialState = GenerateEnum.PRIORITY_ORDERED;
        aBoard.generateInicialState(inicialState);

        //FIRST VALUES CATCH
        fill(inicialValues,aBoard,null);

        //TIMER CATCH
        Problem problem =  new Problem(null,aBoard, null,aBoard);
        Search search =  new HillClimbingSearch();
        d1=new Date(); SearchAgent agent = new SearchAgent(problem,search); d2=new Date();
        c1.setTime(d1); c2.setTime(d2);

        //LAST VALUES CATCH
        fill(finalValues,aBoard,null);

        print(c2.getTimeInMillis()-c1.getTimeInMillis(),inicialValues,finalValues);
    }

    private static void azamonSimulatedAnnealing() throws Exception {

        //VARIABLES DECLARATION
        String option;
        Date d1,d2; Calendar c1,c2;
        double inicialValues[]={.0D,.0D,.0D,.0D,.0D};
        double finalValues[]={.0D,.0D,.0D,.0D,.0D};
        //VARIABLES STANDARD

        GenerateEnum inicialState = GenerateEnum.BASIC;
        c1=Calendar.getInstance(); c2=Calendar.getInstance();

        //READERS
        AzamonInfo aInfo = AzamonInfo.reader();
        AzamonBoardAnneling aBoard = new AzamonBoardAnneling(aInfo);
        System.out.print("Estat inicial [basic|priority]: "); option=reader.readLine();
        if (option.equals("basic")) inicialState = GenerateEnum.BASIC;
        else if (option.equals("priority")) inicialState = GenerateEnum.PRIORITY_ORDERED;
        aBoard.generateInicialState(inicialState);

        //FIRST VALUES CATCH
        fill(inicialValues,aBoard,null);

        //TIMER CATCH
        Problem problem =  new Problem(null,aBoard, null,aBoard);
        Search search =  readAnnealingSearch();
        d1=new Date(); SearchAgent agent = new SearchAgent(problem,search); d2=new Date();
        c1.setTime(d1); c2.setTime(d2);

        //LAST VALUES CATCH
        fill(finalValues,aBoard, (AzamonState) search.getGoalState());

        print(c2.getTimeInMillis()-c1.getTimeInMillis(),inicialValues,finalValues);
    }

    private static Search readAnnealingSearch() throws IOException {
        int steps, stiter, k;
        double lamb;
        System.out.print("steps: "); steps=Integer.parseInt(reader.readLine());
        System.out.print("stiter: "); stiter=Integer.parseInt(reader.readLine());
        System.out.print("k: "); k=Integer.parseInt(reader.readLine());
        System.out.print("lamb: "); lamb=Double.parseDouble(reader.readLine());
        return new SimulatedAnnealingSearch(steps,stiter,k,lamb);
    }

    private static void fill(double[] values, AzamonBoard azamonBoard ,AzamonState azamonState) {
        values[0] = azamonBoard.getHeuristicValue(azamonState);
        values[1] = azamonBoard.getFelicidad(azamonState);
        values[2] = azamonBoard.getCost(azamonState);
        values[3] = azamonBoard.getTransportCost(azamonState);
        values[4] = azamonBoard.getAlmacenCost(azamonState);
    }

    private static void print(long ms,double[] inicialD, double[] finalD) {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println("El algoritmo tardó "+ms+" ms en ejecutarse.");
        System.out.println("State"+(char)9+"Heuristico"+(char)9+"Felicidad"+(char)9+"Coste"+(char)9+"Transporte"+(char)9+"Almacenamiento");
        System.out.print("Inicial");
        for(int i=0;i<5;++i) System.out.print(" "+(char)9+df.format(inicialD[i]));
        System.out.println();
        System.out.print("Final");
        for(int i=0;i<5;++i) System.out.print(" "+(char)9+df.format(finalD[i]));
        System.out.println();
    }

    public static void execute() throws Exception
    {
        System.out.println("-- OPCION PERSONALIZADA --");
        System.out.print("Escoge algoritmo [hill|annealing]: "); String algoritmo = reader.readLine();
        if (algoritmo.equals("hill")) azamonHillClimbing();
        else if (algoritmo.equals("annealing")) azamonSimulatedAnnealing();
    }
}
