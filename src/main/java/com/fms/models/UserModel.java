package com.fms.models;
import com.fms.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@NoArgsConstructor
public class UserModel extends BaseModel{
    private Long userId;
    private String userName;
    private String email;
    private Long roleId;
    private User.UserStatus status;

    public UserModel(User user){
        super(user);
        this.userId = user.getUserId();
        this.userName = user.getName();
        this.email = user.getEmail();
        this.roleId = !ObjectUtils.isEmpty(user.getRole()) ?
                user.getRole().getRoleId() : null;
        this.status = user.getStatus();

    }

}
