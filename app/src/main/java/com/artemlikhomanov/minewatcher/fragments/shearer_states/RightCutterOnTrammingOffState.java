package com.artemlikhomanov.minewatcher.fragments.shearer_states;

import com.artemlikhomanov.minewatcher.fragments.BaseAbstractFragment;
import com.artemlikhomanov.minewatcher.fragments.TechnicalWindowFragment;

/*состояние - правый шнек вращается, движения нет*/
public class RightCutterOnTrammingOffState implements ShearerState {

    private TechnicalWindowFragment m_Fragment;

    public RightCutterOnTrammingOffState(BaseAbstractFragment fragment){
        m_Fragment = (TechnicalWindowFragment) fragment;
    }

    @Override
    public void turnOffShearer() {
        m_Fragment.stopShearerAnimations();
        m_Fragment.setShearerState(m_Fragment.getTurnedOffState());
    }

    @Override
    public void startLeftCutter() {
        m_Fragment.startLeftCutterAnimation();
        m_Fragment.setShearerState(m_Fragment.getBothCuttersOnTrammingOffState());
    }

    @Override
    public void startRightCutter() {

    }

    @Override
    public void turnOffTramming() {

    }

    @Override
    public void startTrammingLeft() {
        m_Fragment.startAnticlockwiseStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getRightCutterOnTrammingLeftState());
    }

    @Override
    public void startTrammingRight() {
        m_Fragment.startClockwiseStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getRightCutterOnTrammingRightState());
    }
}
