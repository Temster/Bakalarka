/*
 * Created on 16.7.2006
 *
 * Copyright (C) 2006
 * David Je�ek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.data;

import java.util.Collection;

import unisave2006.data.value.MeasurementEntry;

public interface MeasurementEntryListener {

    public void entryAdded(MeasurementEntry e);
    public void entryDeleted(int index, MeasurementEntry e);
    public void entryChanged(int index, MeasurementEntry e);
    public void entriesDeleted();
    public void entriesChanged();
    public void entriesAdded(Collection<MeasurementEntry> v);
}
