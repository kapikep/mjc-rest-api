package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.dtoFactory.DtoFactory;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.util.CriteriaUtil;
import com.epam.esm.service.util.GiftCertificateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.service.dtoFactory.DtoFactory.getNewCriteriaDtoWithDefaultVal;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getGiftCertificateDtoId1;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDto;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoId1;
import static com.epam.esm.service.dtoFactory.GiftCertificateDtoFactory.getNewGiftCertificateDtoList;
import static com.epam.esm.service.entityFactory.EntityFactory.getNewCriteriaEntityWithDefaultVal;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getGiftCertificateEntityId1;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntity;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityId1;
import static com.epam.esm.service.entityFactory.GiftCertificateEntityFactory.getNewGiftCertificateEntityList;
import static com.epam.esm.service.impl.GiftCertificateServiceImpl.CANNOT_ADD_OR_UPDATE_A_CHILD_ROW;
import static com.epam.esm.service.impl.GiftCertificateServiceImpl.CREATE_PROBLEM;
import static com.epam.esm.service.impl.GiftCertificateServiceImpl.INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0;
import static com.epam.esm.service.impl.GiftCertificateServiceImpl.RESOURCE_NOT_FOUND;
import static com.epam.esm.service.impl.GiftCertificateServiceImpl.UPDATE_PROBLEM;
import static com.epam.esm.service.util.CriteriaUtil.criteriaDtoToEntityConverting;
import static com.epam.esm.service.util.CriteriaUtil.setDefaultPageValIfEmpty;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateDtoToEntityConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.giftCertificateEntityListToDtoConverting;
import static com.epam.esm.service.util.GiftCertificateUtil.updateFieldsInDtoFromEntity;
import static com.epam.esm.service.util.GiftCertificateUtil.updateNonNullFieldsFromDtoToEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    public static final String MESSAGE = "message";
    @Mock
    GiftCertificateRepository giftRepository;

    @Mock
    TagService tagService;

    @InjectMocks
    GiftCertificateServiceImpl giftService;

    @Test
    void readGiftCertificatePaginatedTest() throws ServiceException, RepositoryException, ValidateException {
        CriteriaEntity crEntity = getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = DtoFactory.getNewCriteriaDtoWithDefaultVal();
        List<GiftCertificateEntity> giftEntityList = getNewGiftCertificateEntityList();
        List<GiftCertificateDto> giftDtoList = getNewGiftCertificateDtoList();
        List<GiftCertificateDto> actualDtoList;
        long totalSize = giftEntityList.size();

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<GiftCertificateUtil> giftUtil = Mockito.mockStatic(GiftCertificateUtil.class)) {

            when(giftRepository.readPaginated(crEntity)).thenReturn(giftEntityList);
            giftUtil.when(() -> giftCertificateEntityListToDtoConverting(giftEntityList))
                    .thenReturn(giftDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            crEntity.setTotalSize(totalSize);
            actualDtoList = giftService.readGiftCertificatesPaginated(crDto);

            verify(giftRepository).readPaginated(crEntity);
            crUtil.verify(() -> criteriaDtoToEntityConverting(crDto));
            crUtil.verify(() -> setDefaultPageValIfEmpty(crDto));
            giftUtil.verify(() -> GiftCertificateUtil.sortingValidation(crDto));
            assertEquals(giftDtoList, actualDtoList);
            assertEquals(totalSize, crDto.getTotalSize());
        }
    }

    @Test
    void readGiftCertificatePaginatedWithExceptionTest() throws RepositoryException {
        CriteriaEntity crEntity = getNewCriteriaEntityWithDefaultVal();
        CriteriaDto crDto = DtoFactory.getNewCriteriaDtoWithDefaultVal();
        List<GiftCertificateEntity> giftEntityList = getNewGiftCertificateEntityList();
        List<GiftCertificateDto> giftDtoList = getNewGiftCertificateDtoList();

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<GiftCertificateUtil> giftUtil = Mockito.mockStatic(GiftCertificateUtil.class)) {

            when(giftRepository.readPaginated(crEntity)).thenThrow(new RepositoryException(MESSAGE));
            giftUtil.when(() -> giftCertificateEntityListToDtoConverting(giftEntityList))
                    .thenReturn(giftDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            ServiceException e = assertThrows(ServiceException.class,
                    () -> giftService.readGiftCertificatesPaginated(crDto));

            assertEquals(MESSAGE, e.getMessage());
        }
    }

    @Test
    void readGiftCertificateByIdTest() throws RepositoryException, ServiceException {
        when(giftRepository.readById(anyLong()))
                .thenReturn(getNewGiftCertificateEntityId1()).thenThrow(new RepositoryException());

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class)) {
            util.when(() -> GiftCertificateUtil.giftCertificateEntityToDtoConverting(getGiftCertificateEntityId1()))
                    .thenReturn(getNewGiftCertificateDtoId1());
            GiftCertificateDto actualDto = giftService.readGiftCertificateById(1);

            assertEquals(getGiftCertificateDtoId1(), actualDto);
        }
        assertThrows(ServiceException.class, () -> giftService.readGiftCertificateById(222));
    }


    @Test
    void findGiftCertificatesAllOkTest() throws RepositoryException, ValidateException, ServiceException {
        List<GiftCertificateEntity> giftEntityList = getNewGiftCertificateEntityList();
        List<GiftCertificateDto> giftDtoList = getNewGiftCertificateDtoList();
        List<GiftCertificateDto> actualDtoList;
        long totalSize = giftEntityList.size();
        CriteriaDto crDto = getNewCriteriaDtoWithDefaultVal();
        CriteriaEntity crEntity = getNewCriteriaEntityWithDefaultVal();
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(SearchParam.GIFT_SEARCH_BY_TAG_NAME, "tag");
        crDto.setSearchParam(criteriaMap);
        crEntity.setSearchParam(criteriaMap);

        try (MockedStatic<CriteriaUtil> crUtil = Mockito.mockStatic(CriteriaUtil.class);
             MockedStatic<GiftCertificateUtil> giftUtil = Mockito.mockStatic(GiftCertificateUtil.class)) {

            when(giftRepository.findByCriteria(crEntity)).thenReturn(giftEntityList)
                    .thenThrow(new RepositoryException(MESSAGE));
            giftUtil.when(() -> giftCertificateEntityListToDtoConverting(giftEntityList))
                    .thenReturn(giftDtoList);
            crUtil.when(() -> criteriaDtoToEntityConverting(crDto)).thenReturn(crEntity);

            crEntity.setTotalSize(totalSize);
            actualDtoList = giftService.findGiftCertificatesByCriteria(crDto);

            verify(giftRepository).findByCriteria(crEntity);
            crUtil.verify(() -> criteriaDtoToEntityConverting(crDto));
            crUtil.verify(() -> setDefaultPageValIfEmpty(crDto));
            giftUtil.verify(() -> GiftCertificateUtil.sortingValidation(crDto));
            assertEquals(giftDtoList, actualDtoList);
            assertEquals(totalSize, crDto.getTotalSize());

            ServiceException e = assertThrows(ServiceException.class,
                    () -> giftService.findGiftCertificatesByCriteria(crDto));
            assertEquals(MESSAGE, e.getMessage());
        }
    }

    @Test
    void createGiftCertificateTest() throws RepositoryException, ValidateException, ServiceException {
        GiftCertificateDto newDto = getNewGiftCertificateDto();
        GiftCertificateEntity newEntity = getNewGiftCertificateEntity();

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class)) {
            util.when(() -> giftCertificateDtoToEntityConverting(newDto))
                    .thenReturn(newEntity);

            giftService.createGiftCertificate(newDto);

            util.verify(() -> updateFieldsInDtoFromEntity(newEntity, newDto));
            verify(tagService).setIdOrCreateTags(newDto.getTags());
            verify(giftRepository).create(newEntity);
        }
    }

    @Test
    void createGiftCertificateWithExceptionTest() throws RepositoryException {
        GiftCertificateDto newDto = getNewGiftCertificateDto();
        GiftCertificateEntity newEntity = getNewGiftCertificateEntity();

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class)) {
            util.when(() -> giftCertificateDtoToEntityConverting(newDto))
                    .thenReturn(newEntity);

            doThrow(new RepositoryException(CANNOT_ADD_OR_UPDATE_A_CHILD_ROW))
                    .doThrow(new RepositoryException(MESSAGE))
                    .when(giftRepository).create(newEntity);

            ServiceException e = assertThrows(ServiceException.class,
                    () -> giftService.createGiftCertificate(newDto));
            assertEquals(CREATE_PROBLEM, e.getResourceBundleCode());

            e = assertThrows(ServiceException.class,
                    () -> giftService.createGiftCertificate(newDto));
            assertEquals(MESSAGE, e.getMessage());
        }
    }

    @Test
    void updateGiftCertificateAllFieldsNotNullTest() throws ValidateException, ServiceException, RepositoryException {
        GiftCertificateDto newDto = getNewGiftCertificateDto();
        newDto.setId(1);
        GiftCertificateEntity entityFromDb = getNewGiftCertificateEntityId1();

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class)) {
            when(giftRepository.readById(anyLong())).thenReturn(entityFromDb);

            giftService.updateGiftCertificate(newDto);

            verify(giftRepository).readById(newDto.getId());
            verify(tagService).setIdOrCreateTags(newDto.getTags());
            util.verify(() -> updateNonNullFieldsFromDtoToEntity(newDto, entityFromDb));
            util.verify(() -> updateFieldsInDtoFromEntity(entityFromDb, newDto));
        }
    }

    @Test
    void updateGiftCertificateWithExceptionTest() throws RepositoryException {
        when(giftRepository.readById(anyLong()))
                .thenThrow(new RepositoryException(INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0))
                .thenThrow(new RepositoryException(CANNOT_ADD_OR_UPDATE_A_CHILD_ROW))
                .thenThrow(new RepositoryException(MESSAGE));


        ServiceException e = assertThrows(ServiceException.class,
                () -> giftService.updateGiftCertificate(getNewGiftCertificateDtoId1()));
        assertEquals(RESOURCE_NOT_FOUND, e.getResourceBundleCode());

        e = assertThrows(ServiceException.class,
                () -> giftService.updateGiftCertificate(getNewGiftCertificateDtoId1()));
        assertEquals(UPDATE_PROBLEM, e.getResourceBundleCode());

        e = assertThrows(ServiceException.class,
                () -> giftService.updateGiftCertificate(getNewGiftCertificateDtoId1()));
        assertEquals(MESSAGE, e.getMessage());
    }

    @Test
    void deleteGiftCertificateTest() throws ServiceException, RepositoryException {
        giftService.deleteGiftCertificateById(1);
        verify(giftRepository).deleteById(1);
    }

    @Test
    void deleteGiftCertificateWithExceptionTest() throws RepositoryException {
        doThrow(new RepositoryException()).when(giftRepository).deleteById(anyLong());

        ServiceException e = assertThrows(ServiceException.class,
                () -> giftService.deleteGiftCertificateById(1));
        assertEquals(RESOURCE_NOT_FOUND, e.getResourceBundleCode());
    }
}