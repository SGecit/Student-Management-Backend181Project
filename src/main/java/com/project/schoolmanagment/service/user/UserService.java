package com.project.schoolmanagment.service.user;
import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exceptions.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.abstracts.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.payload.mappers.UserMapper;
import com.project.schoolmanagment.repository.user.UserRepository;
import com.project.schoolmanagment.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;

    public ResponseMessage<UserResponse>saveUser(UserRequest userRequest, String userRole){
        //handle uniqueness exceptions
        uniquePropertyValidator.checkDuplicate(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail());


        //map DTO -> Entity (domain object)
        User user = userMapper.mapUserRequestToUser(userRequest);


        //get correct role from DB and set it to the user
        if(userRole.equalsIgnoreCase(RoleType.ADMIN.name())){
            //we are setting a superUser that can not be deleted
            if(Objects.equals(userRequest.getUsername(),"Admin")){
                user.setBuiltIn(true);
            }
            //give the role of admin to this user
            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        } else if(userRole.equalsIgnoreCase("Dean")){
            user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        } else if (userRole.equalsIgnoreCase("ViceDean")) {
            user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
        } else {
            throw new ResourceNotFoundException(String.format(
                    ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE,userRole));
        }
        user.setIsAdvisor(false);
        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATE)
                .object(userMapper.mapUserToUserResponse(savedUser))
                .build();
    }

}