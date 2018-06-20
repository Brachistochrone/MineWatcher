package com.artemlikhomanov.minewatcher.fragments.shearer_states;

import com.artemlikhomanov.minewatcher.fragments.BaseAbstractFragment;
import com.artemlikhomanov.minewatcher.fragments.TechnicalWindowFragment;

/*состояние - правый шнек вращается, движение влево*/
public class RightCutterOnTrammingLeftState implements ShearerState {

    private TechnicalWindowFragment m_Fragment;

    public RightCutterOnTrammingLeftState(BaseAbstractFragment fragment){
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
        m_Fragment.setShearerState(m_Fragment.getRightCutterOnTrammingOffState());
    }

    @Override
    public void startTrammingLeft() {

    }

    @Override
    public void startTrammingRight() {
        m_Fragment.startClockwiseStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getRightCutterOnTrammingRightState());
    }
}
