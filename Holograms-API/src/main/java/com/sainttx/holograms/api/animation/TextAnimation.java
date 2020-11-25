package com.sainttx.holograms.api.animation;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TextAnimation implements Animation<String> {

    private final List<String> slides;
    private int slide = 0;

    public TextAnimation() {
        this.slides = new ArrayList<>();
    }

    public TextAnimation(String... stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = Arrays.asList(stacks);
    }

    public TextAnimation(List<String> stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = stacks;
    }

    @Override
    public String firstSlide() {
        return slides.get(0);
    }

    @Override
    public String nextSlide() {
        if (slides.isEmpty()) {
            throw new IllegalStateException("There are no slides to display");
        }
        if (slide >= slides.size()) {
            slide = 0;
        }
        return slides.get(slide++);
    }

    @Override
    public Collection<String> getSlides() {
        return slides;
    }

    @Override
    public boolean addSlide(String slide) {
        return slides.add(slide);
    }

    @Override
    public boolean removeSlide(String slide) {
        if (slides.remove(slide)) {
            this.slide = Math.min(this.slide, slides.size());
            return true;
        }
        return false;
    }
}