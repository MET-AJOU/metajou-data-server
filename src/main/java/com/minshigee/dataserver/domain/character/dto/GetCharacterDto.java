package com.minshigee.dataserver.domain.character.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.Size;

@ToString
@Data
@Builder
public class GetCharacterDto {
    @Size(min = 8, max = 8)
    @Column("avatar_custom_code")
    String avatarCustomCode;
    @Column("available_change_cnt")
    Long availableChangeCnt;
}
