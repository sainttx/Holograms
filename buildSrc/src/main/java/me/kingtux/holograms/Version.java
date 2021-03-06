package me.kingtux.holograms;

import org.gradle.api.GradleException;

import java.io.IOException;
import java.util.List;

public class Version {
    public static final String HOLOGRAMS_VERSION = "1.0-SNAPSHOT";
    public static final List<String> branchBlacklist = List.of("master", "active");

    /**
     * Generates the hologram version based on the current branch.
     * Ignores for non snapshots.
     * Ignores for master branch
     *
     * @return the Hologram version.
     */
    public static String getHologramsVersion(String buildNumber, String branch) throws GradleException {
        String value = HOLOGRAMS_VERSION;
        if (HOLOGRAMS_VERSION.endsWith("-SNAPSHOT")) {
            try {
                String finalBranch = branch;
                if (branch.isEmpty()) {
                    finalBranch = execCmd("git rev-parse --abbrev-ref HEAD").replace("\n", "");
                }
                if (finalBranch.equals("HEAD")) {
                    throw new GradleException("Can not work in HEAD");
                }

                String finalBranch1 = finalBranch;
                System.out.println("finalBranch = " + finalBranch);
                if (!branchBlacklist.stream().anyMatch(s -> s.contains(finalBranch1))) {
                    value = HOLOGRAMS_VERSION.replace("-SNAPSHOT", String.format("-%s-SNAPSHOT", finalBranch.replace("/", "-")));

                }
            } catch (IOException e) {
                throw new GradleException("Unable to execute git command", e);
            }

        }
        if (!buildNumber.isEmpty() && !buildNumber.isBlank() && !buildNumber.equalsIgnoreCase("0")) {
            value = value.replace("-SNAPSHOT", String.format("-%s-SNAPSHOT", buildNumber));
        }
        System.out.println("Building with version: " + value);
        return value;
    }

    /**
     * Executes a command
     *
     * @param cmd the command
     * @return the return
     * @throws IOException if it failed.
     */
    public static String execCmd(String cmd) throws IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}