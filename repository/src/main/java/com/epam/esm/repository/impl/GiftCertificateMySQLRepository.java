package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.epam.esm.repository.constant.Constant.AND;
import static com.epam.esm.repository.constant.Constant.APOSTROPHE;
import static com.epam.esm.repository.constant.Constant.COMMA;
import static com.epam.esm.repository.constant.Constant.DESC;
import static com.epam.esm.repository.constant.Constant.EQUALS;
import static com.epam.esm.repository.constant.Constant.MINUS;
import static com.epam.esm.repository.constant.Constant.OR;
import static com.epam.esm.repository.constant.Constant.PLUS;
import static com.epam.esm.repository.constant.Constant.ROUND_LEFT_BRACKET;
import static com.epam.esm.repository.constant.Constant.ROUND_RIGHT_BRACKET;
import static com.epam.esm.repository.constant.Constant.SPACE;
import static com.epam.esm.repository.constant.Constant.WHERE;
import static com.epam.esm.repository.constant.ExceptionMes.INCORRECT_SORTING_PARAM;
import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SORT_PARAM;
import static com.epam.esm.repository.constant.SearchParam.GIFT_SEARCH_BY_TAG_NAME;

/**
 * MySQL repository for gift GiftCertificateEntity
 *
 * @author Artsemi Kapitula
 * @version 2.0
 */
@Repository
public class GiftCertificateMySQLRepository extends AbstractMySQLRepository<GiftCertificateEntity> implements GiftCertificateRepository {
    private static final String SELECT_FROM_GIFT = "select distinct g from GiftCertificateEntity g left join g.tags t";
    private static final String SELECT_COUNT_FROM_GIFT = "select count (gc) from GiftCertificateEntity gc where gc in "
            + ROUND_LEFT_BRACKET;
    private static final String GROUP_BY_G_ID = "group by g.id";
    private static final String HAVING_COUNT_T_NAME = "having count(t.name) =";
    private static final String G_DOT = "g.";
    private static final String LIKE = "like '%";
    private static final String PERCENTAGE = "%'";
    private static final String ORDER_BY_G = "order by g.";

    public GiftCertificateMySQLRepository() {
        setClazz(GiftCertificateEntity.class);
    }

    /**
     * Find GiftCertificateEntity by criteria map and sort by sorting param from database.
     *
     * @param cr CriteriaEntity with criteria map and sorting param.
     * @return list with GiftCertificateEntities.
     * @throws RepositoryException if sorting field does not match GIFT_CERTIFICATE_SORT_PARAM.
     *                             If page or size is null or less 1.
     *                             If the page is larger than the total size of the pages.
     */
    @Override
    public List<GiftCertificateEntity> findByCriteria(CriteriaEntity cr) throws RepositoryException {
        StringBuilder findQuery = new StringBuilder(SELECT_FROM_GIFT);

        appendCriteriaToFindQuery(findQuery, cr);
        appendSortingToFindQuery(findQuery, cr);

        TypedQuery<GiftCertificateEntity> query = entityManager.createQuery(findQuery.toString(),
                GiftCertificateEntity.class);

        setPagination(query, cr);

        cr.setTotalSize((long) entityManager
                .createQuery(findQuery.insert(0, SELECT_COUNT_FROM_GIFT).append(ROUND_RIGHT_BRACKET).toString())
                .getSingleResult());

        pageValidation(cr);

        return query.getResultList();
    }

    private void appendSortingToFindQuery(StringBuilder findQuery, CriteriaEntity cr) throws RepositoryException {
        String sorting = cr.getSorting();

        if (sorting != null) {
            boolean desc = false;
            if (sorting.startsWith(MINUS)) {
                desc = true;
                sorting = sorting.substring(1);
            }
            if (sorting.startsWith(PLUS) || sorting.startsWith(SPACE)) {
                sorting = sorting.substring(1);
            }

            if (GIFT_CERTIFICATE_SORT_PARAM.contains(sorting)) {
                findQuery.append(SPACE + ORDER_BY_G);
                findQuery.append(sorting);
                if (desc) {
                    findQuery.append(SPACE + DESC);
                }
            } else {
                throw new RepositoryException(INCORRECT_SORTING_PARAM);
            }
        }
    }

    private void appendCriteriaToFindQuery(StringBuilder findQuery, CriteriaEntity cr) {
        Map<String, String> criteriaMap = cr.getSearchParam();
        String[] tags = null;

        if (criteriaMap != null && !criteriaMap.isEmpty()) {
            findQuery.append(SPACE + WHERE + SPACE);

            Iterator<String> iter = criteriaMap.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();

                if (GIFT_SEARCH_BY_TAG_NAME.equals(s)) {
                    tags = tagsParsingAndAppendToFindQuery(findQuery, criteriaMap.get(s));
                } else {
                    findQuery.append(G_DOT).append(s).append(SPACE + LIKE)
                            .append(criteriaMap.get(s)).append(PERCENTAGE);
                }
                if (iter.hasNext()) {
                    findQuery.append(SPACE + AND + SPACE);
                }
            }
        }

        if (tags != null) {
            findQuery.append(SPACE + GROUP_BY_G_ID);
            findQuery.append(SPACE + HAVING_COUNT_T_NAME + SPACE).append(tags.length);
        }
    }

    private String[] tagsParsingAndAppendToFindQuery(StringBuilder findQuery, String tagNamesStr) {
        String[] tags = null;
        if (tagNamesStr.contains(COMMA)) {
            tags = tagNamesStr.split(COMMA);
            Iterator<String> tagsIt = Arrays.stream(tags).iterator();
            while (tagsIt.hasNext()) {
                findQuery.append(GIFT_SEARCH_BY_TAG_NAME).append(SPACE + EQUALS + APOSTROPHE);
                String tag = tagsIt.next();
                findQuery.append(tag);
                findQuery.append(APOSTROPHE);
                if (tagsIt.hasNext()) {
                    findQuery.append(SPACE + OR + SPACE);
                }
            }
        } else {
            findQuery.append(GIFT_SEARCH_BY_TAG_NAME).append(SPACE + EQUALS + APOSTROPHE).append(tagNamesStr).append(APOSTROPHE);
        }
        return tags;
    }
}
