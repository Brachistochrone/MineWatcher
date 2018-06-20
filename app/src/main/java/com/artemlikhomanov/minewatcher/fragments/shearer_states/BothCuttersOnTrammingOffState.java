package com.artemlikhomanov.minewatcher.fragments.shearer_states;

import com.artemlikhomanov.minewatcher.fragments.BaseAbstractFragment;
import com.artemlikhomanov.minewatcher.fragments.TechnicalWindowFragment;

/*состояние - шнеки вращаются, движение нет*/
public class BothCuttersOnTrammingOffState implements ShearerState {

    private TechnicalWindowFragment m_Fragment;

    public BothCuttersOnTrammingOffState(BaseAbstractFragment fragment){
        m_Fragment = (TechnicalWindowFragment) fragment;
    }

    @Override
    public void turnOffShearer() {
        m_Fragment.stopShearerAnimations();
        m_Fragment.setShearerState(m_Fragment.getTurnedOffState());
    }

    @Override
    public void startLeftCutter() {

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
        m_Fragment.setShearerState(m_Fragment.getBothCuttersOnTrammingLeftState());
    }

    @Override
    public void startTrammingRight() {
        m_Fragment.startClockwiseStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getBothCuttersOnTrammingRightState());
    }
}
