package src.com.project;

import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class AzamonInfo {
    public final Paquetes paquetes;
    public final Transporte transporte;

    public AzamonInfo(Paquetes paquetes, Transporte transporte) {
        this.paquetes = paquetes;
        this.transporte = transporte;
    }

    public static AzamonInfo reader() throws IOException {
        int npaquetes; int seed; double proportion;
        BufferedReader reader; String seedStr; Paquetes paquetes; Transporte transporte;

        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Numero de la semilla o [random]:"); seedStr = reader.readLine();
        System.out.println("Numero de paquetes:"); npaquetes = Integer.parseInt(reader.readLine());
        System.out.println("Proporcion:"); proportion = Double.parseDouble(reader.readLine());

        if(seedStr.equals("random")) seed=(new Random()).nextInt(101);
        else seed = Integer.parseInt(seedStr);

        paquetes = new Paquetes(npaquetes,seed);
        transporte = new Transporte(paquetes,proportion,seed);

        return new AzamonInfo(paquetes,transporte);
    }

}
