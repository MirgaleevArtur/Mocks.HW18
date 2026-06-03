package ru.netology.sender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderTest {

    private MessageSender messageSender;
    Map<String, String> headers = new HashMap<>();

    @BeforeEach
    void setUp() {
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp(GeoServiceImpl.MOSCOW_IP))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(geoServiceMock.byIp(GeoServiceImpl.NEW_YORK_IP))
                .thenReturn(new Location("New York", Country.USA, "10th Avenue", 32));

        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationServiceMock.locale(Country.USA))
                .thenReturn("Welcome");

        messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        headers.clear();
    }


    @Test
    void shouldBeRussianMessage_whenIpIsRussian() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);

        assertEquals("Добро пожаловать", messageSender.send(headers));

    }

    @Test
    void shouldBeEnglishMessage_whenIpIsEnglish() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP);

        assertEquals("Welcome", messageSender.send(headers));
    }
}
