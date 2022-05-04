package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.CertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.persistence.repository.entity.TagEntity;
import com.epam.esm.persistence.repository.sort.SortColumn;
import com.epam.esm.persistence.repository.sort.SortOrder;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.aspect.Loggable;
import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.certificate.CertificateDTO;
import com.epam.esm.service.dto.certificate.CertificateExclusivePatchDTO;
import com.epam.esm.service.dto.certificate.CertificateNewDTO;
import com.epam.esm.service.dto.certificate.CertificatePatchDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import com.epam.esm.service.mapper.СertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.pagination.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    private final СertificateMapper certificateMapper;
    private final TagMapper tagMapper;
    private final Paginator paginator;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  TagRepository tagRepository,
                                  СertificateMapper certificateMapper,
                                  TagMapper tagMapper,
                                  Paginator paginator) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
        this.paginator = paginator;
    }

    @Override
    public ObjectListDTO<CertificateDTO> getAll(int page, int size) {
        List<CertificateEntity> certificates = certificateRepository
                .getAll(size, paginator.getOffset(page, size));
        int count = certificateRepository.getCount();

        return getCertificateObjectListDTO(certificates, paginator.getPage(size, count, page));
    }

    @Override
    public Optional<CertificateDTO> getById(Integer id) {
        Optional<CertificateEntity> certificate = certificateRepository.getById(id);

        return certificate.map(certificateMapper::toDto);
    }

    @Override
    public ObjectListDTO<CertificateDTO> getByName(String name, int page, int size) {
        List<CertificateEntity> certificates = certificateRepository
                .getByName(name, size, paginator.getOffset(page, size));
        int count = certificateRepository.getByNameCount(name);

        return getCertificateObjectListDTO(certificates, paginator.getPage(size, count, page));
    }

    @Override
    public ObjectListDTO<CertificateDTO> getAllSorted(SortColumn sort, SortOrder order, int page, int size) {
        List<CertificateEntity> certificates = certificateRepository
                .getAllSorted(sort, order, size, paginator.getOffset(page, size));
        int count = certificateRepository.getCount();

        return getCertificateObjectListDTO(certificates, paginator.getPage(size, count, page));
    }

    @Override
    public ObjectListDTO<CertificateDTO> getByTagNames(List<String> tags, int page, int size) {
        List<CertificateEntity> certificates;
        int count;

        if (tags.size() > 1) {
            certificates = certificateRepository
                    .getByMultipleTagNames(tags, size, paginator.getOffset(page, size));
            count = certificateRepository.getByTagsCount(tags);
        } else {
            certificates = certificateRepository
                    .getByTagName(tags.get(0), size, paginator.getOffset(page, size));
            count = certificateRepository.getByTagCount(tags.get(0));
        }

        return getCertificateObjectListDTO(certificates, paginator.getPage(size, count, page));
    }

    @Override
    public ObjectListDTO<CertificateDTO> searchByNameOrDescription(String search, int page, int size) {
        List<CertificateEntity> certificates = certificateRepository
                .searchByNameOrDescription(search, size, paginator.getOffset(page, size));
        int count = certificateRepository.getSearchCount(search);

        return getCertificateObjectListDTO(certificates, paginator.getPage(size, count, page));
    }

    @Override
    @Loggable
    @Transactional
    public Optional<CertificateDTO> create(CertificateNewDTO certificate) {
        CertificateEntity createCertificate = certificateMapper.toCertificateEntity(certificate.setId(0));
        Optional<Integer> certificateId = certificateRepository.create(createCertificate);

        if (!certificateId.isPresent()) {
            return Optional.empty();
        }

        int id = certificateId.get();
        addTags(id, certificate.getTags());

        return getById(createCertificate);
    }

    @Override
    @Loggable
    @Transactional
    public Optional<CertificateDTO> update(CertificateNewDTO certificate) {
        Optional<CertificateEntity> oldCertificate = certificateRepository.getById(certificate.getId());

        if (!oldCertificate.isPresent()) {
            return Optional.empty();
        }

        return updateCertificate(certificate, oldCertificate.get());
    }

    @Override
    @Loggable
    @Transactional
    public Optional<CertificateDTO> patch(CertificatePatchDTO certificate) {
        int id = certificate.getId();

        Optional<CertificateEntity> oldCertificate = certificateRepository.getById(id);

        if (!oldCertificate.isPresent()) {
            return Optional.empty();
        }

        CertificateEntity oldCertificateSave = certificateMapper.copyCertificateEntity(oldCertificate.get());
        CertificateEntity updateCertificate = oldCertificate.get();

        List<TagEntity> oldTags = oldCertificate.get().getTags();

        List<TagDTO> newTags = new ArrayList<>();
        setPatchFields(certificate, updateCertificate, newTags);

        if (oldCertificateSave.equals(updateCertificate) && newTags.isEmpty()) {
            return Optional.of(certificateMapper.toDto(oldCertificateSave));
        }

        updateCertificate.setLastUpdateDate(null).setTags(oldTags);

        int result = certificateRepository.update(updateCertificate);

        if (result == 0) {
            return Optional.empty();
        }

        if (!newTags.isEmpty()) {
            addTags(id, newTags);
        }

        return getById(updateCertificate);
    }

    @Override
    @Loggable
    @Transactional
    public Optional<CertificateDTO> delete(Integer id) {
        Optional<CertificateEntity> certificate = certificateRepository.getById(id);

        if (!certificate.isPresent()) {
            return Optional.empty();
        }

        int result = certificateRepository.delete(id);

        if (result == 0) {
            return Optional.empty();
        }

        return certificate.map(certificateMapper::toDto);
    }

    @Override
    @Loggable
    @Transactional
    public Optional<CertificateDTO> exclusivePatch(CertificateExclusivePatchDTO certificate) {
        int id = certificate.getId();
        Optional<CertificateEntity> updateCertificate = certificateRepository.getById(id);
        if (!updateCertificate.isPresent()) {
            return Optional.empty();
        }

        if ((certificate.getPrice() != null && (Double.parseDouble(certificate.getPrice()))
                == (updateCertificate.get().getPrice())) || (certificate.getDuration() != null
                && certificate.getDuration().equals(updateCertificate.get().getDuration()))) {
            return getById(certificate.getId());
        }

        int result = 0;

        if (certificate.getPrice() != null) {
            updateCertificate.get().setPrice(Double.parseDouble(certificate.getPrice()));
            result = certificateRepository.update(updateCertificate.get());
        } else if (certificate.getDuration() != null) {
            updateCertificate.get().setDuration(certificate.getDuration());
            result = certificateRepository.update(updateCertificate.get());
        }

        return result == 1
                ? getById(id)
                : Optional.empty();
    }

    private Optional<CertificateDTO> getById(CertificateEntity certificate) {
        Optional<CertificateEntity> gc = certificateRepository.getById(certificate);

        return gc.map(certificateMapper::toDto);
    }

    private Optional<CertificateDTO> updateCertificate(CertificateNewDTO certificate,
                                                       CertificateEntity oldCertificate) {
        List<TagEntity> oldTags = oldCertificate.getTags();

        CertificateDTO oldCertificateDTO = certificateMapper.toDto(oldCertificate);
        String createDate = oldCertificateDTO.getCreateDate();
        String lastUpdateDate = oldCertificateDTO.getLastUpdateDate();
        oldCertificateDTO.setCreateDate(null).setLastUpdateDate(null);

        if (oldCertificateDTO.equals(certificateMapper.toDto(certificate))) {
            oldCertificateDTO.setCreateDate(createDate);
            oldCertificateDTO.setLastUpdateDate(lastUpdateDate);
            return Optional.of(oldCertificateDTO);
        }

        CertificateEntity updateCertificate = certificateMapper.toCertificateEntity(certificate);
        updateCertificate.setCreateDate(LocalDateTime.parse(createDate)).setTags(oldTags);

        int result = certificateRepository.update(updateCertificate);

        if (result == 0) {
            return Optional.empty();
        }

        updateTags(certificate.getId(), certificate.getTags(), oldTags);

        return getById(updateCertificate);
    }

    private void updateTags(int id, List<TagDTO> tags, List<TagEntity> oldTags) {
        List<TagDTO> newTags = getNewTags(tags, id);
        addTags(id, newTags);
        deleteTags(id, tags, oldTags);
    }

    private List<TagDTO> getNewTags(List<TagDTO> tags, int id) {
        return tags.stream()
                .filter(tag -> !tagRepository.getCertificateTagByTagName(id, tag.getName()).isPresent())
                .collect(Collectors.toList());
    }

    private void addTags(int id, List<TagDTO> tags) {
        //add to tag non-existing tags
        tags.stream()
                .filter(tag -> !tagRepository.getByName(tag.getName()).isPresent())
                .map(tagMapper::toTag)
                .forEach(tagRepository::create);
        //add to certificates_tags (m2m)
        tags.stream()
                .map(tag -> tagRepository.getByName(tag.getName()))
                .filter(Optional::isPresent)
                .forEach(tag -> tagRepository.addCertificateTag(id, tag.get().getId()));
    }

    private void deleteTags(int id, List<TagDTO> tags, List<TagEntity> oldTags) {
        //if tag from existing tags doesn't exist in new tags -> delete
        List<String> existingTags = oldTags.stream()
                .map(TagEntity::getName).collect(Collectors.toList());
        List<String> updateTags = tags.stream()
                .map(TagDTO::getName).collect(Collectors.toList());

        existingTags.stream()
                .filter(tag -> !updateTags.contains(tag))
                .map(tagRepository::getByName)
                .filter(Optional::isPresent)
                .forEach(tag -> tagRepository.deleteCertificateTag(id, tag.get().getId()));
    }

    private void setPatchFields(CertificatePatchDTO certificate,
                                CertificateEntity updateCertificate,
                                List<TagDTO> newTags) {
        int id = certificate.getId();

        if (certificate.getName() != null) {
            updateCertificate.setName(certificate.getName());
        }
        if (certificate.getDescription() != null) {
            updateCertificate.setDescription(certificate.getDescription());
        }
        if (certificate.getPrice() != null) {
            updateCertificate.setPrice(Double.parseDouble(certificate.getPrice()));
        }
        if (certificate.getDuration() != 0) {
            updateCertificate.setDuration(certificate.getDuration());
        }
        if (null != certificate.getTags() && !certificate.getTags().isEmpty()) {
            newTags.addAll(getNewTags(certificate.getTags(), id));
        }
    }

    private ObjectListDTO<CertificateDTO> getCertificateObjectListDTO(List<CertificateEntity> certificates, PageDTO pageDTO) {
        ObjectListDTO<CertificateDTO> objectListDTO = new ObjectListDTO<>();
        objectListDTO.setObjects(getCertificateDTOs(certificates));
        objectListDTO.setPage(pageDTO);

        return objectListDTO;
    }

    private List<CertificateDTO> getCertificateDTOs(List<CertificateEntity> certificates) {
        return certificates
                .stream()
                .map(certificateMapper::toDto)
                .collect(Collectors.toList());
    }
}

