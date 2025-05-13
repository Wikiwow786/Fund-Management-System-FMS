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
    private String name;
    private String password;
    private String currentPassword;
    private String language;
    private Long roleId;
    private String roleName;
    private User.UserStatus status;

    public UserModel(User user){
        super(user);
        this.userId = user.getUserId();
        this.name = user.getName();
        this.language = user.getLanguage();
        this.roleId = !ObjectUtils.isEmpty(user.getRole()) ?
                user.getRole().getRoleId() : null;
        this.roleName = !ObjectUtils.isEmpty(user.getRole()) ?
                user.getRole().getRoleName() : null;
        this.status = user.getStatus();

    }

}
