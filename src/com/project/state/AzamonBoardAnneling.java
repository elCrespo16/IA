package src.com.project.state;

import aima.search.framework.Successor;
import src.com.project.AzamonInfo;

import java.util.ArrayList;
import java.util.List;

public class AzamonBoardAnneling extends AzamonBoard{

    AzamonState actualState;

    public AzamonBoardAnneling(AzamonInfo aInfo) {
        super(aInfo);
        actualState=null;
    }

    @Override
    public List getSuccessors(Object state) {
        if (state!=actualState) updateState((AzamonState) state);

        List<Successor> list= new ArrayList<>();

        //GENERADORES DE OPERACIONES
        int flags=azamonInfo.operadoresFlag;
        if ((flags & 1)==1) addMovableSuccessors(list);
        if (((flags >> 1) & 1) == 1) addSwappableSuccessors(list);
        //...
        return list;
    }

    @Override
    public double getCost(Object state) {
        AzamonState oldState=null;
        if (state!=actualState) oldState = updateState((AzamonState) state);
        double cost=0.0D;
        for(int i=actualWeight.length-1;i>=0;--i) {
            int day = azamonInfo.transporte.get(i).getDias();
            cost+=actualWeight[i]*azamonInfo.transporte.get(i).getPrecio();
            if (day==3 || day==4) cost+=actualWeight[i]*0.25D;
            else if (day==5) cost+=actualWeight[i]*0.5D;
        }
        if (oldState!=actualState) updateState(oldState);
        return cost;
    }

    @Override
    public double getFelicidad(Object state) {
        AzamonState oldState=null;
        if (state!=actualState) oldState = updateState((AzamonState) state);
        int size = azamonInfo.paquetes.size();
        double felicidad = 0, priori = 0, dias = 0;
        for(int i = 0;i < size;++i) {
            priori = priorityToDays(azamonInfo.paquetes.get(i).getPrioridad());
            dias = azamonInfo.transporte.get(assignedOffer[i]).getDias();
            felicidad += priori - dias;
        }
        if (oldState!=null) updateState(oldState);
        return felicidad;
    }


}
