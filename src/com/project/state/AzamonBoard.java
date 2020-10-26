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
    }

    public AzamonState generateInicialState(/*SE PODRIA ESPECIFICAR AQUI CON UN ENUM*/) {
        //HACER GENERACION BASICA Y GENERACION INTELIGENTE
        //if(generacionInteligente) {
            assignPackagesToOffersGreedy(0);
            assignPackagesToOffersGreedy(1);
            assignPackagesToOffersGreedy(2);
        //}
        /*else {
            assignPackagesToOffers(0);
            assignPackagesToOffers(1);
            assignPackagesToOffers(2);
        }*/
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
        double cost = getCost(null);
        if (azamonInfo.heuristico==HeuristicEnum.COSTE) {updateState(oldState); return cost;}
        double felicidad = getFelicidad(null);

        updateState(oldState);
        if(felicidad > 0) return cost/felicidad;
        else return Double.MAX_VALUE;
    }
    //GETTERS

    int getAssignedOffer(int pack) {return assignedOffer[pack];}

    public double getCost(Object state) {
        AzamonState oldState = updateState((AzamonState) state);
        double cost=0.0D;
        for(int i=actualWeight.length-1;i>=0;--i) {
            int day = azamonInfo.transporte.get(i).getDias();
            cost+=actualWeight[i]*azamonInfo.transporte.get(i).getPrecio();
            if (day==3 || day==4) cost+=actualWeight[i]*0.25D;
            else if (day==5) cost+=actualWeight[i]*0.5D;
        }
        updateState(oldState);
        return cost;
    }

    public double getFelicidad(Object state) {
        AzamonState oldState = updateState((AzamonState) state);
        int size = azamonInfo.paquetes.size();
        double felicidad = 0, priori = 0, dias = 0;
        for(int i = 0;i < size;++i) {
            priori = priorityToDays(azamonInfo.paquetes.get(i).getPrioridad());
            dias = azamonInfo.transporte.get(assignedOffer[i]).getDias();
            felicidad += priori - dias;
        }
        updateState(oldState);
        return felicidad;
    }
    //FUNCIONES PRIVADAS

    private int priorityToDays(int priority) {
        if(priority == 0) return 1;
        if(priority == 1) return 3;
        return 5;
    }

    private void assignPackagesToOffersGreedy(int priority) {
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
    private void assignPackagesToOffers(int priority) {
        int packagePointer = 0;
        for (Paquete paquete : azamonInfo.paquetes) {
            if (paquete.getPrioridad()==priority) {
                int offerPointer = 0;
                for (Oferta oferta : azamonInfo.transporte) {

                    double maxWeight = oferta.getPesomax();
                    double sumWeight = actualWeight[offerPointer] + paquete.getPeso();

                    boolean satisfablePriority = isPriorityEqual(oferta, paquete);
                    if (sumWeight <= maxWeight && satisfablePriority) {
                        actualWeight[offerPointer] = sumWeight;
                        assignedOffer[packagePointer] = offerPointer;
                        break;
                    } ++offerPointer;

                }
            } ++packagePointer;
        }
    }

    private boolean isPriorityEqual (Oferta oferta, Paquete paquete) {
        int diasOferta = oferta.getDias();
        int diasPaquete = priorityToDays(paquete.getPrioridad());
        return (diasOferta == diasPaquete) || (diasPaquete == 3 && diasOferta == 2) || (diasPaquete == 5 && diasOferta == 4);
    }
    private boolean isPrioritySatisfable(Oferta oferta, Paquete paquete) {
        int difference = oferta.getDias() - paquete.getPrioridad()*2;
        return difference <= 1;
        /*
        int difference = oferta.getDias() - paquete.getPrioridad()*2;
        if (difference==0 || difference==1) return true;
        */
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
        int packagesSize = azamonInfo.paquetes.size();
        for(int i=0;i<packagesSize-1;++i) {
            double packweight1 = azamonInfo.paquetes.get(i).getPeso();
            double offerMaxWeight1 = azamonInfo.transporte.get(assignedOffer[i]).getPesomax();
            double actualweight1 = actualWeight[assignedOffer[i]];
            for(int j=i+1;j<packagesSize;++j) {
                if(assignedOffer[i]!=assignedOffer[j]) {
                    double packweight2 = azamonInfo.paquetes.get(i).getPeso();
                    double offerMaxWeight2 = azamonInfo.transporte.get(assignedOffer[j]).getPesomax();
                    double actualweight2 = actualWeight[assignedOffer[j]];
                    boolean satisfablePriority1 =
                            isPrioritySatisfable(azamonInfo.transporte.get(assignedOffer[j]), azamonInfo.paquetes.get(i));
                    boolean satisfablePriority2 =
                            isPrioritySatisfable(azamonInfo.transporte.get(assignedOffer[i]), azamonInfo.paquetes.get(j));
                    if (actualweight1-packweight1+packweight2<=offerMaxWeight1 && actualweight2-packweight2+packweight1<=offerMaxWeight2
                        && satisfablePriority1 && satisfablePriority2) {
                        AzamonState newBoard = new AzamonSwapState(i, j);
                        list.add(new Successor("s" + i + "." + j, newBoard));
                    }
                }
            }
        }
    }

    //FUNCIONES FRIEND -Uso exclusivo para la interface AzamonState-
    public void __move__(int pack, int offer) {
        double packageWeight = azamonInfo.paquetes.get(pack).getPeso();
        actualWeight[assignedOffer[pack]]-=packageWeight;
        actualWeight[offer]+=packageWeight;
        assignedOffer[pack]=offer;
    }
}
