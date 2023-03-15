package Controller;

import Exceptions.MyException;
import Repository.IRepo;
import Repository.Repo;
import javafx.util.Pair;
import models.ADT.MyDictionary;
import models.ADT.MyIList;
import models.PrgState;
import models.Values.IntValue;
import models.Values.RefValue;
import models.Values.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepo repo;

    ExecutorService executor;

    public Controller(PrgState prgState) {
        this.repo = new Repo(prgState);
    }
    public Controller(IRepo r) {
        this.repo = r;
    }

    void displayState(PrgState state){
        System.out.println("\n" + state);
        System.out.println("");
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer,Value>
            heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
    List<Value> getSymtablesValues(){
        return repo.getPrgList().stream()
                .flatMap(x -> x.getSymTable().getValues().stream())
                .collect(Collectors.toList());
    }


    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
                displayState(prg);
            } catch (MyException | IOException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(()-> {return p.oneStep();}))
                .collect(Collectors.toList());

        try{
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException  e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(p -> p!=null)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);
        }catch (Exception ignore){}


        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
                displayState(prg);
            } catch (MyException | IOException e) {
                System.out.println(e.getMessage());
            }
        });


        repo.setPrgList(prgList);

    }

    public List<PrgState> getPrgList(){
        return repo.getPrgList();
    }

    public void setUpExecutor(){
        executor = Executors.newFixedThreadPool(2);
    }

    public void setUpOneStep(){
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if (prgList.size() > 0){
            List<Value> tmp = getSymtablesValues();
            System.out.println(tmp);
            prgList.get(0).getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(getSymtablesValues()), getAddrFromHeap(prgList.get(0).getHeap().getValues()), prgList.get(0).getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        else {
            executor.shutdownNow();

            repo.setPrgList(prgList);
        }
    }

    public List<String> getIds(){
        List<String> tmpList = new ArrayList<>();
        repo.getPrgList().forEach(prgState ->  tmpList.add(String.valueOf(prgState.getId())));

        return tmpList;
    }

    public List<String> getOutItems(){
        List<String> tmpList = new ArrayList<>();

        MyIList<Value> tmp = repo.getCrtPrg().getOut();
        for (int i = 0; i<tmp.size(); i++){
            tmpList.add(tmp.get(i).toString());
        }

        return tmpList;
    }

    public List<String> getExeStackItem(int currentId){
        List<String> tmpList = new ArrayList<>();

        PrgState[] tmp = new PrgState[1];
        repo.getPrgList().forEach(prgState -> {
            if (prgState.getId() == currentId) tmp[0] = prgState;
        });


        for (int i = tmp[0].getStk().getLen() - 1; i >= 0; i--){
            tmpList.add(tmp[0].getStk().get(i).toString());
        }


        return tmpList;
    }

    public List<String> getFileTableValues(){
        List<String> tmpList = new ArrayList<>();

        String k = repo.getCrtPrg().getFileTable().getValues().toString();

        if (!k.equals("[]"))
            tmpList.add(k);

        return tmpList;
    }

    public MyDictionary<String, Value> getSymTable(int index){
        PrgState[] tmp = new PrgState[1];

        repo.getPrgList().forEach(prgState -> {
            if (prgState.getId() == index) tmp[0] = prgState;
        });

        return tmp[0].getSymTable().deepCopy();
    }

    public Map<Integer, Value> getHeapTable(int index){
        PrgState[] tmp = new PrgState[1];

        repo.getPrgList().forEach(prgState -> {
            if (prgState.getId() == index) tmp[0] = prgState;
        });

        return tmp[0].getHeap().getContent();
    }

    public Map<Integer, Integer> getLatchTable(){
        return repo.getCrtPrg().getLatchTable().getContent();
    }

    public void allStep(){
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0){
            List<Value> tmp = getSymtablesValues();
            System.out.println(tmp);
            prgList.get(0).getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(getSymtablesValues()), getAddrFromHeap(prgList.get(0).getHeap().getValues()), prgList.get(0).getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdownNow();

        repo.setPrgList(prgList);
    }
//               prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getValues()),
//                      getAddrFromHeap(prg.getHeap().getValues()), prg.getHeap().getContent()));


}
