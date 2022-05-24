package com.minshigee.dataserver.domain.character.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class GetCharacterDto {
    String avatarCustomCode;
    Long availableChangeCnt;
}
