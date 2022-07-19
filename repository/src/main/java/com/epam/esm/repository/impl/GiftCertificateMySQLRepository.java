package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.*;

import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SEARCH_PARAM;
import static com.epam.esm.repository.constant.SearchParam.GIFT_SEARCH_BY_TAG_NAME;

/**
 * MySQL repository for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class GiftCertificateMySQLRepository extends AbstractMySQLRepository<GiftCertificateEntity> implements GiftCertificateRepository {

    private static final String SELECT_FROM_GIFT = "select distinct g from GiftCertificateEntity g left join g.tags t";
    public static final String SELECT_COUNT_FROM_GIFT = "select count (gc.id) from GiftCertificateEntity gc where gc in (";

    public GiftCertificateMySQLRepository() {
        setClazz(GiftCertificateEntity.class);
    }

    @Override
    @Transactional
    public List<GiftCertificateEntity> findByParams(CriteriaEntity cr) throws RepositoryException {
        Map<String, String> criteriaMap = cr.getSearchParam();
        String sorting = cr.getSorting();
        StringBuilder findQuery = new StringBuilder(SELECT_FROM_GIFT);
        String[] tags = null;

        if (criteriaMap != null) {
            if (!criteriaMap.isEmpty()) {
                findQuery.append(" where ");
            }
            Iterator<String> iter = criteriaMap.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                if (GIFT_CERTIFICATE_SEARCH_PARAM.contains(s)) {
                    findQuery.append("g.");
                }

                if (GIFT_SEARCH_BY_TAG_NAME.equals(s)) {
                    String s1 = criteriaMap.get(s);
                    if (s1.contains(",")) {
                        tags = s1.split(",");
                        Iterator<String> tagsIt = Arrays.stream(tags).iterator();
                        while (tagsIt.hasNext()) {
                            findQuery.append(s).append(" ='");
                            String tag = tagsIt.next();
                            findQuery.append(tag);
                            findQuery.append("'");
                            if (tagsIt.hasNext()) {
                                findQuery.append(" or ");
                            }
                        }
                    } else {
                        findQuery.append(s).append(" ='").append(s1).append("'");
                    }
                } else {
                    findQuery.append(s).append(" like '%")
                            .append(criteriaMap.get(s)).append("%'");
                }
                if (iter.hasNext()) {
                    findQuery.append(" and ");
                }
            }
        }

        if (tags != null) {
            findQuery.append(" group by g.id");
            findQuery.append(" having count(t.name) = ").append(tags.length);
        }

        if (sorting != null) {
            boolean desc = false;
            if (sorting.startsWith("-")) {
                desc = true;
                sorting = sorting.substring(1);
            }
            if (sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }
            if (SearchParam.GIFT_CERTIFICATE_SORT_PARAM.contains(sorting)) {
                findQuery.append(" order by ");
                if (GIFT_CERTIFICATE_SEARCH_PARAM.contains(sorting)) {
                    findQuery.append("g.");
                }
                findQuery.append(sorting);
                if (desc) {
                    findQuery.append(" desc");
                }
            }
        }

        TypedQuery<GiftCertificateEntity> query = entityManager.createQuery(findQuery.toString(),
                GiftCertificateEntity.class);

        if(cr.getSize() != null && cr.getPage() != null){
            query.setFirstResult((cr.getPage() - 1) * cr.getSize());
            query.setMaxResults(cr.getSize());
        }

        cr.setTotalSize((long) entityManager
                .createQuery(findQuery.insert(0, SELECT_COUNT_FROM_GIFT).append(")").toString())
                .getSingleResult());

        return query.getResultList();
    }

//    @Override
//    @Transactional
//    public List<GiftCertificateEntity> findByParams(CriteriaEntity cr) throws RepositoryException {
//        Map<String, String> criteriaMap = cr.getSearchParam();
//        String sorting = cr.getSorting();
//        StringBuilder findQuery = new StringBuilder("select distinct g from GiftCertificateEntity g left join g.tags t");
//        String[] tags = null;
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<GiftCertificateEntity> cq;
//        TypedQuery<GiftCertificateEntity> query;
//
//        cq = cb.createQuery(clazz);
//        Metamodel m = entityManager.getMetamodel();
//        EntityType<GiftCertificateEntity> gift_ = m.entity(GiftCertificateEntity.class);
//        EntityType<TagEntity> tag_ = m.entity(TagEntity.class);
//        Root<GiftCertificateEntity> gift = cq.from(clazz);
//        Join<GiftCertificateEntity, TagEntity> tag = gift.join("tags", JoinType.LEFT);
//
//        cq.select(gift);
//        cq.distinct(true);
//        cq.orderBy(cb.asc(gift.get(sorting)));
//
////        Path<String> path = root.get("name");
////        List<Predicate> predicates = new ArrayList<>();
////        predicates.add(cb.like(path, "Ночной"));
////        cq.where(cb.equal(tag.get("name"), "Автоспорт"));
////        cq.where(cb.equal(tag.get("name"), "Автолюбителям"));
////        cq.where(cb.or(predicates);
//
//
////        Subquery<TagEntity> subquery = query.subquery(TagEntity.class);
////        Root<TagEntity> tagEntityRoot = subquery.from(TagEntity.class);
////        subquery.select(tagEntityRoot).where(cb.like(tagEntityRoot.get("name"), "%Автолюбителям%"));
//
//
//
//
////        Predicate greaterThanPrice = cb.lt(root.get("price"), 11);
////        Predicate chairItems = cb.like(root.get("Tag.name"), "%Автолюбителям%");
////        query.select(root).where(cb.or(greaterThanPrice, chairItems));
//
////        query.select(root).where(cb.in()).;
//
//
//
//        Predicate [] predicates = new Predicate[1];
////        predicates[0] = cb.in(tag.get("name")).value("Автоспорт").value("Автолюбителям");
////        cq.where(cb.or(predicates));
////        cq.groupBy(gift.get("id"));
////        cq.having(cb.equal(cb.count(tag.get("name")), 2));
//
//
//
//        cr.setTotalSize(0l);
//        Subquery<GiftCertificateEntity> subquery = cq.subquery(GiftCertificateEntity.class);
//        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
////        countCq.select(cb.count(countCq.from(clazz).in(subquery)));
//        countCq.select(cb.count(gift)).where(cb.in(gift).value(subquery));
//        cr.setTotalSize(entityManager.createQuery(countCq).getSingleResult());
//
//        query = entityManager.createQuery(cq);
//
//        if (cr.getSize() != null && cr.getPage() != null) {
//            query.setFirstResult((cr.getPage() - 1) * cr.getSize());
//            query.setMaxResults(30);
//        } else {
//            throw new RepositoryException("Size and page must be not null");
//        }
//
//        return query.getResultList();
//    }
}
