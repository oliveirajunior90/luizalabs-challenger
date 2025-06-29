package magalu.challenger.challenger.shared.usecase;

public interface UseCaseInOut<I,O> {
    O execute(I input);
}
