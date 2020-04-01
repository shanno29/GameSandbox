package sample.core.state;

import java.util.function.Function;

public class State {

    public interface StateMutator<S extends State> extends Function<Action, Function<S, S>> {}

}
