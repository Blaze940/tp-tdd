package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;

import java.util.Optional;
import java.util.UUID;

public final class DrivingLicencePointsHandlerService {
        private final DrivingLicenceFinderService drivingLicenceFinderService;

        private static final int MIN_DRIVING_LICENCE_POINTS = 0 ;


        public DrivingLicencePointsHandlerService(DrivingLicenceFinderService drivingLicenceFinderService) {
            this.drivingLicenceFinderService = drivingLicenceFinderService;
        }

        private DrivingLicence adjustPoints(DrivingLicence drivingLicence, int pointsToSubstract) {
            int currentPoints = drivingLicence.getAvailablePoints();
            int plannedPoints = currentPoints - pointsToSubstract ;
            if (plannedPoints < MIN_DRIVING_LICENCE_POINTS) {
                return drivingLicence.withAvailablePoints(MIN_DRIVING_LICENCE_POINTS);
            } else {
                return drivingLicence.withAvailablePoints(plannedPoints);
            }
        }

        public DrivingLicence substractPoints(UUID drivingLicenceId , int pointsToSubstract) {
            Optional<DrivingLicence> drivingLicence = drivingLicenceFinderService.findById(drivingLicenceId);
            DrivingLicence drivingLicenceToHandle = drivingLicence.get();
            drivingLicenceToHandle = adjustPoints(drivingLicenceToHandle, pointsToSubstract);
            InMemoryDatabase.getInstance().save(drivingLicenceId, drivingLicenceToHandle);
            return drivingLicenceToHandle ;
        }

}
