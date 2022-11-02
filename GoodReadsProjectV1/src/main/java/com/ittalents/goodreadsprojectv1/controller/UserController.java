package com.ittalents.goodreadsprojectv1.controller;



import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.users.*;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.UnauthorizedException;
import com.ittalents.goodreadsprojectv1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;


    @PostMapping("/users")
    public UserRespWithoutPassDTO register(@RequestBody UserReqRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/auth")
    public UserWithoutRelationsDTO login(@RequestBody UserReqLoginDTO dto, HttpServletRequest request) {
        if (getLoggedUserId(request) > 0) {
            throw new BadRequestException("You already have logged!");
        }
        UserWithoutRelationsDTO response = userService.login(dto);
        if (response != null) {
            logUser(request, response.getId());
            return response;
        } else {
            throw new BadRequestException("Wrong credentials!");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }


    //                          ----DELETE USER
    @DeleteMapping("/users")
    public void deleteUser(HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        userService.delete(id);
        request.getSession().invalidate();
    }

    //                          ----FOLLOW USER
    @PutMapping("/users")
    public UserFollowingDTO followUser(@RequestParam int fid, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        if (fid == id) {
            throw new UnauthorizedException("You can't follow yourself, narcissus!");
        }
        return userService.followUser(fid, id);
    }

    //                          ----GET ALL USERS
    @GetMapping("/users")
    public List<UserWithoutRelationsDTO> getAllUsers() {
        return userService.findAll();
    }


    //                          ----CHANGE PASS
    @PutMapping("/users/sth")
    public UserRespWithoutPassDTO changePass(@RequestBody UserReqChangePassDTO dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return userService.changePass(dto, id);

    }

    //                          ----GET BY ID
    @GetMapping("/users/sth")
    public UserRespWithoutPassDTO getById(@RequestParam int uid) {
        return userService.getById(uid);
    }

//                          ----EDIT PROFILE

    @PutMapping("/users/user")
    public UserRespWithoutPassDTO editProfile(@RequestBody UserEditProfile dto, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        checkLog(id);
        return userService.editProfile(dto, id);
    }

    //                          ----GET USER BY EMAIL
    @GetMapping("users/user")
    public UserWithoutRelationsDTO getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    //                          ----GET USER SHELVES
    @GetMapping("/users/sh")
    public UserShelvesDTO getAllUserShelves(@RequestParam int uid) {
        return userService.findAllUserShelves(uid);

    }

    //                          ----GET USER by firstName
    @GetMapping("users/users")
    public List<UserWithoutRelationsDTO> findUsersByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    //                          ----GET ALL USERS - Paginated Result
    @GetMapping("/users/page")
    public Page<UserWithoutRelationsDTO> getAll(@RequestParam int page, @RequestParam int size) {
        return userService.getAll(page, size);
    }

    //                          ----GET USER's Friends
    @GetMapping("users/friends")
    public List<UserRespFriendDTO>  getFriends(@RequestParam int id) throws SQLException{
        return userService.getUserFriends(id);

    }

    //                          ----GET USER reviews(sum)
    @GetMapping("users/review")
    public int getTotalReviews(@PathVariable int uid) {
        return userService.getAllReviews(uid);
    }

    //    ----GET USER likedGenres
    @GetMapping("users/gen")
    public UserGenresDTO getUserGenres(@RequestParam int uid) {
        return userService.getUserGenres(uid);
    }

    //                          ----GET USER comments
    @GetMapping("users/com")
    public UserCommentsDTO UserComments(@PathVariable int uid) {
        return userService.getUserComments(uid);
    }


    //                          ----GET USER reviews
    @GetMapping("users/rev")
    public UserReviewsDTO getUserReviews(@PathVariable int uid) {
        return userService.getUserReviews(uid);
    }
    //                          ----GET AVRRate
    @GetMapping("users/avrRate")
    public double getAvrRateOfReviews(@RequestParam int id) throws SQLException {
        return userService.getAvrRate(id);
    }
    //                          ----GET ReviewTotalRate
    @GetMapping("users/total")
    public int getTotalRate(@RequestParam int id) throws SQLException {
        return userService.getTotalRate(id);
    }


        //                          ----GET ReviewSum
        @GetMapping("users/sum")
        public int getSumRate(@RequestParam int id) throws SQLException {
            return userService.getSumRateReviews(id);
        }
    @PostMapping("/users/pic")
    public UserWithoutRelationsDTO uploadPicture(@RequestParam MultipartFile file, HttpServletRequest request){
        int uid = getLoggedUserId(request);
        checkLog(uid);
        return userService.uploadPicture(file, uid);
    }
}


