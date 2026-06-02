package ru.netology;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GeoServiceTest {
    public final GeoServiceImpl geoService = new GeoServiceImpl();

    static List<Arguments> geoData() {
        return Arrays.asList(
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, "10th Avenue", 32)),
                Arguments.of("172.44.183.149", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.0.32.11", new Location("New York", Country.USA, null, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("geoData")
    void shouldBeCorrectLocation_withIp(String ip, Location expectedLocation) {
        Location actualLocation = geoService.byIp(ip);
        assertThat(actualLocation, is(expectedLocation));
    }

    @Test
    void shouldBeAnException() {
        assertThatThrownBy(() -> geoService.byCoordinates(123, 456))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Not implemented");
    }
}
