package fr.esgi.cleancode.service;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SocialSecurityCheckServiceTest {

    @InjectMocks
    private SocialSecurityCheckService socialSecurityCheckService;

    @Test
    void security_number_should_return_throw_at_wrong_instance(){
        assertThrows(InvalidDriverSocialSecurityNumberException.class, () ->
                socialSecurityCheckService.isValid("12345678901234")
        );
    }

    @Test
    void security_number_should_not_be_empty(){
        final InvalidDriverSocialSecurityNumberException thrown = assertThrows(InvalidDriverSocialSecurityNumberException.class,
                () -> socialSecurityCheckService.isValid("4554554656"));
        assertNotEquals("Social security number shouldn't be empty", thrown.getMessage());
    }

    @Test
    void security_number_should_contains_15_characters(){
        final String numberSocialSecurity = "12345678901234a";
        final InvalidDriverSocialSecurityNumberException thrown = assertThrows(InvalidDriverSocialSecurityNumberException.class, () -> socialSecurityCheckService.isValid(numberSocialSecurity));
        assertNotEquals("Social security number shouldn't be empty", thrown.getMessage());
        assertNotEquals("The social security number must have 15 digits", thrown.getMessage());
        assertEquals("The social security number must be made by only digits", thrown.getMessage());
    }

    @Test
    void security_number_should_be_ok(){
        final String numberSocialSecurity = "123456789012345";
        assertDoesNotThrow(() -> {
            socialSecurityCheckService.isValid(numberSocialSecurity);
        });
    }

}
