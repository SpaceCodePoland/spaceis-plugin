package pl.spaceis.plugin.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PlatformDataProvider {

    Pattern VERSION_PATTERN = Pattern.compile("([0-9.]+)");

    String getVersion();

    String getEngineName();

    String getEngineVersion();

    static String matchVersion(final String version) {
        if (version == null || version.isEmpty() || version.trim().isEmpty()) {
            return "Unknown";
        }
        final Matcher matcher = VERSION_PATTERN.matcher(version);
        return matcher.find()
                ? matcher.group(1)
                : "Unknown";
    }

}
