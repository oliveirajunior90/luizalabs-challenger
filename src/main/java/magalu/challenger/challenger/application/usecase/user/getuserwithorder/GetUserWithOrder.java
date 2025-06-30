package magalu.challenger.challenger.application.usecase.user.getuserwithorder;

import magalu.challenger.challenger.application.dto.PageResponseDTO;
import magalu.challenger.challenger.application.dto.UserWithOrdersDTO;
import magalu.challenger.challenger.shared.usecase.UseCaseInOut;
import org.springframework.data.domain.Pageable;

public interface GetUserWithOrder extends UseCaseInOut<Pageable, PageResponseDTO<UserWithOrdersDTO>> {

}
