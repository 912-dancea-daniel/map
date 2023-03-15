package Repository;

import Exceptions.MyException;
import models.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{

    PrgState prgState;
    List<PrgState> arr = new ArrayList<>();
    String logFilePath;

    PrintWriter logFile;
    int counter = -1;

    public Repo(PrgState state, String logFilePath) {
        addPrgState(state);
        this.logFilePath = logFilePath;

        try {
            logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.write("");
            logFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    void addPrgState(PrgState state){
        arr.add(state);
        counter++;
    }


    public Repo(PrgState prgState) {
        addPrgState(prgState);
    }



    @Override
    public List<PrgState> getPrgList() {
        return arr;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        arr = newList;
    }

    @Override
    public PrgState getCrtPrg() {
        return arr.get(counter);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException, IOException {
        logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
//        logFile.write("ExeStack\n");
//        logFile.write(state.getStk().toString() + "\n");
//        logFile.write("\nSymTable\n");
//        logFile.write(state.getSymTable().toString() + "\n");
//        logFile.write("\nOut\n");
//        logFile.write(state.getOut().toString() + "\n");
//        logFile.write("\nFileTable\n");
//        logFile.write(state.getFileTable().toString() + "\n\n");
        logFile.write(state.toString());
        logFile.flush();
        logFile.close();
    }
}
