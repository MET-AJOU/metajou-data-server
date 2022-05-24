package com.minshigee.dataserver.domain.character.entity;

import com.minshigee.dataserver.domain.character.dto.GetCharacterDto;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table("Charactor")
@Builder
public class Character {

    @Id
    @Column("id")
    Long id;
    @NonNull
    @Column("user_code")
    Long userCode;
    @Column("avatar_custom_code")
    String avatarCustomCode;
    @Column("available_change_cnt")
    Long availableChangeCnt;

    public GetCharacterDto extractGetCharacterDto() {
        return GetCharacterDto.builder()
                .avatarCustomCode(avatarCustomCode)
                .availableChangeCnt(availableChangeCnt)
                .build();
    }

    public static Character createCharacterUsingAuthInfo(CustomUser user) {
        return Character.builder()
                .userCode(user.getUserCode())
                .availableChangeCnt(5L)
                .avatarCustomCode(null)
                .build();
    }

    public void useChangeCount() {
        availableChangeCnt -= 1;
    }

}
