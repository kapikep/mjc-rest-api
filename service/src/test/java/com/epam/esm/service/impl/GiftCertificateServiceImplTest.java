package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.constant.GiftCertificateSearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.utils.GiftCertificateUtil;
import com.epam.esm.service.utils.ServiceUtil;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)

class GiftCertificateServiceImplTest {

    @Mock
    GiftCertificateRepository repository;

    @Mock
    TagService tagService;

    @InjectMocks
    GiftCertificateServiceImpl service;

    GiftCertificateEntity gift1;
    GiftCertificateEntity gift2;
    List<GiftCertificateEntity> entityList;
    List<TagEntity> tagListGift1;
    List<TagEntity> tagListGift2;
    List<GiftCertificateDto> dtoList;

    @BeforeEach
    void init() {
        gift1 = getEntityId1();
        gift2 = getEntityId2();

        entityList = getEntityList();

        dtoList = getDtoList();
    }

    @Test
    void readAllGiftCertificates() throws RepositoryException, ServiceException {
        when(repository.readAllGiftCertificates()).thenReturn(entityList).thenThrow(new RepositoryException());
        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class)) {
            util.when(() -> GiftCertificateUtil.giftCertificateEntityListToDtoConverting(entityList))
                    .thenReturn(dtoList);
            List<GiftCertificateDto> actualGiftList = service.readAllGiftCertificates();
            assertEquals(dtoList, actualGiftList);
        }
        assertThrows(ServiceException.class, () -> service.readAllGiftCertificates());
    }

    @Test
    void testReadGiftCertificateStr() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readGiftCertificate(anyInt())).thenReturn(getEntityId1()).thenThrow(new RepositoryException());

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<ServiceUtil> servUtil = Mockito.mockStatic(ServiceUtil.class)) {
            servUtil.when(() -> ServiceUtil.parseInt(anyString())).thenReturn(1);
            util.when(() -> GiftCertificateUtil.giftCertificateEntityToDtoTransfer(getEntityId1()))
                    .thenReturn(getDtoId1());
            GiftCertificateDto actualDto = service.readGiftCertificate("1");
            assertEquals(dtoList.get(0), actualDto);
        }
        assertThrows(ServiceException.class, () -> service.readGiftCertificate("2"));
        assertThrows(ServiceException.class, () -> service.readGiftCertificate("2"));
    }

    @Test
    void testReadGiftCertificateInt() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readGiftCertificate(anyInt())).thenReturn(getEntityId1()).thenThrow(new RepositoryException());

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
            validator.when(() -> GiftCertificateValidator.idValidation(1)).thenReturn(true);
            util.when(() -> GiftCertificateUtil.giftCertificateEntityToDtoTransfer(getEntityId1()))
                    .thenReturn(getDtoId1());
            GiftCertificateDto actualDto = service.readGiftCertificate(1);
            assertEquals(getDtoId1(), actualDto);
        }
        assertThrows(ServiceException.class, () -> service.readGiftCertificate(2));
        assertThrows(ServiceException.class, () -> service.readGiftCertificate(2));
    }

    @Test
    void testReadGiftCertificateIntWrongId(){

        try (MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
            validator.when(() -> GiftCertificateValidator.idValidation(-1)).thenReturn(false);

            assertThrows(ValidateException.class, () -> service.readGiftCertificate(-1));
        }
    }

    @Test
    void testReadGiftCertificateThrowValidateException(){
        try (MockedStatic<ServiceUtil> servUtil = Mockito.mockStatic(ServiceUtil.class)) {
            servUtil.when(() -> ServiceUtil.parseInt(anyString())).thenThrow(new ValidateException());

            assertThrows(ValidateException.class, () -> service.readGiftCertificate("abc"));
        }
    }

    @Test
    void createGiftCertificate() throws RepositoryException, ValidateException, ServiceException {
        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {

            util.when(() -> GiftCertificateUtil.giftCertificateDtoToEntityTransfer(dtoList.get(0)))
                    .thenReturn(gift1);

            service.createGiftCertificate(dtoList.get(0));

            verify(repository, times(1)).createGiftCertificate(gift1);
        }
    }

    @Test
    void createGiftCertificateNoDates() throws RepositoryException, ValidateException, ServiceException {
        GiftCertificateDto dto = getDtoId1();
        dto.setCreateDate(null);
        dto.setLastUpdateDate(null);

        LocalDateTime timeNow = LocalDateTime.now();
        GiftCertificateEntity entity;
        GiftCertificateUtil util = spy(new GiftCertificateUtil());

        service.createGiftCertificate(dto);
        entity = util.giftCertificateDtoToEntityTransfer(dto);
        assertEquals(timeNow.getMonthValue(), entity.getCreateDate().getMonthValue());
        assertEquals(timeNow.getMonthValue(), entity.getLastUpdateDate().getMonthValue());

        verify(repository, times(1)).createGiftCertificate(entity);
    }


    @Test
    void updateGiftCertificateAllFieldsNotNull() throws ValidateException, ServiceException, RepositoryException {
        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {

            validator.when(() -> GiftCertificateValidator.allNotNullFieldValidation(dtoList.get(0)))
                    .thenReturn(true);

            util.when(() -> GiftCertificateUtil.giftCertificateDtoToEntityTransfer(dtoList.get(0)))
                    .thenReturn(gift1);

            service.updateGiftCertificate(dtoList.get(0));

            verify(repository, times(1)).updateGiftCertificate(gift1);
        }
    }

//    @Test
//    void updateGiftCertificateHaveNullFields() throws ValidateException, ServiceException, RepositoryException {
//        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
//             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
//
//            validator.when(() -> GiftCertificateValidator.allNotNullFieldValidation(dtoList.get(0)))
//                    .thenReturn(false);
//
//            validator.when(() -> GiftCertificateValidator.idValidation(1))
//                    .thenReturn(false);
//
//            when(service.readGiftCertificate(1)).thenReturn(dtoList.get(0));
//
//            util.when(() -> GiftCertificateUtil.giftCertificateDtoToEntityTransfer(dtoList.get(0)))
//                    .thenReturn(gift1);
//
//            service.updateGiftCertificate(dtoList.get(0));
//
//            verify(repository, times(1)).updateGiftCertificate(gift1);
//        }
//    }

    @Test
    void deleteGiftCertificate() throws RepositoryException, ValidateException, ServiceException {
        try (MockedStatic<ServiceUtil> util = Mockito.mockStatic(ServiceUtil.class)){
            util.when(() -> ServiceUtil.parseInt("1")).thenReturn(1);
            service.deleteGiftCertificate("1");
            verify(repository, times(1)).deleteGiftCertificate(1);
        }
    }

    @Test
    void findGiftCertificatesAllOk() throws RepositoryException, ValidateException, ServiceException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, "tag");
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, "name");
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_DESCRIPTION, "description");
        String sorting = "-name";

        when(repository.findGiftCertificate(criteriaMap, sorting)).thenReturn(entityList);
        service.findGiftCertificates(criteriaMap, sorting);
        verify(repository, times(1)).findGiftCertificate(criteriaMap, sorting);
    }

    @Test
    void findGiftCertificatesIncorrectParam() throws RepositoryException, ValidateException, ServiceException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, "tag");
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, "name");
        criteriaMap.put("qqeqe", "description");

        try (MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
            validator.when(() -> GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, null))
                    .thenThrow(ValidateException.class);
        }

        verify(repository, never()).findGiftCertificate(criteriaMap, null);
        assertThrows(ValidateException.class, () -> service.findGiftCertificates(criteriaMap, null));
    }

    private GiftCertificateEntity getEntityId1() {
        return new GiftCertificateEntity(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagEntityId1(), getTagEntityId2(), getTagEntityId7()).collect(Collectors.toList()));
    }

    private GiftCertificateEntity getEntityId2() {
        return new GiftCertificateEntity(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagEntityId5()).collect(Collectors.toList()));
    }

    private GiftCertificateEntity getEntityId4() {
        return new GiftCertificateEntity(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagEntityId5(), getTagEntityId7()).collect(Collectors.toList()));
    }

    private GiftCertificateDto getDtoId1() {
        return new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagDtoId1(), getTagDtoId2(), getTagDtoId7()).collect(Collectors.toList()));
    }

    private GiftCertificateDto getDtoId2() {
        return new GiftCertificateDto(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagDtoId5()).collect(Collectors.toList()));
    }

    private GiftCertificateDto getDtoId4() {
        return new GiftCertificateDto(4, "Bowling for the company",
                "Bowling will be an excellent option for outdoor activities for a large company", 45.0, 60, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), Stream.of(getTagDtoId5(), getTagDtoId7()).collect(Collectors.toList()));
    }

    private List<GiftCertificateEntity> getEntityList() {
        List<GiftCertificateEntity> entityList = new ArrayList<>();
        entityList.add(getEntityId1());
        entityList.add(getEntityId2());
        entityList.add(getEntityId4());
        return entityList;
    }

    private List<GiftCertificateDto> getDtoList(){
        List<GiftCertificateDto> dtoList = new ArrayList<>();
        dtoList.add(getDtoId1());
        dtoList.add(getDtoId2());
        dtoList.add(getDtoId4());
        return dtoList;
    }

    private TagEntity getTagEntityId1() {
        return new TagEntity(1, "Sport");
    }

    private TagEntity getTagEntityId2() {
        return new TagEntity(2, "Water");
    }

    private TagEntity getTagEntityId5() {
        return new TagEntity(5, "Auto");
    }

    private TagEntity getTagEntityId7() {
        return new TagEntity(7, "Health");
    }

    private TagDto getTagDtoId1() {
        return new TagDto(1, "Sport");
    }

    private TagDto getTagDtoId2() {
        return new TagDto(2, "Water");
    }

    private TagDto getTagDtoId5() {
        return new TagDto(5, "Auto");
    }

    private TagDto getTagDtoId7() {
        return new TagDto(7, "Health");
    }

}