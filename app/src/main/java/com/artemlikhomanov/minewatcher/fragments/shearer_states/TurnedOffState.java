package com.artemlikhomanov.minewatcher.fragments.shearer_states;

import com.artemlikhomanov.minewatcher.fragments.BaseAbstractFragment;
import com.artemlikhomanov.minewatcher.fragments.TechnicalWindowFragment;

/*состояние - выключен*/
public class TurnedOffState implements ShearerState {

    private TechnicalWindowFragment m_Fragment;

    public TurnedOffState(BaseAbstractFragment fragment){
        m_Fragment = (TechnicalWindowFragment) fragment;
    }

    @Override
    public void turnOffShearer() {

    }

    @Override
    public void startLeftCutter() {
        m_Fragment.startShearer();
        m_Fragment.startLeftCutterAnimation();
        m_Fragment.setShearerState(m_Fragment.getLeftCutterOnTrammingOffState());
    }

    @Override
    public void startRightCutter() {
        m_Fragment.startShearer();
        m_Fragment.startRightCutterAnimation();
        m_Fragment.setShearerState(m_Fragment.getRightCutterOnTrammingOffState());
    }

    @Override
    public void turnOffTramming() {

    }

    @Override
    public void startTrammingLeft() {
        m_Fragment.startShearer();
        m_Fragment.startAnticlockwiseStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getBothCuttersOffTrammingLeftState());
    }

    @Override
    public void startTrammingRight() {
        m_Fragment.startShearer();
        m_Fragment.startClockwiseStarsAnimation();
        m_Fragment.setShearerState(m_Fragment.getBothCuttersOffTrammingRightState());
    }
}
