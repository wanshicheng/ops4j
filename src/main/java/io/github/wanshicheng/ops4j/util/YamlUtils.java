package io.github.wanshicheng.ops4j.util;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class YamlUtils {
    public static <T> T load(String path, Class<T> type) throws IOException {
        Yaml yaml = new Yaml();
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return yaml.loadAs(new String(bytes, StandardCharsets.UTF_8), type);
    }
}
