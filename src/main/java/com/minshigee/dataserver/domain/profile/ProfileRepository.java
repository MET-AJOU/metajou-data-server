package com.minshigee.dataserver.domain.profile;

import com.minshigee.dataserver.domain.profile.entity.Profile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileRepository extends R2dbcRepository<Profile, Long> {
    public Mono<Profile> findProfileByUserCode(String userCode);
}
