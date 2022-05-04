package com.epam.esm.service;

import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.entity.TagEntity;
import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import com.epam.esm.service.dto.tag.TagPostDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.pagination.Paginator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.epam.esm.service.utils.TagServiceTestUtils.getTagDTOs;
import static com.epam.esm.service.utils.TagServiceTestUtils.getTags;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;
    @Spy
    private TagMapper tagMapper;
    @Spy
    private Paginator paginator;

    private TagService tagService;

    private final static List<TagEntity> testTags = getTags();
    private final static List<TagDTO> testTagsDTOs = getTagDTOs();

    @BeforeEach
    public void setUp() {
        tagService = new TagServiceImpl(tagRepository, tagMapper, paginator);
    }

    @Test
    public void testGetAll_shouldDelegateGettingToRepositoryIfTagsExist() {
        List<TagDTO> expectedTags = testTagsDTOs.subList(0, 6);
        PageDTO expectedPage = new PageDTO().setSize(6).setTotalElements(12).setTotalPages(2).setNumber(1);

        when(tagRepository.getAll(6, 0)).thenReturn(testTags.subList(0, 6));
        when(tagRepository.getCount()).thenReturn(12);

        ObjectListDTO<TagDTO> tags = tagService.getAll(1, 6);

        verify(tagRepository).getAll(6, 0);
        verify(tagRepository).getCount();
        verify(paginator).getPage(6, 12, 1);
        IntStream.range(0, 6).forEach(i -> verify(tagMapper).toTagDTO(testTags.get(i)));

        verifyNoMoreInteractions(tagRepository);
        verifyNoMoreInteractions(tagMapper);

        assertThat(tags.getObjects().size(), is(6));
        assertThat(tags.getObjects(), is(equalTo(expectedTags)));
        assertThat(tags.getPage(), is(equalTo(expectedPage)));
    }

    @Test
    public void testGetById_shouldDelegateGettingToRepositoryIfTagFoundById() {
        int id = 1;
        TagDTO expectedTags = testTagsDTOs.get(0);
        when(tagRepository.getById(id)).thenReturn(Optional.of(testTags.get(0)));

        Optional<TagDTO> tags = tagService.getById(id);

        verify(tagRepository).getById(id);
        verify(tagMapper).toTagDTO(testTags.get(0));

        verifyNoMoreInteractions(tagRepository);
        verifyNoMoreInteractions(tagMapper);

        assertTrue(tags.isPresent());
        assertThat(tags.get().getName(), is("game"));
        assertThat(tags.get(), is(equalTo(expectedTags)));
    }

    @Test
    public void testCreate_shouldDelegateCreationToRepositoryIfCreationIsSuccessful() {
        String tagName = "test";
        int tagId = 13;
        TagDTO expectedTag = new TagDTO().setName(tagName);
        TagEntity createTag = new TagEntity().setName(tagName);
        TagEntity tag = new TagEntity().setId(tagId).setName(tagName);

        TagPostDTO createTagDTO = new TagPostDTO().setName(tagName);

        when(tagRepository.getByName(tagName)).thenReturn(Optional.empty());
        when(tagRepository.create(createTag)).thenReturn(Optional.of(tagId));
        when(tagRepository.getById(tagId)).thenReturn(Optional.of(tag));

        Optional<TagDTO> tags = tagService.create(createTagDTO);

        verify(tagRepository).getByName(tagName);
        verify(tagRepository).create(createTag);
        verify(tagRepository).getById(tagId);
        verify(tagMapper).toTagDTO(tag);

        verifyNoMoreInteractions(tagRepository);
        verifyNoMoreInteractions(tagMapper);

        assertTrue(tags.isPresent());
        assertThat(tags.get().getName(), is(tagName));
        assertThat(tags.get(), is(equalTo(expectedTag)));
    }

    @Test
    public void testDeleteById_shouldDelegateDeletionToRepositoryIfTagFoundById() {
        int id = 1;
        TagDTO expectedTag = testTagsDTOs.get(0);

        when(tagRepository.getById(id)).thenReturn(Optional.of(testTags.get(0)));
        when(tagRepository.delete(id)).thenReturn(1);

        Optional<TagDTO> tags = tagService.delete(id);

        verify(tagRepository).getById(id);
        verify(tagRepository).delete(id);
        verify(tagMapper).toTagDTO(testTags.get(0));
        verifyNoMoreInteractions(tagRepository);
        verifyNoMoreInteractions(tagMapper);

        assertTrue(tags.isPresent());
        assertThat(tags.get().getName(), is("game"));
        assertThat(tags.get(), is(equalTo(expectedTag)));
    }

    @Test
    public void testDeleteByName_shouldDelegateDeletionToRepositoryIfTagFoundByName() {
        String name = "game";
        TagDTO expectedTag = testTagsDTOs.get(0);

        when(tagRepository.getByName(name)).thenReturn(Optional.of(testTags.get(0)));
        when(tagRepository.delete(name)).thenReturn(1);

        Optional<TagDTO> tag = tagService.delete(name);

        verify(tagRepository).getByName(name);
        verify(tagRepository).delete(name);
        verify(tagMapper).toTagDTO(testTags.get(0));
        verifyNoMoreInteractions(tagRepository);
        verifyNoMoreInteractions(tagMapper);

        assertTrue(tag.isPresent());
        assertThat(tag.get().getName(), is("game"));
        assertThat(tag.get(), is(equalTo(expectedTag)));
    }

}
