package org.spartahub.hubservice.infrastructure.persistence.hub;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.domain.hub.Hub;
import org.spartahub.hubservice.domain.hub.HubDetailsRepository;
import org.spartahub.hubservice.domain.hub.HubId;
import org.spartahub.hubservice.domain.hub.QHub;
import org.spartahub.hubservice.domain.hub.dto.HubDto;
import org.spartahub.hubservice.infrastructure.persistence.exception.HubNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubDetailsDao implements HubDetailsRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 허브 하나 조회
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "hubItem", key="args[0].id")
    public HubDto findById(HubId id) {
        QHub qHub = QHub.hub;
        Hub hub = queryFactory.selectFrom(qHub)
                .where(qHub.id.eq(id), qHub.deletedAt.isNull())
                .fetchFirst();
        if (hub == null) throw new HubNotFoundException();

        return hub.toDto();
    }

    /**
     * 허브 여러개 조회
     *
     * @param ids
     * @return
     */
    @Override
    @Cacheable(cacheNames = "hubItems", key="args[0]")
    public List<HubDto> findAll(Collection<HubId> ids) {
        QHub hub = QHub.hub;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(hub.deletedAt.isNull());
        if (ids != null && !ids.isEmpty()) builder.and(hub.id.in(ids));

        List<Hub> items = queryFactory.selectFrom(hub)
                .where(builder)
                .orderBy(hub.hubName.asc())
                .fetch();
        return  items == null ? null : items.stream().map(Hub::toDto).toList();
    }

    public List<HubDto> findAllByUUID(Collection<UUID> ids) {
        return findAll(ids == null ? null : ids.stream().map(HubId::of).toList());
    }

    /**
     * 허브 전체 조회
     *
     * @return
     */
    @Cacheable(cacheNames = "hubItems")
    public List<HubDto> findAll() {
        return findAll(null);
    }
}
