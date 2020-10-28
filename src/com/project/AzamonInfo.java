package src.com.project;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import src.com.project.state.HeuristicEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class AzamonInfo {
    public final Paquetes paquetes;
    public final Transporte transporte;
    public final HeuristicEnum heuristico;
    public final double ganancia;
    public final int operadoresFlag;

    public AzamonInfo(Paquetes paquetes, Transporte transporte, HeuristicEnum heuristico,int operadoresFlag) {
        this.paquetes = paquetes;
        this.transporte = transporte;
        this.heuristico = heuristico;
        double ganancia=0;
        int size = paquetes.size();
        int prio = 0;
        for(int i = 0; i < size;++i){
            prio = paquetes.get(i).getPrioridad();
            if(prio == 0)ganancia += 5;
            else if(prio == 1)ganancia += 3;
            else ganancia += 1.5;
        }
        this.ganancia=ganancia;
        this.operadoresFlag=operadoresFlag;
    }

    public static AzamonInfo reader() throws IOException {
        int npaquetes; int seed; double proportion;
        BufferedReader reader; String seedStr,heuristicoStr; Paquetes paquetes; Transporte transporte;
        HeuristicEnum heuristico = HeuristicEnum.COSTE;
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Numero de la semilla o [random]:"); seedStr = reader.readLine();
        System.out.println("Numero de paquetes:"); npaquetes = Integer.parseInt(reader.readLine());
        System.out.println("Proporcion:"); proportion = Double.parseDouble(reader.readLine());
        boolean consulta=false;
        do {
            System.out.println("Heuristico [coste|felicidad]:");
            heuristicoStr = reader.readLine();
            if (heuristicoStr.equals("coste")) {heuristico = HeuristicEnum.COSTE;consulta=false;}
            else if (heuristicoStr.equals("felicidad")) {heuristico = HeuristicEnum.FELICIDAD;consulta=false;}
            else consulta=true;
        } while (consulta);

        if(seedStr.equals("random")) seed=(new Random()).nextInt(101);
        else seed = Integer.parseInt(seedStr);

        paquetes = new Paquetes(npaquetes,seed);
        transporte = new Transporte(paquetes,proportion,seed);


        return new AzamonInfo(paquetes,transporte, heuristico, 0x011);
    }

}
