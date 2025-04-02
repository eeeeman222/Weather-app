package org.example.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.mountebank.Mountebank;
import com.github.tomakehurst.mountebank.MountebankException;
import com.github.tomakehurst.mountebank.config.MountebankConfiguration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MountebankTestUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Mountebank mb;
    private static int mbPort = 2525;

    public static void startMountebank() throws Exception {
        Path configPath = Paths.get("src", "test", "resources", "mb-config.json").toAbsolutePath();

        // Валидация конфига
        if (!mapper.readTree(new File(configPath.toString())).has("imposters")) {
            throw new IllegalStateException("Invalid Mountebank config format");
        }

        mb = Mountebank.builder()
                .withConfiguration(MountebankConfiguration.fromCommandLineArgs(
                        "--configfile", configPath.toString(),
                        "--port", String.valueOf(mbPort),
                        "--loglevel", "info",
                        "--mock"
                ))
                .build();

        mb.start();
        waitForStartup();
    }

    private static void waitForStartup() throws InterruptedException {
        int retries = 3;
        while (retries-- > 0) {
            try {
                if (mb.isRunning()) return;
                Thread.sleep(1000);
            } catch (MountebankException e) {
                Thread.sleep(1000);
            }
        }
        throw new IllegalStateException("Mountebank failed to start");
    }

    public static void stopMountebank() {
        if (mb != null && mb.isRunning()) {
            try {
                mb.stop();
            } catch (MountebankException e) {
                System.err.println("Error stopping Mountebank: " + e.getMessage());
            }
        }
    }

    public static int getPort() {
        return mbPort;
    }
}