package com.minshigee.dataserver.domain.character.dto;

import com.minshigee.dataserver.domain.character.entity.Character;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.Size;

@Data
@Builder
@ToString
public class UpdateCharacterDto {
    @Size(min = 8, max = 8)
    @Column("avatar_custom_code")
    String avatarCustomCode;

    public Character updateCharactor(Character charactor) {
        if(avatarCustomCode != null && avatarCustomCode.length() == 8)
            charactor.setAvatarCustomCode(avatarCustomCode);
        charactor.useChangeCount();
        return charactor;
    }
}
