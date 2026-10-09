package com.biel.lobby.utilities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class GestorPropietatsEncodingTest {
    @TempDir Path directory;

    @Test
    void readsLegacyDominionNamesAndPreservesThemWhenUpdatingAnotherProperty() throws Exception {
        Path propertiesFile = directory.resolve("pMapaActual.txt");
        Files.writeString(propertiesFile,
                "PointNames=Mina,Ràdio,Magatzem,Fàbrica,Pedrera\r\nHillName=Turó\r\nCaptureRate=1\r\n",
                Charset.forName("windows-1252"));
        GestorPropietats properties = new GestorPropietats(propertiesFile.toString());

        assertArrayEquals(new String[] {"Mina", "Ràdio", "Magatzem", "Fàbrica", "Pedrera"},
                properties.ObtenirLlista("PointNames"));
        assertEquals("Turó", properties.ObtenirPropietat("HillName"));
        properties.EstablirPropietat("CaptureRate", 2);

        assertEquals("PointNames=Mina,Ràdio,Magatzem,Fàbrica,Pedrera\nHillName=Turó\nCaptureRate=2\n",
                Files.readString(propertiesFile, StandardCharsets.UTF_8).replace("\r\n", "\n"));
        assertEquals("Ràdio", properties.ObtenirLlista("PointNames")[1]);
    }

    @Test
    void readsAndWritesUtf8WithoutChangingAccents() throws Exception {
        Path propertiesFile = directory.resolve("pMapaActual.txt");
        Files.writeString(propertiesFile, "PointNames=Ràdio,Fàbrica,Turó\nBonus=Visió\n",
                StandardCharsets.UTF_8);
        GestorPropietats properties = new GestorPropietats(propertiesFile.toString());

        assertEquals("Visió", properties.ObtenirPropietat("Bonus"));
        properties.EstablirPropietat("PointNames", "Ràdio,Fàbrica,Turó,Estació");
        assertArrayEquals(new String[] {"Ràdio", "Fàbrica", "Turó", "Estació"},
                properties.ObtenirLlista("PointNames"));
        assertEquals("PointNames=Ràdio,Fàbrica,Turó,Estació\nBonus=Visió\n",
                Files.readString(propertiesFile, StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }
}
