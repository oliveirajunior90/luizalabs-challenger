package magalu.challenger.challenger.shared;

@FunctionalInterface
public interface UseCase<I> {
    void execute(I input);
}
