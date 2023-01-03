package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceGeneratorServiceTest {

    @InjectMocks
    private DrivingLicenceGeneratorService licenceGeneratorService;

    @Mock
    private DrivingLicenceIdGenerationService idGenerationService;

    @Mock
    private SocialSecurityCheckService socialSecurityCheckService;

    @Mock
    private InMemoryDatabase database;


    @Test
    void saveDrivingLicence() {
        final var securitySocialNumber = "123456789012345";
        final var randomUUID = UUID.randomUUID();

        when(idGenerationService.generateNewDrivingLicenceId()).thenReturn(randomUUID);

        if (securitySocialNumber.length() > 15){
            when(socialSecurityCheckService.isValid(securitySocialNumber)).thenThrow(InvalidDriverSocialSecurityNumberException.class);
            assertThrows(InvalidDriverSocialSecurityNumberException.class, () -> {
                socialSecurityCheckService.isValid(securitySocialNumber);
            });
        }else{
            when(socialSecurityCheckService.isValid(securitySocialNumber)).thenReturn(true);
            assertDoesNotThrow(() -> {
                socialSecurityCheckService.isValid(securitySocialNumber);
            });
        }

        var licence = licenceGeneratorService.saveDrivingLicence(securitySocialNumber);


        assertThat(licence).isNotNull();
        when(database.findById(licence.getId())).thenReturn(Optional.of(licence));

        final var licenceGenerated = database.findById(licence.getId());

        assertThat(licenceGenerated).containsSame(licence);
        verify(database).findById(licence.getId());
        verifyNoMoreInteractions(database);

    }

}
