package com.minshigee.dataserver.domain.character.dto;

import com.minshigee.dataserver.domain.character.entity.Character;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCharacterDto {

    String avatarCustomCode;

    public Character updateCharactor(Character charactor) {
        if(avatarCustomCode != null)
            charactor.setAvatarCustomCode(avatarCustomCode);
        charactor.useChangeCount();
        return charactor;
    }
}
