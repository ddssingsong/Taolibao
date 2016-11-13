
package com.jhs.taolibao.view.charting.buffer;

import com.jhs.taolibao.view.charting.data.Entry;
import com.jhs.taolibao.view.charting.interfaces.datasets.IScatterDataSet;

public class ScatterBuffer extends AbstractBuffer<IScatterDataSet> {
    
    public ScatterBuffer(int size) {
        super(size);
    }

    protected void addForm(float x, float y) {
        buffer[index++] = x;
        buffer[index++] = y;
    }

    @Override
    public void feed(IScatterDataSet data) {
        
        float size = data.getEntryCount() * phaseX;
        
        for (int i = 0; i < size; i++) {

            Entry e = data.getEntryForIndex(i);
            addForm(e.getXIndex(), e.getVal() * phaseY);
        }
        
        reset();
    }
}
