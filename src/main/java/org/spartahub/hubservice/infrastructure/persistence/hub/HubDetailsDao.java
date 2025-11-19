package org.spartahub.hubservice.infrastructure.persistence.hub;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spartahub.hubservice.domain.Hub;
import org.spartahub.hubservice.domain.HubDetailsRepository;
import org.spartahub.hubservice.domain.QHub;
import org.spartahub.hubservice.domain.dto.HubDto;
import org.spartahub.hubservice.infrastructure.persistence.exception.HubNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

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
    public HubDto findById(Long id) {
        QHub qHub = QHub.hub;
        Hub hub = queryFactory.selectFrom(qHub)
                .where(qHub.hubId.eq(id), qHub.deletedAt.isNull())
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
    @Cacheable(cacheNames = "hubItems", condition = "#p1 != null", key="args[0]")
    public List<HubDto> findAll(Collection<Long> ids) {
        QHub hub = QHub.hub;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(hub.deletedAt.isNull());
        if (ids != null && !ids.isEmpty()) builder.and(hub.hubId.in(ids));

        List<Hub> items = queryFactory.selectFrom(hub)
                .where(builder)
                .orderBy(hub.hubName.asc())
                .fetch();
        return  items == null ? null : items.stream().map(Hub::toDto).toList();
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
