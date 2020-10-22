package src.com.project.state;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import src.com.project.AzamonInfo;

import java.util.ArrayList;
import java.util.List;

public class AzamonBoard implements SuccessorFunction, HeuristicFunction {
    private AzamonInfo azamonInfo;
    private int[] assignedOffer;
    private double[] actualWeight;
    private int ganancia = 0;

    public AzamonBoard(AzamonInfo aInfo) {
        this.azamonInfo = aInfo;
        this.assignedOffer = new int[aInfo.paquetes.size()];
        this.actualWeight = new double[aInfo.transporte.size()];
        int size = this.azamonInfo.paquetes.size();
        int prio = 0;
        for(int i = 0; i < size;++i){
            prio = this.azamonInfo.paquetes.get(i).getPrioridad();
            if(prio == 0)this.ganancia += 5;
            else if(prio == 1)this.ganancia += 3;
            else this.ganancia += 1.5;
        }

    }

    public AzamonState generateInicialState(/*SE PODRIA ESPECIFICAR AQUI CON UN ENUM*/) {
        //HACER GENERACION BASICA Y GENERACION INTELIGENTE
        assignPackagesToOffers(0);
        assignPackagesToOffers(1);
        assignPackagesToOffers(2);
        return null;
    }

    @Override
    public List getSuccessors(Object state) {
        updateState((AzamonState) state);
        List<Successor> list= new ArrayList<>();

        //GENERADORES DE OPERACIONES
        addMovableSuccessors(list);
        addSwappableSuccessors(list);
        //...
        return list;
    }


    @Override
    public double getHeuristicValue(Object state) {
        AzamonState oldState = updateState((AzamonState) state);
        //FUNCION HEURISTICA DE COSTE
        double cost=0.0D;
        for(int i=actualWeight.length-1;i>=0;--i) {
            int day = azamonInfo.transporte.get(i).getDias();
            cost+=actualWeight[i]*azamonInfo.transporte.get(i).getPrecio();
            if (day==3 || day==4) cost+=actualWeight[i]*0.25D;
            else if (day==5) cost+=actualWeight[i]*0.5D;
        }
        //HACER FUNCION HEURISTICA DE FELICIDAD

        //Calculo de la felicidad actual

        //if(!priorizeMoney){

            int size = azamonInfo.paquetes.size();
            double felicidad = 0, priori = 0, dias = 0;
            for(int i = 0;i < size;++i) {
                priori = priorityToDays(azamonInfo.paquetes.get(i).getPrioridad());
                dias = azamonInfo.transporte.get(assignedOffer[i]).getDias();
                felicidad += priori - dias;
            }
            //System.out.println(felicidad);
            updateState(oldState);
            if(felicidad > 0) return cost/felicidad;
            else return Double.MAX_VALUE;
        //}
        //updateState(oldState);
        //return cost;
    }
    //GETTERS

    int getAssignedOffer(int pack) {return assignedOffer[pack];}

    //FUNCIONES PRIVADAS

    private int priorityToDays(int priority) {
        if(priority == 0) return 1;
        if(priority == 1) return 3;
        return 5;
    }

    private void assignPackagesToOffers(int priority) {
        int packagePointer = 0;
        for (Paquete paquete : azamonInfo.paquetes) {
            if (paquete.getPrioridad()==priority) {
                int offerPointer = 0;
                for (Oferta oferta : azamonInfo.transporte) {

                    double maxWeight = oferta.getPesomax();
                    double sumWeight = actualWeight[offerPointer] + paquete.getPeso();

                    boolean satisfablePriority = isPrioritySatisfable(oferta, paquete);
                    if (sumWeight <= maxWeight && satisfablePriority) {
                        actualWeight[offerPointer] = sumWeight;
                        assignedOffer[packagePointer] = offerPointer;
                        break;
                    } ++offerPointer;

                }
            } ++packagePointer;
        }
    }

    private boolean isPrioritySatisfable(Oferta oferta, Paquete paquete) {
        int difference = oferta.getDias() - paquete.getPrioridad()*2;
        if (difference<=1 ) return true;
        /*
        int difference = oferta.getDias() - paquete.getPrioridad()*2;
        if (difference==0 || difference==1) return true;
        */
        return false;
    }

    private AzamonState updateState(AzamonState azamonState) {
        if (azamonState==null) return null;
        return azamonState.updateBoard(this);
    }

    private void addMovableSuccessors(List<Successor> list) {
        int packagesSize = azamonInfo.paquetes.size();
        int offersSize = azamonInfo.transporte.size();

        for(int pack = 0; pack<packagesSize; ++pack) {
            for(int offer = 0; offer<offersSize; ++offer) {
                if (offer != assignedOffer[pack]) {
                    double packageWeight = azamonInfo.paquetes.get(pack).getPeso();
                    double offerMaxWeight = azamonInfo.transporte.get(offer).getPesomax();
                    boolean satisfablePriority =
                            isPrioritySatisfable(azamonInfo.transporte.get(offer), azamonInfo.paquetes.get(pack));
                    if (packageWeight+actualWeight[offer]<=offerMaxWeight && satisfablePriority) {
                        AzamonState newBoard = new AzamonMoveState(pack,offer);
                        list.add(new Successor("m"+ pack + "." + offer,newBoard));
                    }
                }
            }
        }
    }

    private void addSwappableSuccessors(List<Successor> list) {
        //HACER FUNCION
    }

    //FUNCIONES FRIEND -Uso exclusivo para la interface AzamonState-
    public void __move__(int pack, int offer) {
        double packageWeight = azamonInfo.paquetes.get(pack).getPeso();
        actualWeight[assignedOffer[pack]]-=packageWeight;
        actualWeight[offer]+=packageWeight;
        assignedOffer[pack]=offer;
    }
}
