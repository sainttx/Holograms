package com.sainttx.holograms.api.line;

/**
 * Represents a hologram line that is updated at a specific interval.
 */
public interface UpdatingHologramLine extends HologramLine {

    /**
     * Updates the contents of this line.
     */
    void update();

    /**
     * Gets the delay in milliseconds between calls to
     * {@link #update()} by the plugins internal scheduler.
     * <p>
     * It is not guaranteed that the line will be updated
     * instantaneously once the delay passes. Rather, the
     * scheduler will call the {@link #update()} whenever
     * it executes and the delay has passed.
     *
     * @return the delay
     */
    long getDelay();

    /**
     * Returns the last time in milliseconds that the line
     * was updated.
     *
     * @return the last update time
     */
    long getLastUpdateTime();
}
