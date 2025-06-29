package magalu.challenger.challenger.shared.usecase;

@FunctionalInterface
public interface UseCase<I> {
    void execute(I input);
}
