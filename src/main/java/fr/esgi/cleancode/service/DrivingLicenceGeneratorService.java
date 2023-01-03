package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;

public final class DrivingLicenceGeneratorService {
    private final DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;
    private final SocialSecurityCheckService socialSecurityCheckService;

    private static final int MAX_DRIVING_LICENCE_POINTS = 12 ;


    public DrivingLicenceGeneratorService(DrivingLicenceIdGenerationService drivingLicenceIdGenerationService, SocialSecurityCheckService socialSecurityCheckService) {
        this.socialSecurityCheckService = socialSecurityCheckService;
        this.drivingLicenceIdGenerationService = drivingLicenceIdGenerationService;
    }

    private DrivingLicence createDrivingLicence(String driverSocialSecurityNumber) {
        return  DrivingLicence.builder()
                .id(drivingLicenceIdGenerationService.generateNewDrivingLicenceId())
                .driverSocialSecurityNumber(driverSocialSecurityNumber)
                .availablePoints(MAX_DRIVING_LICENCE_POINTS)
                .build();
    }

     private void saveDrivingLicenceInDatabase(DrivingLicence drivingLicenceCreated) {
        try{
            InMemoryDatabase.getInstance().save(drivingLicenceCreated.getId(), drivingLicenceCreated) ;
        }catch (Exception error){
            error.printStackTrace();
        }
    }

    //Poser question, a t'on le droit de faire appel à save et isDriverSocialSecurityNumberOk dans cette méthode ?
    //CreateSaveAndReturn ??
    public DrivingLicence saveDrivingLicence(String driverSocialSecurityNumber) {
        if (socialSecurityCheckService.isValid(driverSocialSecurityNumber)) {
                DrivingLicence drivingLicenceCreated = createDrivingLicence(driverSocialSecurityNumber) ;
                saveDrivingLicenceInDatabase(drivingLicenceCreated);
                return drivingLicenceCreated;
        }
        return null;
    }


   /* public DrivingLicence getCreatedDrivingLicence() {
        return drivingLicenceCreated ;
    }*/

}
