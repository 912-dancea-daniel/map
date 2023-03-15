package Repository;

import Exceptions.MyException;
import models.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    PrgState getCrtPrg();

    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> newList);

    void logPrgStateExec(PrgState state) throws MyException, IOException;
}
