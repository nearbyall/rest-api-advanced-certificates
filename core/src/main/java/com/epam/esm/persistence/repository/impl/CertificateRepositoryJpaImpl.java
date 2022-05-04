package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.repository.CertificateRepository;
import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.persistence.repository.sort.SortColumn;
import com.epam.esm.persistence.repository.sort.SortOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CertificateRepositoryJpaImpl implements CertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CertificateEntity> getByName(String name, int limit, int offset) {
        return entityManager
                .createQuery("SELECT c FROM CertificateEntity c " +
                        "WHERE c.name = :name", CertificateEntity.class)
                .setParameter("name", name)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<CertificateEntity> getById(CertificateEntity certificate) {
        entityManager.flush();
        entityManager.detach(entityManager.find(CertificateEntity.class, certificate.getId()));

        return getById(certificate.getId());
    }

    @Override
    public List<CertificateEntity> getByTagName(String tag, int limit, int offset) {
        return entityManager
                .createQuery("SELECT c FROM CertificateEntity c " +
                        "JOIN c.tags t " +
                        "WHERE t.name = :tag", CertificateEntity.class)
                .setParameter("tag", tag)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<CertificateEntity> getByMultipleTagNames(List<String> tags, int limit, int offset) {
        return entityManager
                .createQuery("SELECT c FROM CertificateEntity c " +
                        "JOIN c.tags t " +
                        "WHERE t.name IN (:tagRange) " +
                        "GROUP BY c.id " +
                        "HAVING COUNT(c) = :tagCount", CertificateEntity.class)
                .setParameter("tagRange", tags)
                .setParameter("tagCount", (long) tags.size())
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<CertificateEntity> getAllSorted(SortColumn sort, SortOrder order, int limit, int offset) {
        String sql = String.format("SELECT c FROM CertificateEntity c ORDER BY %s %s",
                sort.getSortTable(), order.toString());

        return entityManager
                .createQuery(sql, CertificateEntity.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<CertificateEntity> searchByNameOrDescription(String search, int limit, int offset) {
        return entityManager
                .createQuery("SELECT c FROM CertificateEntity c " +
                        "WHERE LOWER(c.name) LIKE :search OR LOWER(c.description) LIKE :search", CertificateEntity.class)
                .setParameter("search", "%" + search + "%")
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Integer getCount() {
        return entityManager
                .createQuery("SELECT COUNT(c) FROM CertificateEntity c", Long.class)
                .getSingleResult()
                .intValue();
    }

    @Override
    public Integer getByNameCount(String name) {
        return entityManager
                .createQuery("SELECT COUNT(c) FROM CertificateEntity c WHERE c.name = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult()
                .intValue();
    }

    @Override
    public Integer getSearchCount(String search) {
        return entityManager
                .createQuery("SELECT COUNT(c) FROM CertificateEntity c " +
                        "WHERE LOWER(c.name) LIKE :search OR LOWER(c.description) LIKE :search", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult()
                .intValue();
    }

    @Override
    public Integer getByTagCount(String tag) {
        return entityManager
                .createQuery("SELECT COUNT(c) FROM CertificateEntity c " +
                        "JOIN c.tags t " +
                        "WHERE t.name = :tag", Long.class)
                .setParameter("tag", tag)
                .getSingleResult()
                .intValue();
    }

    @Override
    public Integer getByTagsCount(List<String> tags) {
        return entityManager.createQuery("SELECT COUNT(c.id) FROM CertificateEntity c " +
                        "WHERE c.id IN (SELECT c FROM CertificateEntity c " +
                        "JOIN c.tags t " +
                        "WHERE t.name IN (:tagRange) " +
                        "GROUP BY c.id " +
                        "HAVING COUNT(c) = :tagCount )", Long.class)
                .setParameter("tagRange", tags)
                .setParameter("tagCount", (long) tags.size())
                .getSingleResult()
                .intValue();
    }

    @Override
    public Optional<Integer> create(CertificateEntity certificate) {
        entityManager.persist(certificate);

        Integer id = certificate.getId();
        return id != 0
                ? Optional.of(id)
                : Optional.empty();
    }

    @Override
    public List<CertificateEntity> getAll(int limit, int offset) {
        return entityManager
                .createQuery("SELECT с FROM CertificateEntity с", CertificateEntity.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<CertificateEntity> getById(Integer id) {
        List<CertificateEntity> certificates = entityManager
                .createQuery("SELECT с FROM CertificateEntity с WHERE с.id = :id", CertificateEntity.class)
                .setParameter("id", id)
                .getResultList();

        return certificates.isEmpty()
                ? Optional.empty()
                : Optional.of(certificates.get(0));
    }

    @Override
    public Integer update(CertificateEntity certificate) {
        entityManager.merge(certificate);
        return 1;
    }

    @Override
    public Integer delete(Integer id) {
        return entityManager
                .createQuery("DELETE FROM CertificateEntity WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
