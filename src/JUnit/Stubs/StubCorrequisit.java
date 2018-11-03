package JUnit.Stubs;

import domain.Classe;

import java.util.ArrayList;
import java.util.Stack;

public class StubCorrequisit extends domain.Corequisit {

    public StubCorrequisit() {
        super();
    }

    @Override
    public ArrayList<String> getAssignatures() {
        return null;
    }

    @Override
    public boolean addAssignatura(String id_assignatura) {
        return true;
    }

    @Override
    public boolean checkRestriccio(Stack<Classe> assignats) {
        return true;
    }

    @Override
    public boolean checkCorrecte(Classe c1, Classe c2) {
        return true;
    }
}
