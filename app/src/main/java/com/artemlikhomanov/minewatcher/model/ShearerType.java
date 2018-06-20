package com.artemlikhomanov.minewatcher.model;

import android.support.annotation.DrawableRes;

import com.artemlikhomanov.minewatcher.R;

public enum ShearerType {
    CLS450_first(R.drawable.cls450_grey, R.drawable.cls450_left_cutter_grey, R.drawable.cls450_right_cutter_grey, R.drawable.cls450_stars_0, R.drawable.cls450_left_cutter_0, R.drawable.cls450_right_cutter_0, R.drawable.cls450_left_cutter_animation, R.drawable.cls450_right_cutter_animation, R.drawable.cls450_stars_clockwise_animation, R.drawable.cls450_stars_anticlockwise_animation, R.drawable.cls450_position_shearer),
    CLS450(R.drawable.cls450_grey, R.drawable.cls450_left_cutter_grey, R.drawable.cls450_right_cutter_grey, R.drawable.cls450_stars_0, R.drawable.cls450_left_cutter_0, R.drawable.cls450_right_cutter_0, R.drawable.cls450_left_cutter_animation, R.drawable.cls450_right_cutter_animation, R.drawable.cls450_stars_clockwise_animation, R.drawable.cls450_stars_anticlockwise_animation, R.drawable.cls450_position_shearer),
    KDK500(R.drawable.kdk500_grey, R.drawable.kdk500_left_cutter_grey, R.drawable.kdk500_right_cutter_grey, R.drawable.kdk500_stars_0, R.drawable.kdk500_left_cutter_0, R.drawable.kdk500_right_cutter_0, R.drawable.kdk500_left_cutter_animation, R.drawable.kdk500_right_cutter_animation, R.drawable.kdk500_stars_clockwise_animation, R.drawable.kdk500_stars_anticlockwise_animation, R.drawable.kdk500_position_shearer),
    ;

    private final int mShearerGreyResourceId;
    private final int mLeftCutterGreyResourceId;
    private final int mRightCutterGreyResourceId;
    private final int mShearerColoredResourceId;
    private final int mLeftCutterColoredResourceId;
    private final int mRightCutterColoredResourceId;
    private final int mLeftCutterAnimationResourceId;
    private final int mRightCutterAnimationResourceId;
    private final int mStarsClockwiseAnimationResourceId;
    private final int mStarsAnticlockwiseAnimationResourceId;
    private final int mShearerPositionResourceId;

    ShearerType(@DrawableRes int shearerGreyResourceId, @DrawableRes int leftCutterGreyResourceId, @DrawableRes int rightCutterGreyResourceId,
                @DrawableRes int shearerColoredResourceId, @DrawableRes int leftCutterColoredResourceId, @DrawableRes int rightCutterColoredResourceId,
                @DrawableRes int leftCutterAnimationResourceId, @DrawableRes int rightCutterAnimationResourceId,
                @DrawableRes int starsClockwiseAnimationResourceId, @DrawableRes int starsAnticlockwiseAnimationResourceId,
                @DrawableRes int shearerPositionResourceId) {
        mShearerGreyResourceId = shearerGreyResourceId;
        mLeftCutterGreyResourceId = leftCutterGreyResourceId;
        mRightCutterGreyResourceId = rightCutterGreyResourceId;
        mShearerColoredResourceId = shearerColoredResourceId;
        mLeftCutterColoredResourceId = leftCutterColoredResourceId;
        mRightCutterColoredResourceId = rightCutterColoredResourceId;
        mLeftCutterAnimationResourceId = leftCutterAnimationResourceId;
        mRightCutterAnimationResourceId = rightCutterAnimationResourceId;
        mStarsClockwiseAnimationResourceId = starsClockwiseAnimationResourceId;
        mStarsAnticlockwiseAnimationResourceId = starsAnticlockwiseAnimationResourceId;
        mShearerPositionResourceId = shearerPositionResourceId;
    }

    @DrawableRes
    public int getShearerGrey() {
        return mShearerGreyResourceId;
    }

    @DrawableRes
    public int getLeftCutterGrey() {
        return mLeftCutterGreyResourceId;
    }

    @DrawableRes
    public int getRightCutterGrey() {
        return mRightCutterGreyResourceId;
    }

    @DrawableRes
    public int getShearerColored() {
        return mShearerColoredResourceId;
    }

    @DrawableRes
    public int getLeftCutterColored() {
        return mLeftCutterColoredResourceId;
    }

    @DrawableRes
    public int getRightCutterColored() {
        return mRightCutterColoredResourceId;
    }

    @DrawableRes
    public int getLeftCutterAnimation() {
        return mLeftCutterAnimationResourceId;
    }

    @DrawableRes
    public int getRightCutterAnimation() {
        return mRightCutterAnimationResourceId;
    }

    @DrawableRes
    public int getStarsClockwiseAnimation() {
        return mStarsClockwiseAnimationResourceId;
    }

    @DrawableRes
    public int getStarsAnticlockwiseAnimation() {
        return mStarsAnticlockwiseAnimationResourceId;
    }

    @DrawableRes
    public int getShearerPositionImage() {
        return mShearerPositionResourceId;
    }
}
