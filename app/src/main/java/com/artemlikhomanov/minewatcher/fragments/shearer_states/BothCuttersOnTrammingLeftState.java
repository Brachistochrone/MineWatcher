package com.artemlikhomanov.minewatcher.fragments.shearer_states;

import com.artemlikhomanov.minewatcher.fragments.BaseAbstractFragment;
import com.artemlikhomanov.minewatcher.fragments.TechnicalWindowFragment;

/*состояние - шнеки вращаются, движение влево*/
public class BothCuttersOnTrammingLeftState implements ShearerState {

    private TechnicalWindowFragment m_Fragment;

    public BothCuttersOnTrammingLeftState(BaseAbstractFragment fragment){
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
        m_Fragment.stopStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getBothCuttersOnTrammingOffState());
    }

    @Override
    public void startTrammingLeft() {

    }

    @Override
    public void startTrammingRight() {
        m_Fragment.startClockwiseStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getBothCuttersOnTrammingRightState());
    }
}
