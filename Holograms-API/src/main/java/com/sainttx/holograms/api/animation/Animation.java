package com.sainttx.holograms.api.animation;

import java.util.Collection;

public interface Animation<T> {

    /**
     * Gets the first slide of the animation.
     *
     * @return the first slide
     */
    T firstSlide();

    /**
     * Gets the next slide to display.
     *
     * @return the next slide in the animation
     * @throws IllegalStateException if there are no slides
     *      registered in the animation
     */
    T nextSlide();

    /**
     * Returns a collection of all currently registered slides.
     *
     * @return all registered slides
     */
    Collection<T> getSlides();

    /**
     * Adds a slide to this animation.
     *
     * @param slide the slide
     * @return <tt>true</tt> if the slide was successfully added
     */
    boolean addSlide(T slide);

    /**
     * Removes a slide from this animation.
     *
     * @param slide the slide
     * @return <tt>true</tt> if the slide was successfully removed
     */
    boolean removeSlide(T slide);
}
