package com.minshigee.dataserver.domain.charactor.entity.dto;

import com.minshigee.dataserver.domain.charactor.entity.Charactor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.Size;

@Data
@Builder
@ToString
public class UpdateCharactorDto {
    @Size(max = 192)
    @Column("avatar_code")
    Long avatarCode;
    @Size(min = 8, max = 8)
    @Column("avatar_custom_code")
    String avatarCustomCode;

    public Charactor updateCharactor(Charactor charactor) {
        if(avatarCode != null)
            charactor.setAvatarCode(avatarCode);
        if(avatarCustomCode != null && avatarCustomCode.length() == 8)
            charactor.setAvatarCustomCode(avatarCustomCode);
        charactor.useChangeCount();
        return charactor;
    }
}
