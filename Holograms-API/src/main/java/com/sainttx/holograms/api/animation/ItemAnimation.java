package com.sainttx.holograms.api.animation;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ItemAnimation implements Animation<ItemStack> {

    private final List<ItemStack> slides;
    private int slide = 0;

    public ItemAnimation() {
        this.slides = new ArrayList<>();
    }

    public ItemAnimation(ItemStack... stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = Arrays.asList(stacks);
    }

    public ItemAnimation(List<ItemStack> stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = stacks;
    }

    @Override
    public ItemStack firstSlide() {
        return slides.get(0);
    }

    @Override
    public ItemStack nextSlide() {
        if (slides.isEmpty()) {
            throw new IllegalStateException("There are no slides to display");
        }
        if (slide >= slides.size()) {
            slide = 0;
        }
        return slides.get(slide++);
    }

    @Override
    public Collection<ItemStack> getSlides() {
        return slides;
    }

    @Override
    public boolean addSlide(ItemStack slide) {
        return slides.add(slide);
    }

    @Override
    public boolean removeSlide(ItemStack slide) {
        if (slides.remove(slide)) {
            this.slide = Math.min(this.slide, slides.size());
            return true;
        }
        return false;
    }
}
