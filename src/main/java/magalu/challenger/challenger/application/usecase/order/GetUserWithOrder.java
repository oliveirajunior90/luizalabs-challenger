package magalu.challenger.challenger.application.usecase.order;

import magalu.challenger.challenger.application.dto.UserWithOrdersDTO;
import magalu.challenger.challenger.shared.usecase.UseCaseOut;

import java.util.List;


public interface GetUserWithOrder extends UseCaseOut<List<UserWithOrdersDTO>> {

}
