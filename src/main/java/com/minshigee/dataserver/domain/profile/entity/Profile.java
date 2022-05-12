package com.minshigee.dataserver.domain.profile.entity;

import com.minshigee.dataserver.domain.profile.dto.GetProfileDto;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;

@Data
@Builder
@ToString
@Table("Profile")
public class Profile {
    @Id
    @Column("id")
    Long id;
    @NonNull
    @Column("user_code")
    Long userCode;
    @Email
    @Column("user_email")
    String userEmail;
    @Column("user_name")
    String userName;
    @Column("user_image")
    String userImage;

    public static Profile createProfileByAuthUserInfo(CustomUser user) {
        return Profile.builder()
                .userCode(user.getUserCode())
                .userName(user.getUserEmail().split("@")[0])
                .userEmail(user.getUserEmail())
                .userImage(null)
                .build();
    }

    public GetProfileDto extractGetProfile() {
        return GetProfileDto.builder()
                .userName(userName)
                .userEmail(userEmail)
                .userImage(userImage)
                .build();
    }

}
