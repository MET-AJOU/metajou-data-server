package com.minshigee.dataserver.domain.profile.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
public class GetProfileDto {
    String userEmail;
    String userName;
    String userImage;
}
