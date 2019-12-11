package com.sainttx.holograms.nms.v1_15_R1;

import net.minecraft.server.v1_15_R1.AxisAlignedBB;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.EnumDirection;
import net.minecraft.server.v1_15_R1.Vec3D;

public class NullBoundingBox extends AxisAlignedBB {

    public NullBoundingBox() {
        super(0, 0, 0, 0, 0, 0);
    }

    @Override
    public double a(EnumDirection.EnumAxis var0) {
        return 0.0;
    }

    @Override
    public double b(EnumDirection.EnumAxis var0) {
        return 0.0;
    }

    @Override
    public AxisAlignedBB a(double var0, double var2, double var4) {
        return this;
    }

    @Override
    public AxisAlignedBB a(Vec3D var0) {
        return this;
    }

    @Override
    public AxisAlignedBB b(double var0, double var2, double var4) {
        return this;
    }

    @Override
    public AxisAlignedBB grow(double var0, double var2, double var4) {
        return this;
    }

    @Override
    public AxisAlignedBB g(double var0) {
        return this;
    }

    @Override
    public AxisAlignedBB a(AxisAlignedBB var0) {
        return this;
    }

    @Override
    public AxisAlignedBB b(AxisAlignedBB var0) {
        return this;
    }

    @Override
    public AxisAlignedBB d(double var0, double var2, double var4) {
        return this;
    }

    @Override
    public AxisAlignedBB a(BlockPosition var0) {
        return this;
    }

    @Override
    public AxisAlignedBB b(Vec3D var0) {
        return this;
    }

    @Override
    public boolean c(AxisAlignedBB var0) {
        return false;
    }

    @Override
    public boolean a(double var0, double var2, double var4, double var6, double var8, double var10) {
        return false;
    }

    @Override
    public boolean c(Vec3D var0) {
        return false;
    }

    @Override
    public boolean e(double var0, double var2, double var4) {
        return false;
    }

    @Override
    public double a() {
        return 0.0;
    }

    @Override
    public double b() {
        return 0.0;
    }

    @Override
    public double c() {
        return 0.0;
    }

    @Override
    public double d() {
        return 0.0;
    }

    @Override
    public AxisAlignedBB shrink(double var0) {
        return this;
    }

    @Override
    public Vec3D f() {
        return Vec3D.a;
    }
}