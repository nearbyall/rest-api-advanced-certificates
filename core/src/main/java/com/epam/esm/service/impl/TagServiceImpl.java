package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.entity.TagEntity;
import com.epam.esm.service.TagService;
import com.epam.esm.service.aspect.Loggable;
import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import com.epam.esm.service.dto.tag.TagPostDTO;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.pagination.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final TagMapper mapper;
    private final Paginator paginator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          TagMapper mapper,
                          Paginator paginator) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.paginator = paginator;
    }

    @Override
    public ObjectListDTO<TagDTO> getAll(int page, int size) {
        List<TagEntity> tags = tagRepository.getAll(size, paginator.getOffset(page, size));
        long count = tagRepository.getCount();

        return getTagObjectListDTO(tags, paginator.getPage(size, (int)count, page));
    }

    @Override
    public Optional<TagDTO> getById(Integer id) {
        Optional<TagEntity> tag = tagRepository.getById(id);

        return tag.map(mapper::toTagDTO);
    }

    @Override
    public Optional<TagDTO> getMostWidelyUsedTagWithHighestCostOfAllUserOrders(Integer userId) {
        Optional<TagEntity> tag = tagRepository.getMostWidelyUsedTagWithHighestCostOfAllUserOrders(userId);

        return tag.map(mapper::toTagDTO);
    }

    @Override
    @Loggable
    @Transactional
    public Optional<TagDTO> create(TagPostDTO tagDTO) {
        Optional<TagEntity> existingTag = tagRepository.getByName(tagDTO.getName());

        if (existingTag.isPresent()) {
            return Optional.empty();
        }

        Optional<Integer> tagId = tagRepository.create(new TagEntity().setName(tagDTO.getName()));

        if (!tagId.isPresent()) {
            return Optional.empty();
        }

        return getById(tagId.get());
    }

    @Override
    @Loggable
    @Transactional
    public Optional<TagDTO> delete(Integer id) {
        Optional<TagEntity> deletedTag = tagRepository.getById(id);

        if (!deletedTag.isPresent()) {
            return Optional.empty();
        }

        int success = tagRepository.delete(id);

        return success == 1
                ? Optional.of(mapper.toTagDTO(deletedTag.get()))
                : Optional.empty();
    }

    @Override
    @Loggable
    @Transactional
    public Optional<TagDTO> delete(String name) {
        Optional<TagEntity> deletedTag = tagRepository.getByName(name);

        if (!deletedTag.isPresent()) {
            return Optional.empty();
        }

        int success = tagRepository.delete(name);

        return success == 1
                ? Optional.of(mapper.toTagDTO(deletedTag.get()))
                : Optional.empty();
    }

    @Override
    public ObjectListDTO<TagDTO> getTagsOfCertificate(Integer id, int page, int size) {
        List<TagEntity> tags = tagRepository.getCertificateTagsById(id, size, paginator.getOffset(page, size));
        long count = tagRepository.getCountByCertificateId(id);

        return getTagObjectListDTO(tags, paginator.getPage(size, (int)count, page));
    }

    private List<TagDTO> getTagDTOs(List<TagEntity> tags) {
        return tags.stream()
                .map(mapper::toTagDTO)
                .collect(Collectors.toList());
    }

    private ObjectListDTO<TagDTO> getTagObjectListDTO(List<TagEntity> tags, PageDTO pageDTO) {
        ObjectListDTO<TagDTO> objectListDTO = new ObjectListDTO<>();
        objectListDTO.setObjects(getTagDTOs(tags));
        objectListDTO.setPage(pageDTO);

        return objectListDTO;
    }

}
