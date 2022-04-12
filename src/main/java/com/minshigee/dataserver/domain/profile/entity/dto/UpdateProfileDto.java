package com.minshigee.dataserver.domain.profile.entity.dto;

import com.minshigee.dataserver.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;

@Data
@Builder
@ToString
public class UpdateProfileDto {
    @Email
    String userEmail;
    String userName;
    String userImage;

    public Profile updateProfile(Profile profile) {
        if(userEmail != null)
            profile.setUserEmail(userEmail);
        if(userName != null)
            profile.setUserName(userName);
        if(userImage != null)
            profile.setUserImage(userImage);
        return profile;
    }

    public Profile makeProfile(String userCode) {
        return Profile.builder()
                .userCode(userCode)
                .userName(userName)
                .userEmail(userEmail)
                .userImage(userImage)
                .build();
    }
}
