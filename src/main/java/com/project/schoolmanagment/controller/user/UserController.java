package com.project.schoolmanagment.controller.user;

import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.request.user.UserRequestWithoutPassword;
import com.project.schoolmanagment.payload.response.abstracts.BaseUserResponse;
import com.project.schoolmanagment.payload.response.abstracts.ResponseMessage;
import com.project.schoolmanagment.payload.response.UserResponse;
import com.project.schoolmanagment.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/save/{userRole}")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable String userRole){
        return ResponseEntity.ok(userService.saveUser(userRequest,userRole));
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/getAllUserByPage/{userRole}")
    public ResponseEntity<Page<UserResponse>>getUsersByPage(
            @PathVariable String userRole,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type) {
        Page<UserResponse>userResponses = userService.getUsersByPage(page,size,sort,type,userRole);
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/getUserByName")
    public List<UserResponse> getUserByName(@RequestParam(name = "name") String userName){
        return userService.getUserByName(userName);
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserRequestWithoutPassword userRequestWithoutPassword,
                                             HttpServletRequest request){
        return userService.updateUserForUsers(userRequestWithoutPassword,request);
    }




}
