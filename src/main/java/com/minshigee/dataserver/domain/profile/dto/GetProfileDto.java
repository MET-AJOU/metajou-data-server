package com.minshigee.dataserver.domain.profile.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;

@Builder
@ToString
@Data
public class GetProfileDto {
    @Email
    String userEmail;
    String userName;
    String userImage;
}
