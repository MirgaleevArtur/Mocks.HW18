package ru.netology;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

public class LocalizationServiceTest {

 LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

    static List<Arguments> localeData() {
        return Arrays.asList(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource("localeData")
    void shouldBeCorrectLocale(Country country, String message){
        assertThat(localizationService.locale(country), is(message));
    }
}
