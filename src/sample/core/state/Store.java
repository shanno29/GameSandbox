package sample.core.state;

import sample.core.state.State.StateMutator;

import java.util.function.Consumer;

public class Store<S extends State> {

    private StateMutator<S> mutator;
    private S state;


    public static <S extends State> Store<S> def(S state, StateMutator<S> mutator) {
        return new Store<>(state, mutator);
    }

    private Store(S state, StateMutator<S> mutator) {
        this.state = state;
        this.mutator = mutator;
    }

    public void getState(Consumer<S> stateConsumer) {
        stateConsumer.accept(this.state);
    }

    public void apply(Action action) {
        this.state = mutator.apply(action).apply(this.state);
    }

}
