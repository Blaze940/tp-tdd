package fr.esgi.cleancode.service;

import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrivingLicencePointsHandlerServiceTest {

    @InjectMocks
    private DrivingLicencePointsHandlerService drivingLicencePointsHandlerService;

    @Mock
    private DrivingLicenceFinderService service;

    private final int MAX_DRIVING_LICENCE_POINTS = 12;
    
    @Test
    void should_substract_points() {
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .availablePoints(MAX_DRIVING_LICENCE_POINTS).build();

        when(service.findById(id)).thenReturn(Optional.of(drivingLicence));

        final var newDrivingLicence = drivingLicencePointsHandlerService.substractPoints(id, 10);

        assertEquals(newDrivingLicence, drivingLicence.withAvailablePoints(2));
        assertNotEquals(newDrivingLicence, drivingLicence);

    }

    @Test
    void should_set_0_points_after_planned_points_are_too_much(){
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .availablePoints(5).build();
        final var pointToSubtract = 10;

        when(service.findById(id)).thenReturn(Optional.of(drivingLicence));

        final var newDrivingLicence = drivingLicencePointsHandlerService.substractPoints(id, pointToSubtract);

        int pointsNewDrivingLicence = newDrivingLicence.getAvailablePoints();
        int pointSubstracted = 5 - pointToSubtract;


        assertThat(newDrivingLicence).isNotNull();
        assertEquals(newDrivingLicence.getAvailablePoints(), drivingLicence.withAvailablePoints(0).getAvailablePoints());
        assertNotEquals(pointsNewDrivingLicence, pointSubstracted);

    }

}
