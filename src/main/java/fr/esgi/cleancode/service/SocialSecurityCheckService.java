package fr.esgi.cleancode.service;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;


public final class SocialSecurityCheckService {
    private static final int NUMBER_OF_DIGITS_IN_SOCIAL_SECURITY_NUMBER = 15;

    //Controls if the social security number is valid
    private boolean isMadeByOnlyDigits(String driverSocialSecurityNumber) {
        if (driverSocialSecurityNumber == null) {
            return false;
        }
        int length = driverSocialSecurityNumber.length();
        if (length == 0) {
            return false;
        }
        for (int index = 0; index < length; index++) {
            char myChar = driverSocialSecurityNumber.charAt(index);
            if (myChar < '0' || myChar > '9') {
                return false;
            }
        }
        return true;

    }
    private boolean hasGoodLength(String driverSocialSecurityNumber) {
        if (driverSocialSecurityNumber == null) {
            return false;
        }
        return driverSocialSecurityNumber.length() == NUMBER_OF_DIGITS_IN_SOCIAL_SECURITY_NUMBER;
    }

    private boolean isEmpty(String driverSocialSecurityNumber) {
        return driverSocialSecurityNumber == null || driverSocialSecurityNumber.isBlank() ;
    }
    public boolean isValid(String driverSocialSecurityNumber) {

        if(!isEmpty(driverSocialSecurityNumber)) {
            throw new InvalidDriverSocialSecurityNumberException("Social security number shouldn't be empty");
        }
        if(!hasGoodLength(driverSocialSecurityNumber)) {
            throw new InvalidDriverSocialSecurityNumberException("The social security number must have 15 digits");
        }
        if(!isMadeByOnlyDigits(driverSocialSecurityNumber)) {
            throw new InvalidDriverSocialSecurityNumberException("The social security number must be made by only digits");
        }
        return true ;
    }


}
