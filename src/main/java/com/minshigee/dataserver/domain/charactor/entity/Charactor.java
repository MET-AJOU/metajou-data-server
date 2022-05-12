package com.minshigee.dataserver.domain.charactor.entity;

import com.minshigee.dataserver.domain.charactor.dto.GetCharactorDto;
import com.minshigee.dataserver.security.entity.CustomUser;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Size;

@Data
@ToString
@Table("Charactor")
@Builder
public class Charactor {

    @Id
    @Column("id")
    Long id;
    @NonNull
    @Column("user_code")
    Long userCode;
    @Size(max = 192)
    @Column("avatar_code")
    Long avatarCode;
    @Column("avatar_custom_code")
    String avatarCustomCode;
    @Column("available_change_cnt")
    Long availableChangeCnt;

    public GetCharactorDto extractGetCharactorDto() {
        return GetCharactorDto.builder()
                .avatarCode(avatarCode)
                .avatarCustomCode(avatarCustomCode)
                .availableChangeCnt(availableChangeCnt)
                .build();
    }

    public static Charactor createCharactorUsingAuthInfo(CustomUser user) {
        return Charactor.builder()
                .userCode(user.getUserCode())
                .availableChangeCnt(5L)
                .avatarCode(1L)
                .avatarCustomCode("aaaaaaaa")
                .build();
    }

    public void useChangeCount() {
        availableChangeCnt -= 1;
    }

}
